package com.faening.view;

import com.faening.controller.PizzaController;
import com.faening.model.Pizza;
import com.faening.utils.InputUtils;
import com.faening.utils.Messages;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public final class PizzaView {
    private final PizzaController pizzaController;
    private final static Scanner scanner = new Scanner(System.in);

    public PizzaView(Connection connection) {
        this.pizzaController = new PizzaController(connection);
    }

    public void showMenu() {
        boolean menuOpen = true;
        long menuOption;

        do {
            System.out.println("\n" + Messages.APP_MENU + " > " + Messages.APP_MENU_PIZZA);
            showAllPizzas();

            System.out.print(
                "[1] " + Messages.APP_MENU_CREATE + "   " +
                "[2] " + Messages.APP_MENU_READ + "   " +
                "[3] " + Messages.APP_MENU_UPDATE + "   " +
                "[4] " + Messages.APP_MENU_DELETE + "   " +
                "[0] " + Messages.APP_MENU_RETURN + "\n" +
                "Digite a opção desejada: ");
            menuOption = scanner.nextInt();

            switch ((int) menuOption) {
                case 0 -> {
                    menuOpen = false;
                    System.out.println();
                }
                case 1 -> addPizza();
                case 2 -> showPizza();
                case 3 -> updatePizza();
                case 4 -> deletePizza();
                default -> System.out.println(Messages.INVALID_INPUT);
            }
        } while (menuOpen);
    }

    public void showAllPizzas() {
        List<Pizza> pizzas = pizzaController.getAllPizzas();

        for (Pizza pizza : pizzas) {
            System.out.println(pizza.toString());
        }
        System.out.println();
    }

    public void addPizza() {
        System.out.println(
            "\n" + Messages.APP_MENU + " > " + Messages.APP_MENU_PIZZA + " > " + Messages.APP_MENU_CREATE);
        Pizza pizza = new Pizza();

        String description = InputUtils.getValidStringInput(Messages.ENTER_PIZZA_DESCRIPTION);
        pizza.setDescription(description);

        float price = InputUtils.getValidFloatInput(Messages.ENTER_PIZZA_PRICE);
        pizza.setPrice(price);

        String ingredients = InputUtils.getValidStringInput(Messages.ENTER_PIZZA_INGREDIENTS);
        pizza.setIngredients(ingredients);

        pizzaController.addPizza(pizza);
        System.out.println(Messages.SUCCESSFUL);
    }

    public void showPizza() {
        System.out.println(
            "\n" + Messages.APP_MENU + " > " + Messages.APP_MENU_PIZZA + " > " + Messages.APP_MENU_READ);
        long pizzaId;

        do {
            pizzaId = InputUtils.getValidLongInput("Digite o código da pizza: ");
            boolean pizzaExists = pizzaController.pizzaExists(pizzaId);

            if (pizzaExists) {
                System.out.println(pizzaController.getPizzaById(pizzaId).toString());
                break;
            }

        } while (true);
    }

    public void updatePizza() {
        System.out.println(
            "\n" + Messages.APP_MENU + " > " + Messages.APP_MENU_PIZZA + " > " + Messages.APP_MENU_UPDATE);
        long pizzaId;
        Pizza pizza;

        do {
            pizzaId = InputUtils.getValidLongInput("Digite o código da pizza que você deseja editar: ");
            boolean pizzaExists = pizzaController.pizzaExists(pizzaId);

            if (pizzaExists) {
                pizza = pizzaController.getPizzaById(pizzaId);
                break;
            }

            System.out.println(Messages.INVALID_OPTION);
        } while (true);

        String description = InputUtils.getValidStringInput(Messages.ENTER_PIZZA_DESCRIPTION);
        pizza.setDescription(description);

        float price = InputUtils.getValidFloatInput(Messages.ENTER_PIZZA_PRICE);
        pizza.setPrice(price);

        String ingredients = InputUtils.getValidStringInput(Messages.ENTER_PIZZA_INGREDIENTS);
        pizza.setIngredients( ingredients );

        pizzaController.updatePizza(pizza);
        System.out.println(Messages.SUCCESSFUL);
    }

    public void deletePizza() {
        System.out.println(
            "\n" + Messages.APP_MENU + " > " + Messages.APP_MENU_PIZZA + " > " + Messages.APP_MENU_DELETE);
        long pizzaId;

        do {
            pizzaId = InputUtils.getValidLongInput("Digite o código da pizza que você deseja excluir: ");
            boolean pizzaExists = pizzaController.pizzaExists(pizzaId);

            if (pizzaExists) {
                pizzaController.deletePizza(pizzaId);
                System.out.println(Messages.SUCCESSFUL);
                break;
            }

            System.out.println(Messages.INVALID_OPTION);
        } while (true);
    }
}
