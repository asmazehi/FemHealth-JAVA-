package model.Ecommerce;

public class Lignepanier {
    private int id;
    private int quantité;
    private int idProduit;
    private int idPanier;
public Lignepanier(){}
    public Lignepanier(int id, int quantité, int idProduit, int idPanier) {
        this.id = id;
        this.quantité = quantité;
        this.idProduit = idProduit;
        this.idPanier = idPanier;
    }
    public Lignepanier( int quantité, int idProduit, int idPanier) {
        this.quantité = quantité;
        this.idProduit = idProduit;
        this.idPanier = idPanier;
    }


    @Override
    public String toString() {
        return "lignepanier{" +
                "id=" + id +
                ", quantité=" + quantité +
                ", idProduit=" + idProduit +
                ", idPanier=" + idPanier +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantité() {
        return quantité;
    }

    public void setQuantité(int quantité) {
        this.quantité = quantité;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(int idPanier) {
        this.idPanier = idPanier;
    }
}
