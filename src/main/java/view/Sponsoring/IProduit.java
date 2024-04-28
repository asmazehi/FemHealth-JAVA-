package view.Sponsoring;
import java.util.List;
public interface IProduit <T>{
    void ajouter(T t);
    void modifier(int id,T t);
    void supprimer(int id);
    List<T> display();
}
