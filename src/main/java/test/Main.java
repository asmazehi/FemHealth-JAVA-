package test;

import service.Sponsoring.SponsorService;
import service.Sponsoring.ProduitService;
import model.Sponsoring.Sponsor;
import model.Sponsoring.Produit;
import utils.MyDataBase;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Connection connection = MyDataBase.getInstance().getConnection();
        System.out.println(connection);
        SponsorService sponsorService = new SponsorService();
        ProduitService produitService = new ProduitService();
        try {
            // Test SponsorService
            //sponsorService.update(new Sponsor(4, "aaa", "16 mois"));
            //sponsorService.delete(4);
            //sponsorService.add(new Sponsor(4, "aaa", "16 mois"));
            //System.out.println(sponsorService.select());
        //} catch (SQLException e) {
          //  System.out.println(e.getMessage());        }
   // }
            // Test ProduitService
            produitService.update(new Produit(3, "produit1", 100.0f, 10, "categorie1", "image1", "description1", new Sponsor(7, "", "")));
            //produitService.delete(2);
            //produitService.add(new Produit(4, "produit4", 150.0f, 15, "categorie2", "image2", "description2", new Sponsor(7, "", "")));
            //produitService.select();
            System.out.println(produitService.select());
            } catch (SQLException e) {
             System.out.println(e.getMessage());        }}
}
