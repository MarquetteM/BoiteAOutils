package fr.sio1.boiteoutil;

public class Contact {
    private int id;
    private String nom;
    private String prenom;
    private String[] adMails;
    private Adresse adresse;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() { return nom; }

    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }

    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String[] getAdMails() { return adMails; }

    public void setAddMail(String[] adMails) { this.adMails = adMails; }

    public Adresse getAdresse() { return adresse; }

    public void setAdress(Adresse adresse) { this.adresse = adresse; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("id : "+ this.id);
        sb.append("\nnom : "+this.nom+" "+this.prenom);
        if(this.adMails != null){
            sb.append("\nadresse Mail : ");
            for (String adMail : this.adMails){
                sb.append("\n   - " + adMail);
            }
        }
        if (this.adresse != null){
            sb.append("\n adresse : " + this.adresse.toString()+"\n\n");
        }

        return sb.toString();
    }
}
