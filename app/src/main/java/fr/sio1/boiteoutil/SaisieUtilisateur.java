package fr.sio1.boiteoutil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class SaisieUtilisateur extends AppCompatActivity implements View.OnClickListener {
    private EditText nom;
    private EditText prenom;
    private EditText telephone;
    private Button btnRetour;
    private Button btnValid;
    private Intent intent;
    private Utilisateur utilisateur;
    private DataBaseManager gestBDD;
    private int idUtilisateur;
    private int modifUtilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisie_utilisateur);

        nom = (EditText) findViewById(R.id.saisieNom);
        prenom = (EditText) findViewById(R.id.saisiePrenom);
        telephone = (EditText) findViewById(R.id.saisieTelephone);
        btnRetour = (Button) findViewById(R.id.saisieRetour);
        btnValid = (Button) findViewById(R.id.saisieValid);

        btnRetour.setOnClickListener(this);
        btnValid.setOnClickListener(this);

        intent = getIntent();
        if (intent != null){
            idUtilisateur = intent.getIntExtra("idUtilisateur", 0);
            modifUtilisateur = 0;
            utilisateur = new Utilisateur();
            if (idUtilisateur != 0){
                modifUtilisateur = 1;
                gestBDD = new DataBaseManager(this);
                List<Utilisateur> utilisateurs = gestBDD.lecUtilisateur(idUtilisateur);
                utilisateur = utilisateurs.get(0);
                gestBDD.close();
                nom.setText(utilisateur.getNom());
                prenom.setText(utilisateur.getPrenom());
                telephone.setText(utilisateur.getTelephone());
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnRetour) {
            intent = new Intent(SaisieUtilisateur.this, AffUtilisateur.class);
            startActivity(intent);
        }
        else if (v == btnValid) {
            Log.i ("BTO", "Début bouton valide");
            utilisateur.setNom(String.valueOf(nom.getText()));
            utilisateur.setPrenom(String.valueOf(prenom.getText()));
            utilisateur.setTelephone(String.valueOf(telephone.getText()));
            if (modifUtilisateur == 0) {
                gestBDD = new DataBaseManager(this);
                gestBDD.ajoutUtil(utilisateur);
                gestBDD.close();
            }
            else if (modifUtilisateur == 1) {
                gestBDD =new DataBaseManager(this);
                gestBDD.majUtilisateur(utilisateur);
                gestBDD.close();
            }
            intent = new Intent(SaisieUtilisateur.this, AffUtilisateur.class);
            startActivity(intent);
        }
    }
}
