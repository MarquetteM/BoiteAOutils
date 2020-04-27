package fr.sio1.boiteoutil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

// Activité de saisie
public class SaisieActivity extends AppCompatActivity {
    private Button btSaisie;
    private Button btAnnuler;
    private EditText textSaisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisie);

        //Création objet bouton valider annuler champSaisi
        Button btSaisie = findViewById(R.id.btnvalider);
        Button btAnnuler = findViewById(R.id.btnannuler);
        final EditText textSaisi = findViewById(R.id.textsaisi);

        //Ecoute click sur bouton saisie
        btSaisie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SaisieActivity.this, MainActivity.class);
                Log.i("BTO", String.valueOf(textSaisi.getText()));
                intent.putExtra("texteSaisi",textSaisi.getText().toString());
                startActivity(intent);
            }
        });
        //Ecoute clck bouton annuler
        btAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaisieActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
