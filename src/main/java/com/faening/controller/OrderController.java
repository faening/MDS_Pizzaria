package com.faening.controller;

import com.faening.DAO.OrderDAO;
import com.faening.model.Customer;
import com.faening.model.Order;

import java.sql.Connection;
import java.util.List;

public final class OrderController {
    private final OrderDAO orderDAO;

    public OrderController(Connection connection) {
        this.orderDAO = new OrderDAO(connection);
    }

    public List<Order> getAllOrders() {
        return this.orderDAO.getAllOrders();
    }

    public Order getOrderById(long id) {
        return orderDAO.getOrderById(id);
    }

    public long addOrder(Order order) {
        return this.orderDAO.addOrder(order);
    }

    public boolean orderExists(long id) { return orderDAO.orderExists(id); }
}
