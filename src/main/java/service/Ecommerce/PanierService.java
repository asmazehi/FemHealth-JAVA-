package service.Ecommerce;


import model.Ecommerce.*;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PanierService implements IService<Panier> {

    Connection connection;
    public  PanierService(){
        connection= MyDataBase.getInstance().getConnection();
    }
    @Override
    public void add(Panier panier) throws SQLException {
        String sql = "INSERT INTO panier (client_id, prix_total, statut) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, panier.getIdUser());
        preparedStatement.setInt(2, panier.getPrixTotal());
        preparedStatement.setString(3, panier.getStatut());
        preparedStatement.executeUpdate();


    }

    @Override
    public void update(Panier panier) throws SQLException {
        String sql ="update panier set prix_total=?,statut=? where id=? ";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setInt(1, panier.getPrixTotal());
        preparedStatement.setString(2, panier.getStatut());
        preparedStatement.setInt(3,panier.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from panier where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }


    @Override
    public List<Panier> select() throws SQLException {
        List<Panier>paniers=new ArrayList<>();
        String sql="select * from panier";
        Statement statement=connection.createStatement();
        ResultSet resultSet= statement.executeQuery(sql);
        while (resultSet.next()){
            Panier panier =new Panier();
            panier.setId(resultSet.getInt("id"));
            panier.setIdUser(resultSet.getInt("client_id"));
            panier.setPrixTotal(resultSet.getInt("prix_total"));
            panier.setStatut(resultSet.getString("statut"));
            paniers.add(panier);
        }
        return paniers;
    }
}


