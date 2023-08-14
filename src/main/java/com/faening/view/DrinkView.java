package com.faening.view;

import com.faening.controller.DrinkController;
import com.faening.model.Drink;

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
        List<Drink> drinks;
        boolean openedMenu = true;

        do {
            System.out.println("\n--- Menu: Bebidas ---");
            drinks = drinkController.getAllDrinks();
            drinks.forEach(drink -> System.out.println(drink.toString()));

            System.out.print("\n[1] Cadastrar \n" +
                             "[2] Consultar \n" +
                             "[3] Editar \n" +
                             "[4] Excluir \n" +
                             "[0] Voltar \n" +
                             "Digite a opção desejada: ");
            int optionSelected = scanner.nextInt();
            scanner.nextLine();

            switch (optionSelected) {
                case 0 -> {
                    openedMenu = false;
                    System.out.println();
                }
                case 1 -> drinkController.addDrink( addDrink() );
                case 2 -> System.out.println( drinkController.getDrinkById( showDrink() ) );
                case 3 -> drinkController.updateDrink( updateDrink() );
                case 4 -> drinkController.deleteDrink( deleteDrink() );
                default -> System.out.println("Opção inválida!");
            }
        } while (openedMenu);
    }

    public Drink addDrink() {
        System.out.println("\n--- Menu: Bebidas -> Cadastrar ---");
        Drink drink = new Drink();

        do {
            System.out.print("Descrição (Ex: Fanta Laranja): ");
            String description = scanner.nextLine().trim();

            if (!description.isEmpty()) {
                drink.setDescription( description );
                break;
            }

            System.out.println("Por favor, informe uma descrição correta! \n");
        } while (true);

        do {
            System.out.print("Preço (Ex: 3.5): ");
            String input = scanner.nextLine().trim();

            try {
                float price = Float.parseFloat(input);

                if (price >= 0) {
                    drink.setPrice(price);
                    break;
                } else {
                    System.out.println("Por favor, informe um preço válido (valor deve ser maior ou igual a 0)!\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, informe um preço válido!\n");
            }
        } while (true);

        do {
            System.out.print("Tamhno (Ex: 350 ML): ");
            String packingSize = scanner.nextLine().trim();

            if (!packingSize.isEmpty()) {
                drink.setPackingSize( packingSize );
                break;
            }

            System.out.println("Por favor, informe um tamanho válido \n");
        } while (true);

        return drink;
    }

    public long showDrink() {
        System.out.println("\n--- Menu: Bebidas -> Consultar ---");
        long drinkId;

        do {
            System.out.print("Qual é o código da bebida que você deseja consultar? (Ex: 3): ");
            String input = scanner.nextLine().trim();

            try {
                drinkId = Long.parseLong(input);

                if (drinkId >= 0) {
                    break;
                } else {
                    System.out.println("Por favor, informe um código maior ou igual a 0)!\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, informe um código válido!\n");
            }
        } while (true);

        return drinkId;
    }

    public Drink updateDrink() {
        System.out.println("\n--- Menu: Bebidas -> Editar ---");
        long drinkId;

        do {
            System.out.print("Qual é o código da bebida você quer editar? (Ex: 3): ");
            String input = scanner.nextLine().trim();
            drinkId = Long.parseLong(input);

            if (drinkId >=0)
                break;

            System.out.println("Por favor, informe o código correto! \n");
        } while (true);

        Drink drink = drinkController.getDrinkById( drinkId );

        do {
            System.out.printf("Descrição (Atual: %s): ", drink.getDescription());
            String description = scanner.nextLine().trim();

            if (!description.isEmpty()) {
                drink.setDescription( description );
                break;
            }

            System.out.println("Por favor, informe uma descrição correta! \n");
        } while (true);

        do {
            System.out.printf("\nPreço (Atual: %.2f): ", drink.getPrice());
            String input = scanner.nextLine().trim();

            try {
                float price = Float.parseFloat(input);

                if (price >= 0) {
                    drink.setPrice(price);
                    break;
                } else {
                    System.out.println("Por favor, informe um preço válido (valor deve ser maior ou igual a 0)!\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, informe um preço válido!\n");
            }
        } while (true);

        do {
            System.out.printf("\nTamhno (Atual: %s): ", drink.getPackingSize());
            String packingSize = scanner.nextLine().trim();

            if (!packingSize.isEmpty()) {
                drink.setPackingSize( packingSize );
                break;
            }

            System.out.println("Por favor, informe um tamanho válido \n");
        } while (true);

        return drink;
    }

    public long deleteDrink() {
        System.out.println("\n--- Menu: Bebidas -> Excluir ---");
        long drinkId;

        do {
            System.out.print("Qual é o código da bebida que você deseja excluir? (Ex: 3): ");
            String input = scanner.nextLine().trim();

            try {
                drinkId = Long.parseLong(input);

                if (drinkId >= 0) {
                    break;
                } else {
                    System.out.println("Por favor, informe um código maior ou igual a 0)!\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, informe um código válido!\n");
            }
        } while (true);

        return drinkId;
    }
}
