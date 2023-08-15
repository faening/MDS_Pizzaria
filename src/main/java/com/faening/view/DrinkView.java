package com.faening.view;

import com.faening.controller.DrinkController;
import com.faening.model.Drink;
import com.faening.utils.InputUtils;
import com.faening.utils.Messages;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class DrinkView {
    private final DrinkController drinkController;
    private final static Scanner scanner = new Scanner(System.in);

    public DrinkView(Connection connection) {
        this.drinkController = new DrinkController(connection);
    }

    public void showMenu() {
        boolean menuOpen = true;
        long menuOption;

        do {
            System.out.println("\n" + Messages.APP_MENU + " > " + Messages.APP_MENU_DRINK);
            showAllDrinks();

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
                case 1 -> addDrink();
                case 2 -> showDrink();
                case 3 -> updateDrink();
                case 4 -> deleteDrink();
                default -> System.out.println(Messages.INVALID_INPUT);
            }
        } while (menuOpen);
    }

    public void showAllDrinks() {
        List<Drink> drinks = drinkController.getAllDrinks();

        for (Drink drink : drinks) {
            System.out.println(drink.toString());
        }
        System.out.println();
    }

    public void addDrink() {
        System.out.println(
            "\n" + Messages.APP_MENU + " > " + Messages.APP_MENU_DRINK + " > " + Messages.APP_MENU_CREATE);
        Drink drink = new Drink();

        String description = InputUtils.getValidStringInput(Messages.ENTER_DRINK_DESCRIPTION);
        drink.setDescription( description );

        float price = InputUtils.getValidFloatInput(Messages.ENTER_DRINK_PRICE);
        drink.setPrice(price);

        String packingSize = InputUtils.getValidStringInput(Messages.ENTER_DRINK_PACKING_SIZE);
        drink.setPackingSize(packingSize);

        drinkController.addDrink(drink);
        System.out.println(Messages.SUCCESSFUL);
    }

    public void showDrink() {
        System.out.println(
            "\n" + Messages.APP_MENU + " > " + Messages.APP_MENU_DRINK + " > " + Messages.APP_MENU_READ);
        long drinkId;

        do {
            drinkId = InputUtils.getValidLongInput("Digite o código da bebida: ");
            boolean drinkExists = drinkController.drinkExists(drinkId);

            if (drinkExists) {
                System.out.println(drinkController.getDrinkById(drinkId).toString());
                break;
            }
        } while (true);
    }

    public void updateDrink() {
        System.out.println(
            "\n" + Messages.APP_MENU + " > " + Messages.APP_MENU_DRINK + " > " + Messages.APP_MENU_UPDATE);
        long drinkId;
        Drink drink;

        do {
            drinkId = InputUtils.getValidLongInput("Digite o código da bebida que você quer editar: ");
            boolean drinkExists = drinkController.drinkExists(drinkId);

            if (drinkExists) {
                drink = drinkController.getDrinkById(drinkId);
                break;
            }

            System.out.println(Messages.INVALID_OPTION);
        } while (true);

        String description = InputUtils.getValidStringInput(Messages.ENTER_DRINK_DESCRIPTION);
        drink.setDescription( description );

        float price = InputUtils.getValidFloatInput(Messages.ENTER_DRINK_PRICE);
        drink.setPrice(price);

        String packingSize = InputUtils.getValidStringInput(Messages.ENTER_DRINK_PACKING_SIZE);
        drink.setPackingSize(packingSize);

        drinkController.updateDrink(drink);
        System.out.println(Messages.SUCCESSFUL);
    }

    public void deleteDrink() {
        System.out.println(
            "\n" + Messages.APP_MENU + " > " + Messages.APP_MENU_DRINK + " > " + Messages.APP_MENU_DELETE);
        long drinkId;

        do {
            drinkId = InputUtils.getValidLongInput("Digite o código da bebida que você deseja excluir: ");
            boolean drinkExists = drinkController.drinkExists(drinkId);

            if (drinkExists) {
                drinkController.deleteDrink(drinkId);
                System.out.println(Messages.SUCCESSFUL);
                break;
            }

            System.out.println(Messages.INVALID_OPTION);
        } while (true);
    }
}
