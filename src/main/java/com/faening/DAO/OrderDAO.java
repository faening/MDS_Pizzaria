package com.faening.DAO;

import com.faening.controller.CustomerController;
import com.faening.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private final Connection connection;

    private static final String SELECT_ALL_ORDERS = "SELECT * FROM tb_order";
    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM tb_order WHERE id = ?";
    private static final String INSERT_ORDER = "INSERT INTO tb_order (customer_id, amount, delivery_type, order_date) VALUES (?, ?, ?, ?)";

    public OrderDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ORDERS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Order order = extractOrderFromResultSet(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orders;
    }

    public Order getOrderById(long id) {
        Order order = null;

        try (PreparedStatement statement = connection.prepareStatement(SELECT_ORDER_BY_ID)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    order = extractOrderFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return order;
    }

    public long addOrder(Order order) {
        try {
            connection.setAutoCommit(false);

            // Inserir o pedido na tabela tb_order
            try (PreparedStatement orderStatement = connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
                orderStatement.setLong(1, order.getCustomer().getId());
                orderStatement.setFloat(2, order.getAmount());
                orderStatement.setString(3, order.getDeliveryType().toString());
                orderStatement.setTimestamp(4, new Timestamp(order.getOrderDate().getTime()));
                orderStatement.executeUpdate();

                // Obter o ID gerado
                try (ResultSet generatedKeys = orderStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long generatedId = generatedKeys.getLong(1);
                        order.setId(generatedId);
                    }
                }
            }

            // Inserir as pizzas associadas ao pedido
            insertPizzasForOrder(order);

            // Inserir as bebidas associadas ao pedido
            insertDrinksForOrder(order);

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException resetException) {
                throw new RuntimeException(resetException);
            }
        }

        return order.getId();
    }

    private Customer getCustomerById(long id) {
        Customer customer = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM tb_customer WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                customer = new Customer(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("phone"),
                        resultSet.getString("address")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customer;
    }

    private List<Pizza> getPizzasByOrderId(long orderId) {
        List<Pizza> pizzas = new ArrayList<>();

        try {
            String query = "SELECT p.* FROM tb_pizza p INNER JOIN tb_pizza_order po ON p.id = po.pizza_id WHERE po.order_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, orderId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Pizza pizza = new Pizza(
                                resultSet.getLong("id"),
                                resultSet.getString("description"),
                                resultSet.getFloat("price"),
                                resultSet.getString("ingredients")
                        );
                        pizzas.add(pizza);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return pizzas;
    }

    private List<Drink> getDrinksByOrderId(long orderId) {
        List<Drink> drinks = new ArrayList<>();

        try {
            String query = "SELECT d.* FROM tb_drink d INNER JOIN tb_drink_order do ON d.id = do.drink_id WHERE do.order_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, orderId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Drink drink = new Drink(
                                resultSet.getLong("id"),
                                resultSet.getString("description"),
                                resultSet.getFloat("price"),
                                resultSet.getString("packing_size")
                        );
                        drinks.add(drink);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return drinks;
    }

    private void insertPizzasForOrder(Order order) {
        try {
            String insertPizzaOrderQuery = "INSERT INTO tb_pizza_order (pizza_id, order_id) VALUES (?, ?)";
            for (Pizza pizza : order.getPizzas()) {
                try (PreparedStatement insertPizzaOrderStatement = connection.prepareStatement(insertPizzaOrderQuery)) {
                    insertPizzaOrderStatement.setLong(1, pizza.getId());
                    insertPizzaOrderStatement.setLong(2, order.getId());
                    insertPizzaOrderStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertDrinksForOrder(Order order) {
        try {
            String insertDrinkOrderQuery = "INSERT INTO tb_drink_order (drink_id, order_id) VALUES (?, ?)";
            for (Drink drink : order.getDrinks()) {
                try (PreparedStatement insertDrinkOrderStatement = connection.prepareStatement(insertDrinkOrderQuery)) {
                    insertDrinkOrderStatement.setLong(1, drink.getId());
                    insertDrinkOrderStatement.setLong(2, order.getId());
                    insertDrinkOrderStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean orderExists(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM tb_order WHERE id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    private Order extractOrderFromResultSet(ResultSet resultSet) throws SQLException {
        long orderId = resultSet.getLong("id");
        Customer customer = getCustomerById(resultSet.getLong("customer_id"));
        List<Pizza> pizzas = getPizzasByOrderId(orderId);
        List<Drink> drinks = getDrinksByOrderId(orderId);
        Float amount = resultSet.getFloat("amount");
        DeliveryType deliveryType = DeliveryType.valueOf(resultSet.getString("delivery_type"));
        java.sql.Timestamp orderDate = resultSet.getTimestamp("order_date");

        return new Order(orderId, customer, pizzas, drinks, amount, deliveryType, orderDate);
    }
}
