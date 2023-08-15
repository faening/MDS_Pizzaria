package com.faening.controller;

import com.faening.DAO.PizzaDAO;
import com.faening.model.Pizza;

import java.sql.Connection;
import java.util.List;

public final class PizzaController {
    private final PizzaDAO pizzaDAO;

    public PizzaController(Connection connection) {
        this.pizzaDAO = new PizzaDAO(connection);
    }

    public List<Pizza> getAllPizzas() {
        return pizzaDAO.getAllPizzas();
    }

    public Pizza getPizzaById(long id) {
        return pizzaDAO.getPizzaById(id);
    }

    public void addPizza(Pizza pizza) {
        pizzaDAO.addPizza(pizza);
    }

    public void updatePizza(Pizza pizza) {
        pizzaDAO.updatePizza(pizza);
    }

    public void deletePizza(long id) {
        pizzaDAO.deletePizza(id);
    }

    public boolean pizzaExists(long id) {
        return pizzaDAO.pizzaExists(id);
    }
}
