package models.events;
import java.util.Date;


public class Evenement {

    private int id;
    private Type type_id;
    private String nom;
    private Date dateDebut;
    private Date dateFin;
    private String image;
    private String localisation;
    private float montant;

    public Evenement() {
    }



    public Evenement(Type type_id,String nom, Date dateDebut, Date dateFin, String image, String localisation, float montant) {
        this.type_id=type_id;
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.image = image;
        this.localisation = localisation;
        this.montant = montant;
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

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public void setType_id(Type type_id) {
        this.type_id = type_id;
    }

    public Type getType_id() {
        return type_id;
    }

    public Evenement(int id, Type type_id, String nom, Date dateDebut, Date dateFin, String image, String localisation, float montant) {
        this.id = id;
        this.type_id = type_id;
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.image = image;
        this.localisation = localisation;
        this.montant = montant;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", type_id=" + type_id +
                ", nom='" + nom + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", image='" + image + '\'' +
                ", localisation='" + localisation + '\'' +
                ", montant=" + montant +
                '}';
    }
}
