package model.Sponsoring;

import service.Sponsoring.SponsorService;

import java.sql.SQLException;
import java.util.Objects;

public class Sponsor {
    private int id;
    private String nom;
    private String duree_contrat;

    public Sponsor(){}
    public Sponsor(int id, String nom, String duree_contrat) {
        this.id = id;
        this.nom = nom;
        this.duree_contrat = duree_contrat;
    }
    public Sponsor(String nom, String duree_contrat) {
        this.nom = nom;
        this.duree_contrat = duree_contrat;
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

    public String getDuree_contrat() {
        return duree_contrat;
    }

    public void setDuree_contrat(String duree_contrat) {
        this.duree_contrat = duree_contrat;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Sponsor sponsor = (Sponsor) obj;
        return id == sponsor.id;
    }

    @Override
    public String toString() {
        return "Sponsor{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", duree_contrat='" + duree_contrat + '\'' +
                '}';
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, nom, duree_contrat);
    }

    public double getAmountOfProducts() {
        SponsorService sponsorService = new SponsorService();
        try {
            // Assuming there's a method in SponsorService to get the number of products for a sponsor
            return sponsorService.getAmountOfProductsForSponsor(this); // Assuming 'this' is a Sponsor instance
        } catch (SQLException e) {
            System.err.println("Error fetching amount of products for sponsor: " + e.getMessage());
            return 0; // Return 0 in case of an error
        }
    }


}

