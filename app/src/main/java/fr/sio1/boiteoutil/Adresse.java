package fr.sio1.boiteoutil;

public class Adresse {
    private int num;
    private String rue;
    private String ville;
    private int cp;

    public Adresse() {     }

    public Adresse(int num, String rue, int cp, String ville) {
        this.num = num;
        this.rue = rue;
        this.cp =cp;
        this.ville = ville;
    }
    public int getNum() { return num; }

    public void setNum(int num) { this.num = num; }

    public String getRue() { return rue; }

    public void setRue(String rue) { this.rue = rue; }

    public int getCp() { return cp; }

    public void setCp(int cp) { this.cp = cp; }

    public String getVille() { return ville; }

    public void setVille(String ville) { this.ville = ville; }

    public String toString(){
        return num + " rue " + rue + "\n"+cp+ " " + ville;
    }

}
