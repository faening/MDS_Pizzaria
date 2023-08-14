package com.faening.DAO;

import com.faening.model.Drink;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class DrinkDAO {
    private final Connection connection;

    private static final String SELECT_ALL_DRINKS = "SELECT * FROM tb_drink";
    private static final String SELECT_DRINK_BY_ID = "SELECT * FROM tb_drink WHERE id = ?";
    private static final String INSERT_DRINK = "INSERT INTO tb_drink (description, price, packing_size) VALUES (?, ?, ?)";
    private static final String UPDATE_DRINK = "UPDATE tb_drink SET description = ?, price = ?, packing_size = ? WHERE id = ?";
    private static final String DELETE_DRINK = "DELETE FROM tb_drink WHERE id = ?";
    private static final String CHECK_DRINK_EXISTS = "SELECT COUNT(*) FROM tb_drink WHERE id = ?";

    public DrinkDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Drink> getAllDrinks() {
        List<Drink> drinks = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_DRINKS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Drink drink = extractDrinkFromResultSet(resultSet);
                drinks.add(drink);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return drinks;
    }

    public Drink getDrinkById(long id) {
        Drink drink = null;

        try (PreparedStatement statement = connection.prepareStatement(SELECT_DRINK_BY_ID)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    drink = extractDrinkFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return drink;
    }

    public void addDrink(Drink drink) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_DRINK)) {
            statement.setString(1, drink.getDescription());
            statement.setFloat(2, drink.getPrice());
            statement.setString(3, drink.getPackingSize());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateDrink(Drink drink) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_DRINK)) {
            statement.setString(1, drink.getDescription());
            statement.setFloat(2, drink.getPrice());
            statement.setString(3, drink.getPackingSize());
            statement.setLong(4, drink.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteDrink(long id) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_DRINK)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean drinkExists(long id) {
        try (PreparedStatement statement = connection.prepareStatement(CHECK_DRINK_EXISTS)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    private Drink extractDrinkFromResultSet(ResultSet resultSet) throws SQLException {
        Drink drink = new Drink();
        drink.setId(resultSet.getLong("id"));
        drink.setDescription(resultSet.getString("description"));
        drink.setPrice(resultSet.getFloat("price"));
        drink.setPackingSize(resultSet.getString("packing_size"));
        return drink;
    }
}
