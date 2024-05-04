package test;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import model.Ecommerce.PanierItem;
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


        Stripe.apiKey = "sk_test_51Op589Hvqq7mfMH0fdKOSMMO2pf9QxdTW3Q6pBG13IVODxd9uudifpaL9KS2NgJEG5DyVC7nLFr3XYe5QRmiQa0C009i4gvPkC";



    }
    // Retrieve your account information
      /*  Account account = null;
        try {
            account = Account.retrieve();
        } catch (StripeException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println("Account ID: " + account.getId());
    }}*/


}