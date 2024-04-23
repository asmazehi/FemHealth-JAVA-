package view.Sponsoring;
import java.util.List;
public interface ISponsor <T>{
    void ajouter(T t);
    void modifier(int id,T t);
    void supprimer(int id);
    List<T> display();
}
