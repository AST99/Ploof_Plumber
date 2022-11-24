package ci.astdev.onep_users

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ci.astdev.onep_users.databinding.ActivityFuitesDetailsBinding
import com.google.firebase.auth.FirebaseAuth

class FuitesDetails : AppCompatActivity() {

    private lateinit var binding: ActivityFuitesDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFuitesDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.title = "Détails de la fuite"

        binding.Name.text=Adapter.strShowName
        binding.Mobile.text=Adapter.strShowPhone
        binding.quartier.text=Adapter.strQuartier
        binding.description.text=Adapter.strDescription

        binding.btnIntervenir.setOnClickListener{
            val gmmIntentUri:Uri = Uri.parse("geo:0,0?q="+Adapter.strPosition)
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.`package`="com.google.android.apps.maps"
            startActivity(mapIntent)
        }
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