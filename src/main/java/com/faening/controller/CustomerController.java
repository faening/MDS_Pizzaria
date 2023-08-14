package com.faening.controller;

import com.faening.DAO.CustomerDAO;
import com.faening.model.Customer;

import java.sql.Connection;
import java.util.List;

public final class CustomerController {
    private final CustomerDAO customerDAO;

    public CustomerController(Connection connection) {
        this.customerDAO = new CustomerDAO(connection);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    public Customer getCustomerById(long id) {
        return customerDAO.getCustomerById(id);
    }

    public void addCustomer(Customer customer) {
        customerDAO.addCustomer(customer);
    }

    public void updateCustomer(Customer customer) {
        customerDAO.updateCustomer(customer);
    }

    public void deleteCustomer(long id) {
        customerDAO.deleteCustomer(id);
    }

    public boolean customerExists(long customerId) {
        return customerDAO.customerExists(customerId);
    }
}
