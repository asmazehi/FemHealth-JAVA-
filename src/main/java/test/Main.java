/*package test;

import service.Sponsoring.SponsorService;
import service.Sponsoring.ProduitService;
import models.Sponsoring.Sponsor;
import models.Sponsoring.Produit;
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
*/
package test;

import models.Evenement;
import models.Reservation;
import models.Type;
import service.EvenementC;
import service.ReservationC;
import service.TypeC;
import utils.MyDataBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Connection connection = MyDataBase.getInstance().getConnection();

        // Test des opérations CRUD sur les événements
        testEvenement();

        // Test des opérations CRUD sur les réservations
        testReservation();

        // Test des opérations CRUD sur les types
        testType();

        try {
            // Fermeture de la connexion à la base de données
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void testEvenement() {
        EvenementC evenementService = new EvenementC();

        // Affichage des événements avant ajout
        try {
            System.out.println("Liste des événements avant ajout :");
            List<Evenement> evenementsBefore = evenementService.select();
            for (Evenement evenement : evenementsBefore) {
                System.out.println(evenement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Ajout d'un nouveau type
        TypeC typeC = new TypeC();
        Type newType = new Type();
        newType.setType("Type test");
        try {
            typeC.add(newType);
            System.out.println("Type ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Ajout d'un nouvel événement avec le type associé
        Evenement newEvenement = new Evenement();
        Type type= new Type();
        type.setId(1);
        newEvenement.setType_id(newType); // Utilise l'objet Type
        newEvenement.setNom("Événement test");
        newEvenement.setDateDebut(new java.util.Date());
        newEvenement.setDateFin(new java.util.Date());
        newEvenement.setImage("event.jpg");
        newEvenement.setLocalisation("Location test");
        newEvenement.setMontant(50.0f);
        try {
            evenementService.add(newEvenement);
            System.out.println("Événement ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Affichage des événements après ajout
        try {
            System.out.println("Liste des événements après ajout :");
            List<Evenement> evenementsAfter = evenementService.select();
            for (Evenement evenement : evenementsAfter) {
                System.out.println(evenement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void testReservation() {
        ReservationC reservationC = new ReservationC();

        // Ajout d'une nouvelle réservation
        Reservation newReservation = new Reservation();
        Evenement evenement = new Evenement();
        evenement.setId(1); // Supposons qu'un événement avec l'ID 1 existe
        newReservation.setId_evenement_id(evenement);
        newReservation.setStatut_paiement("Payé");
        newReservation.setMode_paiement("Carte bancaire");
        try {
            reservationC.add(newReservation);
            System.out.println("Réservation ajoutée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Affichage des réservations après ajout
        try {
            System.out.println("Liste des réservations après ajout :");
            List<Reservation> reservations = reservationC.select();
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void testType() {
        TypeC typeC = new TypeC();

        // Ajout d'un nouveau type
        Type newType = new Type();
        newType.setType("Type test");
        try {
            typeC.add(newType);
            System.out.println("Type ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Affichage des types après ajout
        try {
            System.out.println("Liste des types après ajout :");
            List<Type> types = typeC.select();
            for (Type type : types) {
                System.out.println(type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}