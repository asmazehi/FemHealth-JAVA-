package model.User;

public class Utilisateur {
    private int id;
    private String nom;
    private String mail;
    private String mdp;
    private String role;

    public Utilisateur() {
    }

    public Utilisateur(int id) {
        this.id = id;
    }

    public Utilisateur(String nom, String mail) {
        this.nom = nom;
        this.mail = mail;

    }

    public Utilisateur(String nom, String mail, String mdp ) {
        this.nom = nom;
        this.mail = mail;
        this.mdp = mdp;

    }


    public Utilisateur(int id, String nom, String mail, String mdp, String role) {
        this.id = id;
        this.nom = nom;
        this.mail = mail;
        this.mdp = mdp;
        this.role = role;

    }

    public Utilisateur(String nom, String mail, String mdp, String role) {
        this.nom = nom;
        this.mail = mail;
        this.mdp = mdp;

        this.role = role;

    }


    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getMail() {
        return mail;
    }

    public String getMdp() {
        return mdp;
    }



    public String getRole() {
        return role;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }



    public void setRole(String role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return "Utilisateur{" + "id=" + id + ", nom=" + nom +  ", mail=" + mail + ", mdp=" + mdp + ", role=" + role +  '}';
    }







}


