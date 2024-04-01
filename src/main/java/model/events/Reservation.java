package model.events;
import model.events.Evenement;

public class Reservation {
    private int id;
    private Evenement id_evenement_id;
    private String statut_paiement;
    private String mode_paiement;

    public Reservation() {
    }

    public Reservation(int id, Evenement id_evenement_id, String statut_paiement, String mode_paiement) {
        this.id = id;
        this.id_evenement_id = id_evenement_id;
        this.statut_paiement = statut_paiement;
        this.mode_paiement = mode_paiement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Evenement getId_evenement_id() {
        return id_evenement_id;
    }

    public void setId_evenement_id(Evenement id_evenement_id) {
        this.id_evenement_id = id_evenement_id;
    }

    public String getStatut_paiement() {
        return statut_paiement;
    }

    public void setStatut_paiement(String statut_paiement) {
        this.statut_paiement = statut_paiement;
    }

    public String getMode_paiement() {
        return mode_paiement;
    }

    public void setMode_paiement(String mode_paiement) {
        this.mode_paiement = mode_paiement;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", id_evenement_id=" + id_evenement_id +
                ", statut_paiement='" + statut_paiement + '\'' +
                ", mode_paiement='" + mode_paiement + '\'' +
                '}';
    }
}
