package models.Blog;
import java.util.Objects;
import java.util.Date;
public class Publication {
    private int id ;
    private String contenu ;
    private String image ;
    private Date datepub ;
    private String titre ;

    public Publication() {
    }

    public Publication(int id, String contenu, String image, String titre) {
        this.id = id;
        this.contenu = contenu;
        this.image = image;
        this.datepub = new Date();
        this.titre = titre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public java.sql.Date getDatepub() {
        return (java.sql.Date) datepub;
    }

    public java.sql.Date setDatepub() {
        this.datepub = new Date();;
        return null;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "id=" + id +
                ", contenu='" + contenu + '\'' +
                ", image='" + image + '\'' +
                ", datepub=" + datepub +
                ", titre='" + titre + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Publication that)) return false;
        return getId() == that.getId();
    }


    @Override
    public int hashCode() {
        return Objects.hash(getId(), getContenu(), getImage(), getTitre());
    }
}
