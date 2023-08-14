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
        System.out.println("\n--- Menu: Pizzas ---");
        boolean menuOpen = true;
        long menuOption;

        do {
            showAllPizzas();

            System.out.print(
                    "[1] Cadastrar \n" +
                    "[2] Consultar \n" +
                    "[3] Editar \n" +
                    "[4] Excluir \n" +
                    "[0] Voltar ao menu principal \n" +
                    "Digite a opção desejada: ");
            menuOption = scanner.nextInt();

            switch ((int) menuOption) {
                case 0 -> {
                    menuOpen = false;
                    System.out.println();
                }
                case 1 -> addPizza();
                case 2 -> ShowPizza();
                case 3 -> updatePizza();
                case 4 -> deletePizza();
                default -> System.out.println(Messages.INVALID_INPUT);
            }
        } while (menuOpen);
    }

    public void showAllPizzas() {
        System.out.println("Últimas pizzas cadastradas: ");
        List<Pizza> pizzas = pizzaController.getAllPizzas();

        for (Pizza pizza : pizzas) {
            System.out.println(pizza.toString());
        }
        System.out.println();
    }

    public void addPizza() {
        System.out.println("\n--- Menu: Pizzas -> Cadastrar ---");
        Pizza pizza = new Pizza();

        // Description
        String description = InputUtils.getValidStringInput(Messages.ENTER_PIZZA_DESCRIPTION);
        pizza.setDescription(description);

        // Price
        float price = InputUtils.getValidFloatInput(Messages.ENTER_PIZZA_PRICE);
        pizza.setPrice(price);

        // Ingredients
        String ingredients = InputUtils.getValidStringInput(Messages.ENTER_PIZZA_INGREDIENTS);
        pizza.setIngredients(ingredients);

        pizzaController.addPizza(pizza);
        System.out.println("Cadastro realizado com sucesso!");
    }

    public void ShowPizza() {
        System.out.println("\n--- Menu: Pizzas -> Consultar ---");
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
        System.out.println("\n--- Menu: Pizzas -> Editar ---");
        long pizzaId;
        Pizza pizza;

        do {
            pizzaId = InputUtils.getValidLongInput("Digite o código da pizza que você deseja editar: ");
            boolean pizzaExists = pizzaController.pizzaExists(pizzaId);

            if (pizzaExists)
                break;

            System.out.println(Messages.INVALID_OPTION);
        } while (true);

        pizza = pizzaController.getPizzaById(pizzaId);

        // Description
        String description = InputUtils.getValidStringInput(Messages.ENTER_PIZZA_DESCRIPTION);
        pizza.setDescription(description);

        // Price
        float price = InputUtils.getValidFloatInput(Messages.ENTER_PIZZA_PRICE);
        pizza.setPrice(price);

        // Ingredients
        String ingredients = InputUtils.getValidStringInput(Messages.ENTER_PIZZA_INGREDIENTS);
        pizza.setIngredients( ingredients );

        pizzaController.addPizza(pizza);
        System.out.println("Edição realizada com sucesso!");
    }

    public void deletePizza() {
        System.out.println("\n--- Menu: Pizzas -> Excluir ---");
        long pizzaId;

        do {
            pizzaId = InputUtils.getValidLongInput("Digite o código da pizza que você deseja excluir: ");
            boolean pizzaExists = pizzaController.pizzaExists(pizzaId);

            if (pizzaExists) {
                pizzaController.deletePizza(pizzaId);
                System.out.printf("A pizza id %d foi excluida!", pizzaId);
                break;
            }
        } while (true);
    }
}
