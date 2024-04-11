package service.Ecommerce;
import model.Ecommerce.Lignepanier;
import model.Ecommerce.Panier;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class LignepanierService implements IService<Lignepanier>{
    Connection connection;
    public  LignepanierService(){
        connection= MyDataBase.getInstance().getConnection();
    }

    @Override
    public void add(Lignepanier lignepanier) throws SQLException {
        String sql = "INSERT INTO lignepanier (panier_id, produit_id, quantite) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, lignepanier.getIdPanier());
        preparedStatement.setInt(2, lignepanier.getIdProduit());
        preparedStatement.setInt(3, lignepanier.getQuantité());
        preparedStatement.executeUpdate();
    }

    @Override
    public void update(Lignepanier lignepanier) throws SQLException {
        String sql ="update lignepanier set quantite=? where id=? ";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setInt(1, lignepanier.getQuantité());
        preparedStatement.setInt(2,lignepanier.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public List<Lignepanier> select() throws SQLException {
        List<Lignepanier>lignepaniers=new ArrayList<>();
        String sql="select * from lignepanier";
        Statement statement=connection.createStatement();
        ResultSet resultSet= statement.executeQuery(sql);
        while (resultSet.next()){
            Lignepanier lignepanier =new Lignepanier();
            lignepanier.setId(resultSet.getInt("id"));
            lignepanier.setQuantité(resultSet.getInt("quantite"));
            lignepaniers.add(lignepanier);
        }
        return lignepaniers;
    }
    }

