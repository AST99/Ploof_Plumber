package ci.astdev.onep_users

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ci.astdev.onep_users.databinding.ActivityMainBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        binding.btnInfoPerso.setTextColor(Color.parseColor("#DCDCDC"))
        binding.btnFS.setTextColor(Color.parseColor("#000000"))

        binding.layoutInfoPerso.visibility=View.GONE
        binding.layoutListeFuite.visibility=View.VISIBLE

        binding.btnFS.setOnClickListener {
            binding.btnInfoPerso.setTextColor(Color.parseColor("#DCDCDC"))
            binding.btnFS.setTextColor(Color.parseColor("#000000"))
            binding.btnFS.typeface = Typeface.DEFAULT_BOLD
            binding.layoutInfoPerso.visibility=View.GONE
            binding.layoutListeFuite.visibility=View.VISIBLE
        }

        binding.btnInfoPerso.setOnClickListener{
            binding.btnFS.setTextColor(Color.parseColor("#DCDCDC"))
            binding.btnInfoPerso.setTextColor(Color.parseColor("#000000"))
            binding.btnInfoPerso.typeface = Typeface.DEFAULT_BOLD
            binding.layoutInfoPerso.visibility=View.VISIBLE
            binding.layoutListeFuite.visibility=View.GONE
        }

        //Affiche les données de l'utilisateur connecté. Ici on affiche juste le nom.
        val mDatabase = FirebaseDatabase.getInstance().reference
        FirebaseAuth.getInstance().currentUser?.let {
            mDatabase.child("Plombiers").child(it.uid).get().addOnCompleteListener { task: Task<DataSnapshot> ->
                if (!task.isSuccessful) {
                    Log.e("firebase", "Error getting data", task.exception)
                } else {
                    binding.Name.text = (task.result.child("nomPrenom").getValue(String::class.java)).toString()
                    binding.Mail.text = (task.result.child("mail").getValue(String::class.java)).toString()
                    binding.Mobile.text = (task.result.child("phone").getValue(String::class.java)).toString()
                    //Log.d("firebase", (task.result.child("nomPrenom").getValue(String::class.java)).toString())
                }
            }
        }

        listeFuites()
    }

    fun listeFuites() {

        val layoutM = LinearLayoutManager(this)
        layoutM.orientation = LinearLayoutManager.VERTICAL

        binding.recyclerView.layoutManager = layoutM

        val model_List: MutableList<FuitesModel?> = ArrayList()
        val adapter = Adapter(model_List)
        binding.recyclerView.adapter = adapter


        val mDatabase = FirebaseDatabase.getInstance().reference.child("Fuites signalées")
        mDatabase.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    try {
                        model_List.add(snapshot.getValue(FuitesModel::class.java))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@MainActivity, databaseError.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_deconnexion -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(applicationContext, ConnexionPage::class.java))
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}