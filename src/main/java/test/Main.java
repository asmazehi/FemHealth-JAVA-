package test;

import models.Blog.Commentaire;
import models.Blog.Publication;



import service.Blog.CommentaireService;
import service.Blog.PublicationService;
import service.Sponsoring.ProduitService;
import service.Sponsoring.SponsorService;
import utils.MyDataBase;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        Connection connection = MyDataBase.getInstance().getConnection();

        System.out.println(connection);

        SponsorService sponsorService = new SponsorService();
        ProduitService produitService = new ProduitService();
        CommentaireService commentaireService= new CommentaireService();
        PublicationService publicationService = new PublicationService();
        try {
            // Test SponsorService
            //sponsorService.update(new Sponsor(4, "aaa", "16 mois"));
            //sponsorService.delete(4);
            //sponsorService.add(new Sponsor(4, "aaa", "16 mois"));
            //System.out.println(sponsorService.select());
        //} catch (SQLException e) {
          //  System.out.println(e.getMessage());        }
   // }
            //Publication publication = new Publication(6, "malajdhdgegteg", "https://upload.wikimedia.org/wikipedia/commons/b/b6/Image_created_with_a_mobile_phone.png", "health3" );

         /*   publicationService.update(new Publication(6,"mmmmmmm","https://www.industrialempathy.com/img/remote/ZiClJf-1920w.jpg", "health4"));
            publicationService.delete(11);
            System.out.println(publicationService.select());
            commentaireService.delete(3);
            //System.out.println(commentaireService.select());*/
            Publication publication = commentaireService.fetchPublicationById(6);
            System.out.println(publication);
            Commentaire commentaire = new Commentaire( );
            commentaire.setDescription("bfhrbfhrfkr");
            commentaire.setUser_id(3);
            commentaire.setPublication(publication);
            System.out.println(commentaire.getDescription());
            System.out.println(commentaire.getPublication());
            System.out.println(publication.getId());

            commentaireService.add(commentaire);

            System.out.println(produitService.select());
            } catch (SQLException e) {
             System.out.println(e.getMessage());        }}
}
