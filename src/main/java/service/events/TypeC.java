package service.events;

import utils.MyDataBase;
import model.events.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TypeC implements IType<Type> {
    Connection connection;

    public TypeC() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void add(Type type) throws SQLException {
        String sql = "INSERT INTO Type (type) VALUES (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, type.getType());
        preparedStatement.executeUpdate();
    }

    @Override
    public void update(Type type) throws SQLException {
        String sql = "UPDATE Type SET type=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, type.getType());
        preparedStatement.setInt(2, type.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Type WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Type> select() throws SQLException {
        List<Type> types = new ArrayList<>();
        String sql = "SELECT * FROM Type";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            Type type = new Type();
            type.setId(resultSet.getInt("id"));
            type.setType(resultSet.getString("type"));
            types.add(type);
        }
        return types;
    }
    public Type selectById(int typeId) throws SQLException {
        String sql = "SELECT * FROM Type WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, typeId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Type type = new Type();
            type.setId(resultSet.getInt("id"));
            type.setType(resultSet.getString("type"));
            // Set other properties of the Type object if needed
            return type;
        }
        return null; // Return null if no Type with the given ID was found
    }
    public Type selectByName(String typeName) throws SQLException {
        String sql = "SELECT * FROM Type WHERE type=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, typeName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Type type = new Type();
            type.setId(resultSet.getInt("id"));
            type.setType(resultSet.getString("type"));
            // Set other properties of the Type object if needed
            return type;
        }
        return null; // Return null if no Type with the given name was found
    }
    public List<String> getAllTypeNames() throws SQLException {
        List<String> typeNames = new ArrayList<>();
        String sql = "SELECT type FROM Type";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                typeNames.add(resultSet.getString("type"));
            }
        }
        return typeNames;
    }


}
