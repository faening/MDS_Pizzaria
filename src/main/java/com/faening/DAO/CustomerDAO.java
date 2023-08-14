package com.faening.DAO;

import com.faening.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class CustomerDAO {
    private final Connection connection;

    private static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM tb_customer";
    private static final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM tb_customer WHERE id = ?";
    private static final String INSERT_CUSTOMER = "INSERT INTO tb_customer (name, phone, address) VALUES (?, ?, ?)";
    private static final String UPDATE_CUSTOMER = "UPDATE tb_customer SET name = ?, phone = ?, address = ? WHERE id = ?";
    private static final String DELETE_CUSTOMER = "DELETE FROM tb_customer WHERE id = ?";
    private static final String CHECK_CUSTOMER_EXISTS = "SELECT COUNT(*) FROM tb_customer WHERE id = ?";

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CUSTOMERS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Customer customer = extractCustomerFromResultSet(resultSet);
                customers.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customers;
    }

    public Customer getCustomerById(long id) {
        Customer customer = null;

        try (PreparedStatement statement = connection.prepareStatement(SELECT_CUSTOMER_BY_ID)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    customer = extractCustomerFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customer;
    }

    public void addCustomer(Customer customer) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CUSTOMER)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getPhone());
            statement.setString(3, customer.getAddress());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCustomer(Customer customer) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CUSTOMER)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getPhone());
            statement.setString(3, customer.getAddress());
            statement.setLong(4, customer.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCustomer(long id) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CUSTOMER)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean customerExists(long id) {
        try (PreparedStatement statement = connection.prepareStatement(CHECK_CUSTOMER_EXISTS)) {
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

    private Customer extractCustomerFromResultSet(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();
        customer.setId(resultSet.getLong("id"));
        customer.setName(resultSet.getString("name"));
        customer.setPhone(resultSet.getString("phone"));
        customer.setAddress(resultSet.getString("address"));
        return customer;
    }
}
