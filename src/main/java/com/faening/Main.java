package com.faening;

import com.faening.database.H2DatabaseConnection;
import com.faening.initializer.DataInitializer;
import com.faening.utils.Messages;
import com.faening.view.CustomerView;
import com.faening.view.DrinkView;
import com.faening.view.OrderView;
import com.faening.view.PizzaView;
import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    static Scanner scanner;
    static H2DatabaseConnection dbConnection;
    static Connection connection;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        // Database connection
        dbConnection = new H2DatabaseConnection();
        dbConnection.connect();
        connection = dbConnection.getConnection();

        try {
            // H2 Console
            Server h2WebServer = Server.createWebServer("-webPort", "8082", "-tcpAllowOthers").start();
            System.out.println("H2 Console URL: " + h2WebServer.getURL() + "\n");

            // Seeds
            DataInitializer.initialize(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Menu
        int menuOption;

        PizzaView pizzaView = new PizzaView(connection);
        CustomerView customerView = new CustomerView(connection);
        DrinkView drinkView = new DrinkView(connection);
        OrderView orderView = new OrderView(connection);

        do {
            System.out.print(
                Messages.APP_MENU + "\n" +
                "[1] " + Messages.APP_MENU_PIZZA + "   " +
                "[2] " + Messages.APP_MENU_DRINK + "   " +
                "[3] " + Messages.APP_MENU_CUSTOMER + "   " +
                "[4] " + Messages.APP_MENU_ORDER + "   " +
                "[0] " + Messages.APP_MENU_CLOSE + "\n" +
                "Digite a opção desejada: ");
            menuOption = scanner.nextInt();

            switch (menuOption) {
                case 0 -> {
                    System.out.println("Saindo...");
                    scanner.close();
                    System.exit(0);
                }
                case 1 -> pizzaView.showMenu();
                case 2 -> drinkView.showMenu();
                case 3 -> customerView.showMenu();
                case 4 -> orderView.showMenu();
                default -> System.out.println(Messages.INVALID_OPTION);
            }
        } while (true);
    }
}
