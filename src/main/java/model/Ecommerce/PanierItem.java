package model.Ecommerce;

public class PanierItem {
    private int idpanier;
    private int idproduit;
    private String nomProduit;
    private float prixUnitaire;
    private int quantite;
    private float totalProduit;
    private float totalpanier;
    private String status;

    public PanierItem(int idpanier, int idproduit, String nomProduit, float prixUnitaire,int quantite, float totalpanier, String status) {
        this.idpanier = idpanier;
        this.idproduit = idproduit;
        this.prixUnitaire=prixUnitaire;
        this.nomProduit = nomProduit;
        this.quantite = quantite;
        this.totalProduit=prixUnitaire*quantite;
        this.totalpanier = totalpanier;
        this.status = status;}

    public int getIdpanier() {
        return idpanier;
    }

    public void setIdpanier(int idpanier) {
        this.idpanier = idpanier;
    }

    public int getIdproduit() {
        return idproduit;
    }

    public void setIdproduit(int idproduit) {
        this.idproduit = idproduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public float getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(float prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public float getTotalProduit() {
        return totalProduit;
    }

    public void setTotalProduit(float totalProduit) {
        this.totalProduit = totalProduit;
    }

    public float getTotalpanier() {
        return totalpanier;
    }

    public void setTotalpanier(float totalpanier) {
        this.totalpanier = totalpanier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}