package com.faening.controller;

import com.faening.DAO.DrinkDAO;
import com.faening.model.Drink;

import java.sql.Connection;
import java.util.List;

public final class DrinkController {
    private final DrinkDAO drinkDAO;

    public DrinkController(Connection connection) {
        this.drinkDAO = new DrinkDAO(connection);
    }

    public List<Drink> getAllDrinks() {
        return drinkDAO.getAllDrinks();
    }

    public Drink getDrinkById(long id) {
        return drinkDAO.getDrinkById(id);
    }

    public void addDrink(Drink drink) {
        drinkDAO.addDrink(drink);
    }

    public void updateDrink(Drink drink) {
        drinkDAO.updateDrink(drink);
    }

    public void deleteDrink(long id) {
        drinkDAO.deleteDrink(id);
    }

    public boolean drinkExists(long id) {
        return drinkDAO.drinkExists(id);
    }
}
