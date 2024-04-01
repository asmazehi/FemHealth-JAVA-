/*package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import models.events.Evenement;
import models.events.Reservation;
import models.events.Type;
import service.events.EvenementC;
import service.events.ReservationC;
import service.events.TypeC;
import service.Sponsoring.SponsorService;
import service.Sponsoring.ProduitService;
import models.Sponsoring.Sponsor;
import models.Sponsoring.Produit;
import utils.MyDataBase;



public class Main {

    public static void main(String[] args) {
        Connection connection = MyDataBase.getInstance().getConnection();

        System.out.println(connection);
         EvenementC evenementService = new EvenementC();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

       // try {
            // Parse date strings into Date objects
           // Date dateDebut = dateFormat.parse("2024-04-01");
            //Date dateFin = dateFormat.parse("2024-04-15");

            // Add a new Evenement with manually specified dates
            //evenementService.add(new Evenement(new Type(3, ""), "Evenement1", dateDebut, dateFin, "image.jpg", "Location1", 100.0f));

            // Update an existing Evenement (Assuming ID 1 exists in the database)
            // evenementService.update(new Evenement(1, new Type(1, "Type1"), "UpdatedEvenement", dateDebut, dateFin, "image.jpg", "UpdatedLocation", 200.0f));

            // Delete an Evenement by ID (Assuming ID 1 exists in the database)
             //evenementService.delete(16);

            // Select and print all Evenements
            //List<Evenement> evenements = evenementService.select();
            //for (Evenement evenement : evenements) {
              //  System.out.println(evenement);
            //}
        //} catch (SQLException | ParseException e) {
            //System.out.println(e.getMessage());
       // }
   // }
            ReservationC reservationService = new ReservationC();

            try {
                Date dateDebut = dateFormat.parse("2024-04-01");
                Date dateFin = dateFormat.parse("2024-04-15");
                // Add a new Reservation
                reservationService.add(new Reservation(1, new Evenement(1, new Type(1, ""), "", dateDebut, dateFin , "", "", 100.0f), "Paid", "Credit Card"));

                // Update an existing Reservation (Assuming ID 1 exists in the database)
                 //reservationService.update(new Reservation(22, new Evenement(2, new Type(1, ""), "Evenement1", dateDebut, dateFin, "", "", 100.0f), "Not Paid", "Credit Card"));

                // Delete a Reservation by ID (Assuming ID 1 exists in the database)
                // reservationService.delete(22);

                // Select and print all Reservations
               List<Reservation> reservations = reservationService.select();
                for (Reservation reservation : reservations) {
                    System.out.println(reservation);
                }
           } catch (SQLException | ParseException e) {
                System.out.println(e.getMessage());
           }

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




