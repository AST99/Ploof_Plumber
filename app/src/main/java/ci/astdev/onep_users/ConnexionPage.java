package ci.astdev.onep_users;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import ci.astdev.onep_users.databinding.ActivityConnexionPageBinding;

public class ConnexionPage extends AppCompatActivity {

    private TextInputEditText editTxtmail;
    private TextInputEditText editTxtPassWrd;
    public static FirebaseAuth mAuth;

    ActivityConnexionPageBinding binding;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityConnexionPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null)
            startActivity(new Intent(ConnexionPage.this, MainActivity.class));

        progressDialog = new ProgressDialog(ConnexionPage.this, R.style.MyAlertDialogStyle);
        progressDialog.setCanceledOnTouchOutside(false);

        this.editTxtPassWrd = findViewById(R.id.passWrd);
        this.editTxtmail = findViewById(R.id.Mail);

        binding.btnConnecter.setOnClickListener(view -> mailAndPassWrdConnexion());

        binding.btnInscription.setOnClickListener(view ->{
            startActivity(new Intent(getApplicationContext(),InscriptionPage.class));
            this.finish();
        });
    }

    /******************************Connexion avec le mail et le mot de passe***************************/
    private void mailAndPassWrdConnexion(){

        String mail = Objects.requireNonNull(binding.Mail.getText()).toString().trim();
        String passWrd = Objects.requireNonNull(binding.passWrd.getText()).toString().trim();

        if (mail.equals("")){
            if (TextUtils.isEmpty(editTxtmail.getText())){
                editTxtmail.setError("Votre e-mail est requis!");
                editTxtmail.requestFocus();
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(Objects.requireNonNull(editTxtmail.getText()).
                    toString().trim()).matches()){
                editTxtmail.setError("Merci de fournir un email valide!");
                editTxtmail.requestFocus();
            }
        }
        else if (passWrd.equals("")){
            if (TextUtils.isEmpty(editTxtPassWrd.getText())){
                editTxtPassWrd.setError("Veuillez saisir un mot de passe!");
                editTxtPassWrd.requestFocus();
            }
            if (editTxtPassWrd.length()<5){
                editTxtPassWrd.setError("La longueur minimale du mot de passe doit ??tre de 5 caract??res");
                editTxtPassWrd.requestFocus();
            }
        }
        else {
            progressDialog.setMessage("Connexion en cours...!");
            progressDialog.show();
            try {

                mAuth.signInWithEmailAndPassword(mail, passWrd).addOnSuccessListener(authResult -> {
                    progressDialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Toast.makeText(ConnexionPage.this,"Vous ??tes connect?? en tant que plombier"
                            , Toast.LENGTH_SHORT).show();
                    editTxtPassWrd.setText("");
                    editTxtmail.setText("");
                    this.finish();
                }).addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(ConnexionPage.this,"La connexion a ??chouer !\n V??rifiez vos" +
                            " informations ou cr??ez un compte !", Toast.LENGTH_LONG).show();
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}