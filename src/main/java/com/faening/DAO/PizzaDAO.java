package com.faening.DAO;

import com.faening.model.Pizza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class PizzaDAO {
    private final Connection connection;

    private static final String SELECT_ALL_PIZZAS = "SELECT * FROM tb_pizza";
    private static final String SELECT_PIZZA_BY_ID = "SELECT * FROM tb_pizza WHERE id = ?";
    private static final String INSERT_PIZZA = "INSERT INTO tb_pizza (description, price, ingredients) VALUES (?, ?, ?)";
    private static final String UPDATE_PIZZA = "UPDATE tb_pizza SET description = ?, price = ?, ingredients = ? WHERE id = ?";
    private static final String DELETE_PIZZA = "DELETE FROM tb_pizza WHERE id = ?";
    private static final String CHECK_PIZZA_EXISTS = "SELECT COUNT(*) FROM tb_pizza WHERE id = ?";

    public PizzaDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Pizza> getAllPizzas() {
        List<Pizza> pizzas = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PIZZAS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Pizza pizza = extractPizzaFromResultSet(resultSet);
                pizzas.add(pizza);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return pizzas;
    }

    public Pizza getPizzaById(long id) {
        Pizza pizza = null;

        try (PreparedStatement statement = connection.prepareStatement(SELECT_PIZZA_BY_ID)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    pizza = extractPizzaFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return pizza;
    }

    public void addPizza(Pizza pizza) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_PIZZA)) {
            statement.setString(1, pizza.getDescription());
            statement.setFloat(2, pizza.getPrice());
            statement.setString(3, pizza.getIngredients());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePizza(Pizza pizza) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PIZZA)) {
            statement.setString(1, pizza.getDescription());
            statement.setFloat(2, pizza.getPrice());
            statement.setString(3, pizza.getIngredients());
            statement.setLong(4, pizza.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePizza(long id) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_PIZZA)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean pizzaExists(long id) {
        try (PreparedStatement statement = connection.prepareStatement(CHECK_PIZZA_EXISTS)) {
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

    private Pizza extractPizzaFromResultSet(ResultSet resultSet) throws SQLException {
        Pizza pizza = new Pizza();
        pizza.setId(resultSet.getLong("id"));
        pizza.setDescription(resultSet.getString("description"));
        pizza.setPrice(resultSet.getFloat("price"));
        pizza.setIngredients(resultSet.getString("ingredients"));
        return pizza;
    }
}
