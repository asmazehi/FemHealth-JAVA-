package utils;
import java.util.Random;

public class VerificationCodeGenerator {

    public static String generateCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Générer un nombre aléatoire à 6 chiffres
        return String.valueOf(code);
    }
}
