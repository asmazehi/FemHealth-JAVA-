package model.Ecommerce;

public class Panier {
    private int id;
    private int idUser;
    private int prixTotal;
    private String statut;
    public Panier(){}
    public Panier(int id, int idUser, int prixTotal, String statut) {
        this.id = id;
        this.idUser = idUser;
        this.prixTotal = prixTotal;
        this.statut = statut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(int prixTotal) {
        this.prixTotal = prixTotal;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", prixTotal=" + prixTotal +
                ", statut='" + statut + '\'' +
                '}';
    }
}
