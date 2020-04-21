package fr.sio1.boiteoutil;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LectureHttp extends AsyncTask<URL,Void,String> {

    public void onPreExecute(){

    }

    public String doInBackground (URL...urls) {
        StringBuilder sb = new StringBuilder();
        URL url = urls[0];

        HttpURLConnection connexion = null;
        BufferedReader buffer = null;
        try {
            connexion = (HttpURLConnection) url.openConnection();
            connexion.connect();
            InputStream inputStream = connexion.getInputStream();
            buffer = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String ligne = "";
            while ((ligne=buffer.readLine())!=null){
                stringBuffer.append(ligne);
            }

        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            connexion.disconnect();
            try {
                buffer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    public void onPostExecute (String result) {
        Log.i("BTO", result);
    }
}
