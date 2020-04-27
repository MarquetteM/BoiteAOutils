package fr.sio1.boiteoutil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

public class LectureFtpJSon extends AppCompatActivity {
    private EditText listeAffich;
    private RequetteHttp lectureFtp;
    private final int ftpcode = 1;
    private static final String lien = "https://android.sioecoris.com/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_j_son);
        listeAffich = (EditText) this.findViewById(R.id.json_edittext);
    }

    public void retour(View v) {
        Intent intent = new Intent(LectureFtpJSon.this, MainActivity.class);
        startActivity(intent);
    }

    public void lecJson(View v) {
        // Lecture du fichier Json
        try {
            Contact[] contacts = ParseJson.lecContacts(this);
            Log.i("BTO","Lec json");
            for (Contact contact: contacts ) {
                listeAffich.append(contact.toString());
            }
        } catch (Exception e) {
            Log.e ("BTO", e.getMessage());
        }
    }

    public void lectFtp (View v) throws IOException {
        // Récupération Ftp
    //    lectureFtp = new RequetteHttp(lien);
        try {
            recFtp();
        } catch (IOException e) {
            Log.e ("BTO", e.getMessage());
        }
    }

    // lecture Ftp
    private void recFtp() throws IOException {
        //Réseau dispo
        if ( !reseauDispo(LectureFtpJSon.this)){
            Toast.makeText(LectureFtpJSon.this, "Accès Internet nécessaire", Toast.LENGTH_SHORT).show();
            return;
        }
        BarreDeProgression.affProgress(LectureFtpJSon.this);
        //Création de la tache asynchrone pour lecture ftp
        new AsyncTask<Void, Void, String>()  {
            protected String doInBackground(Void[] param) {
                String retour ="";
                HashMap<String ,String> map = new HashMap<>();
                try {
                    RequetteHttp req = new RequetteHttp(lien);
                    retour = req.prepare(RequetteHttp.Method.POST).withData(map).sendAndReadString();
                } catch (Exception e) {
                    retour = e.getMessage();
                }
                return retour;
            }

            protected void onPostExecute (String result) {
                Log.i("BTO", result);
                onTaskCompleted (result,ftpcode);
            }

        }.execute();
    }

    public void onTaskCompleted (String reponse, int serviceCode) {
        switch (serviceCode) {
            case ftpcode:
                BarreDeProgression.supprProgress();
                listeAffich.append(reponse.toString());
                Log.i ("BTO",reponse);
        }
    }


    //fonction vérifie si le réseau est disponible
    private static boolean reseauDispo(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return  false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

}
