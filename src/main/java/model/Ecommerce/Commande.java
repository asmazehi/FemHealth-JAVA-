package model.Ecommerce;

import java.util.Date;

public class Commande {
    private int id;
    private int idPanier;
    private String statut;
    private String Mpaiement;
    private String Mlivraison;
    private String adresse;
    private java.sql.Date DateC;
    private String phone;
    public Commande(){}

    public Commande(int id, int idPanier, String statut, String mpaiement, String mlivraison, String adresse, String phone) {
        this.id = id;
        this.idPanier = idPanier;
        this.statut = statut;
        Mpaiement = mpaiement;
        Mlivraison = mlivraison;
        this.adresse = adresse;
        Date systemDate = new Date();
        this.DateC = new java.sql.Date(systemDate.getTime());
        this.phone = phone;
    }

    public Commande(int idPanier, String adresse,String statut, String mpaiement,    String phone ,String mlivraison) {
        this.idPanier = idPanier;
        this.statut = statut;
        Mpaiement = mpaiement;
        Mlivraison = mlivraison;
        this.adresse = adresse;
        Date systemDate = new Date();
        this.DateC = new java.sql.Date(systemDate.getTime());
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", idPanier=" + idPanier +
                ", statut='" + statut + '\'' +
                ", Mpaiement='" + Mpaiement + '\'' +
                ", Mlivraison='" + Mlivraison + '\'' +
                ", adresse='" + adresse + '\'' +
                ", DateC=" + DateC +
                ", phone='" + phone + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(int idPanier) {
        this.idPanier = idPanier;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getMpaiement() {
        return Mpaiement;
    }

    public void setMpaiement(String mpaiement) {
        Mpaiement = mpaiement;
    }

    public String getMlivraison() {
        return Mlivraison;
    }

    public void setMlivraison(String mlivraison) {
        Mlivraison = mlivraison;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public java.sql.Date getDateC() {
        return DateC;
    }

    public void setDateC(java.sql.Date dateC) {
        DateC = dateC;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
