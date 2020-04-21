package fr.sio1.boiteoutil;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "Utilisateur")
public class Utilisateur {
    @DatabaseField (generatedId = true)
    private int idUtilisateur;
    @DatabaseField
    private String nom;
    @DatabaseField
    private String prenom;
    @DatabaseField
    private String telephone;

    public Utilisateur() {
    }

    public Utilisateur(String nom, String prenom, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {return nom + '\t' + prenom + '\n' + telephone; }
}
