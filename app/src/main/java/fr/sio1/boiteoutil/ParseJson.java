package fr.sio1.boiteoutil;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ParseJson {

    //Méthode qui lit l'ensemble des données tableau JSon et qui retourne un tableau d'objet contacts
    public static Contact[] lecContacts (Context context) throws IOException, JSONException {
        String jsonText = readText(context, R.raw.contact);
        JSONArray jsonRoots = new JSONArray(jsonText);

        Contact[] contacts = new Contact[jsonRoots.length()];

        for (int i=0; i<jsonRoots.length(); i++){
            JSONObject jsonRoot = jsonRoots.getJSONObject(i);
            contacts[i] = lectContactJSON(jsonRoot);
        }
        return contacts;
    }

    //methode qui récupère les données contenues dans l'objet Json passé en paramètre
    //et retourne un objet Contact
    public static Contact lectContactJSON (JSONObject jsonRoot) throws IOException, JSONException{
        int id = jsonRoot.getInt("id");
        String nom = jsonRoot.getString("nom");
        String prenom = jsonRoot.getString("prenom");

        JSONArray jsonArray = jsonRoot.getJSONArray("adMails");
        String[] addMails = new String[jsonArray.length()];

        for (int i=0; i<jsonArray.length(); i++){
            addMails[i] = jsonArray.getString(i);
        }

        JSONObject jsonAddresse = jsonRoot.getJSONObject("adresse");
        int num = jsonAddresse.getInt("num");
        String rue = jsonAddresse.getString("rue");
        int cp = jsonAddresse.getInt("cp");
        String ville = jsonAddresse.getString("ville");
        Adresse adresse = new Adresse(num, rue,cp, ville);

        Contact contact = new Contact();
        contact.setId(id);
        contact.setNom(nom);
        contact.setPrenom(prenom);
        contact.setAdress(adresse);
        contact.setAddMail(addMails);

        return contact;
    }

    //lecture de l'ensemble du fichier, retourne son contenu sous forme de chaine de caractères
    private static String readText(Context context, int resId) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String s = null;
        while ((s=br.readLine()) != null) {
            sb.append(s);
            sb.append("\n");
        }return sb.toString();
    }
}
