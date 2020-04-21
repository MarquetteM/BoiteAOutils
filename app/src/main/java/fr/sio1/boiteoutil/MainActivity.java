package fr.sio1.boiteoutil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    private TextView txtSaisie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Création objets boutons editText ....
        txtSaisie = findViewById(R.id.textesaisi);

        intent = getIntent(); //Récupère les paramètres passé par l'activité d'où l'on vient
        //vérifie si l'application appelante a passé une information "texteSaisi"
        if (intent.hasExtra("texteSaisi")) {
            //récupère le contenu et l'affiche
            Log.i("BTO","on revient de la saisie");
            String texteSaisi = intent.getStringExtra("texteSaisi");
            Log.i("BTO",texteSaisi);
            txtSaisie.setText(texteSaisi);
            Log.i("BTO","affichage du texte");
        }
    }

    public void saisie(View v) {
        //lance l'activité saisie
        intent = new Intent(MainActivity.this, SaisieActivity.class);
        startActivity(intent);
    }
    public void saisieDate(View v) {
        //lance l'activité saisie date
        intent = new Intent(MainActivity.this, DateActivity.class);
        startActivity(intent);
    }
    public void utilisateur(View V) {
        //lance l'activité affichage des utilisateurs
        intent = new Intent(MainActivity.this, AffUtilisateur.class);
        startActivity(intent);
    }
    public void batterie (View v) {
        //lance l'activite récupération des informations de la batterie
        intent = new Intent(MainActivity.this, BatterieActivity.class);
        startActivity(intent);
    }
    public void jsonHttp (View v) {
        //lance l'activité récupération information par http et parse fichier json
        intent = new Intent(MainActivity.this, LectureFtpJSon.class);
        startActivity(intent);
    }
}
