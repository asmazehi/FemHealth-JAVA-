package utils;

import org.mindrot.jbcrypt.BCrypt;


public class PasswordUtils {
    public static String hashPasswrd(String password){
        return BCrypt.hashpw(password,BCrypt.gensalt(13));
    }
    public static Boolean verifyPassword(String Candidatepassword,String CryptedPaswword){
        return BCrypt.checkpw(Candidatepassword,CryptedPaswword);
    }
}
