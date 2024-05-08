package model.User;

import java.util.Date;

public class Utilisateur {
    private int id;
    private String nom;
    private String email;
    private String password;
    private String roles;
    private int active;
    private Date Registred_at;

    private String status;
    public Utilisateur() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Utilisateur(int id, String nom, String email, String mdp) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.password = mdp;
        this.roles = "[\"ROLE_CLIENT\"]";
        Date DT=new Date();
        this.Registred_at=new java.sql.Date(DT.getTime());

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Utilisateur(String nom, String email, String mdp) {
        this.nom = nom;
        this.email = email;
        this.password = mdp;

        this.roles = "[\"ROLE_CLIENT\"]";
        this.active=1;
        Date DT=new Date();
        this.Registred_at= new java.sql.Date(DT.getTime());

    }


    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getMail() {
        return email;
    }

    public String getMdp() {
        return password;
    }



    public String getRole() {
        return roles;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public void setMail(String mail) {
        this.email = mail;
    }

    public void setMdp(String mdp) {
        this.password = mdp;
    }



    public void setRole(String role) {
        this.roles = role;
    }

    public int getActive() {
        return active;
    }

    public java.sql.Date getRegistred_at() {
        return (java.sql.Date) Registred_at;
    }

    public void setRegistred_at(Date registred_at) {
        Registred_at = registred_at;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Utilisateur{" + "id=" + id + ", nom=" + nom +  ", email=" + email + ", mdp=" + password + ", role=" + roles +  "active=" + active +  "Registred_ar=" + Registred_at +  '}';
    }







}