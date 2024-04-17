package test;

import model.Ecommerce.*;
import service.Ecommerce.*;
import service.Sponsoring.SponsorService;
import service.Sponsoring.ProduitService;
import model.Sponsoring.Sponsor;
import model.Sponsoring.Produit;
import utils.MyDataBase;
import service.Ecommerce.LignepanierService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Connection connection = MyDataBase.getInstance().getConnection();

        System.out.println(connection);

        SponsorService sponsorService = new SponsorService();
        ProduitService produitService = new ProduitService();

        //try {
            // Test SponsorService
            //sponsorService.update(new Sponsor(4, "aaa", "16 mois"));
            //sponsorService.delete(4);
            //sponsorService.add(new Sponsor(4, "aaa", "16 mois"));
            //System.out.println(sponsorService.select());
            //} catch (SQLException e) {
            //  System.out.println(e.getMessage());        }
            // }

            // Test ProduitService
            //produitService.update(new Produit(3, "produit1", 100.0f, 10, "categorie1", "image1", "description1", new Sponsor(7, "", "")));
            //produitService.delete(2);
            //produitService.add(new Produit(4, "produit4", 150.0f, 15, "categorie2", "image2", "description2", new Sponsor(7, "", "")));
            //produitService.select();
            //System.out.println(produitService.select());
            //} catch (SQLException e) {
            //  System.out.println(e.getMessage());        }*/


            PanierService panierService = new PanierService();

            int idPanier = 4; // Remplacez 5 par l'ID du panier que vous souhaitez afficher

            List<PanierItem> infosPanier = panierService.afficherinfopanier(idPanier);
            System.out.println(infosPanier.size());
            float totalpanierDt = infosPanier.get(1).getTotalpanier();
            String totalpanierDtString = String.valueOf(totalpanierDt);
            float totalpanierApresDT = infosPanier.get(1).getTotalpanier() + 10;
            String totalpanierApresDtString = String.valueOf(totalpanierApresDT);
            System.out.println(totalpanierApresDtString);
            System.out.println(totalpanierDtString);
            for (PanierItem info : infosPanier) {

                int idpanier = info.getIdpanier();
                System.out.println("idpanier : " + info.getIdpanier());

                int idproduit = info.getIdproduit();
                System.out.println("produit: " + info.getIdproduit());

                int quantite = info.getQuantite();
                System.out.println("Quantité : " + info.getQuantite());

                float prix = info.getPrixUnitaire();
                System.out.println("Prix : " + prix);

                String nomProduit = info.getNomProduit();
                System.out.println("Nom du produit : " + nomProduit);
            }

        LignepanierService lignepanierService = new LignepanierService();
        try {
            lignepanierService.delete(1, 2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Lignepanier ligne=new Lignepanier(1,3,5);



    }}
