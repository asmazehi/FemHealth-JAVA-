package models.Sponsoring;

import java.util.Objects;

public class Produit {
    private int id;
    private String nom;
    private float prix;
    private int taux_remise;
    private String categorie;
    private String image;
    private String description;
    private Sponsor sponsor;

    public Produit() {
    }

    public Produit(int id, String nom, float prix, int taux_remise, String categorie, String image, String description, Sponsor sponsor) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.taux_remise = taux_remise;
        this.categorie = categorie;
        this.image = image;
        this.description = description;
        this.sponsor = sponsor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getTaux_remise() {
        return taux_remise;
    }

    public void setTaux_remise(int taux_remise) {
        this.taux_remise = taux_remise;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", taux_remise=" + taux_remise +
                ", categorie='" + categorie + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", sponsor_id=" + (sponsor != null ? sponsor.getId() : "null") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produit produit = (Produit) o;
        return id == produit.id &&
                Float.compare(produit.prix, prix) == 0 &&
                taux_remise == produit.taux_remise &&
                Objects.equals(nom, produit.nom) &&
                Objects.equals(categorie, produit.categorie) &&
                Objects.equals(image, produit.image) &&
                Objects.equals(description, produit.description) &&
                Objects.equals(sponsor, produit.sponsor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prix, taux_remise, categorie, image, description, sponsor);
    }
}
