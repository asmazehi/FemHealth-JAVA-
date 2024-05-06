package model.Blog;

import model.User.Utilisateur;

import java.util.Date;
import java.util.Objects;

public class Commentaire {
    private int id;
    private Publication publication;
    private Utilisateur user_id;
    private String description;
    private Date datecomnt;
    private boolean active;
 private  int brlike;

    public int getBrlike() {
        return brlike;
    }

    public void setBrlike(int brlike) {
        this.brlike = brlike;
    }

    public Commentaire() {}

    public Commentaire(Publication publication, Utilisateur user_id, String description) {
        this.publication = publication;
        this.user_id = user_id;
        this.description = description;
        this.datecomnt = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public Utilisateur getUser_id() {
        return user_id;
    }

    public void setUser_id(Utilisateur user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.util.Date getDatecomnt() {
        return (java.util.Date) datecomnt;
    }

    public void setDatecomnt(Date datecomnt) {
        this.datecomnt = datecomnt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                ", publication=" + publication +
                ", user_id=" + user_id +
                ", description='" + description + '\'' +
                ", datecomnt=" + datecomnt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commentaire that)) return false;
        return getId() == that.getId() && getUser_id() == that.getUser_id() && isActive() == that.isActive() && Objects.equals(getPublication(), that.getPublication()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getDatecomnt(), that.getDatecomnt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPublication(), getUser_id(), getDescription(), getDatecomnt(), isActive());
    }
}
