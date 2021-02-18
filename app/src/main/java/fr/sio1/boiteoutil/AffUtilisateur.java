package fr.sio1.boiteoutil;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class AffUtilisateur extends AppCompatActivity implements View.OnClickListener {

    private DataBaseManager gestionDataBase;
    private ListView listUtilisateur;
    private Button btnRetour;
    private Button btnAjout;
    private TextView listVide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aff_utilisateur);

        listUtilisateur = (ListView) findViewById(R.id.listUtilisateur);
        btnRetour = (Button) findViewById(R.id.btnRetourUtil);
        btnAjout =(Button) findViewById(R.id.btnAjoutUtil);
        listVide = (TextView) findViewById(R.id.listeVide);

        btnRetour.setOnClickListener(this);
        btnAjout.setOnClickListener(this);

        //connexion bdd
        gestionDataBase = new DataBaseManager(this);

        final List<Utilisateur> utils = gestionDataBase.lecUtilisateurs();

        if (utils.isEmpty()){
            listUtilisateur.setVisibility(View.GONE);
            listVide.setVisibility(View.VISIBLE);
        }
        else {
            listUtilisateur.setVisibility(View.VISIBLE);
            listVide.setVisibility(View.GONE);
        }
//        ArrayAdapter <Utilisateur> arrayAdapter =  new ArrayAdapter<Utilisateur>(this, android.R.layout.simple_list_item_1, utils);
//        listUtilisateur.setAdapter(arrayAdapter);
//        final ListView listView = (ListView)findViewById(R.id.listUtilisateur);
        listUtilisateur.setAdapter(new ListUtilisateursAdap(this, utils));

        gestionDataBase.close();

        //gestion click sur élément de la liste
        listUtilisateur.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AffUtilisateur.this, SaisieUtilisateur.class);
                intent.putExtra("idUtilisateur", utils.get(position).getIdUtilisateur());
                startActivity(intent);
            }
        });
        listUtilisateur.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                confirmDialog(utils.get(position), AffUtilisateur.this);
                return false;
            }
        });
    }

    private void confirmDialog (final Utilisateur utilisateur, final AffUtilisateur afUtilisateur) {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder
                .setMessage("Etes-vous cetain de vouloir supprimer cet utilisateur")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gestionDataBase = new DataBaseManager(afUtilisateur);
                        gestionDataBase.supUtilisateur(utilisateur);
                        gestionDataBase.close();
                        dialog.cancel();
                        Intent intent = new Intent(AffUtilisateur.this, AffUtilisateur.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }
    @Override
    public void onClick(View v) {
        if (v == btnRetour) {
            Intent intent = new Intent(AffUtilisateur.this, MainActivity.class);
            startActivity(intent);
        }
        else if (v == btnAjout) {
            Intent intent = new Intent(AffUtilisateur.this, SaisieUtilisateur.class);
            startActivity(intent);
        }

    }
}
