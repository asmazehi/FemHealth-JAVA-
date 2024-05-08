package utils;
import model.User.Utilisateur;

public class Session {

    private Utilisateur user ;
    private static Session Instance ;

    private Session(Utilisateur user) {
        this.user = user;
    }
    public static Session StartSession(Utilisateur user) {
        if (Instance == null) {
            Instance = new Session(user);
        }
        return Instance;
    }
    public Utilisateur getUser() {
        return user;
    }
    public static Session  getSession(){
        return Instance;
    }
    public static void clearSession(){

        Instance = null;

    }


}
