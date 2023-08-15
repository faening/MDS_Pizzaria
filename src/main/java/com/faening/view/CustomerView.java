package com.faening.view;

import com.faening.controller.CustomerController;
import com.faening.model.Customer;
import com.faening.utils.InputUtils;
import com.faening.utils.Messages;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public final class CustomerView {
    private final CustomerController customerController;
    private final static Scanner scanner = new Scanner(System.in);

    public CustomerView(Connection connection) {
        this.customerController = new CustomerController(connection);
    }

    public void showMenu() {
        boolean menuOpen = true;
        long menuOption;

        do {
            System.out.println("\n" + Messages.APP_MENU + " > " + Messages.APP_MENU_CUSTOMER);
            showAllCustomers();

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
                case 1 -> addCustomer();
                case 2 -> showCustomer();
                case 3 -> updateCustomer();
                case 4 -> deleteCustomer();
                default -> System.out.println(Messages.INVALID_INPUT);
            }
        } while (menuOpen);
    }

    public void showAllCustomers() {
        List<Customer> customers = customerController.getAllCustomers();

        for (Customer customer : customers) {
            System.out.println(customer.toString());
        }
        System.out.println();
    }

    public void addCustomer() {
        System.out.println(
            "\n" + Messages.APP_MENU + " > " + Messages.APP_MENU_CUSTOMER + " > " + Messages.APP_MENU_CREATE);
        Customer customer = new Customer();

        String name = InputUtils.getValidStringInput(Messages.ENTER_CUSTOMER_NAME);
        customer.setName(name);

        String phone = InputUtils.getValidStringInput(Messages.ENTER_CUSTOMER_PHONE);
        customer.setPhone(phone);

        String address = InputUtils.getValidStringInput(Messages.ENTER_CUSTOMER_ADDRESS);
        customer.setAddress(address);

        customerController.addCustomer(customer);
        System.out.println(Messages.SUCCESSFUL);
    }

    public void showCustomer() {
        System.out.println(
            "\n" + Messages.APP_MENU + " > " + Messages.APP_MENU_CUSTOMER + " > " + Messages.APP_MENU_READ);

        do {
          long customerId = InputUtils.getValidLongInput("Digite o código do cliente: ");
          boolean customerExists = customerController.customerExists(customerId);

          if (customerExists) {
              System.out.println(customerController.getCustomerById(customerId));
              break;
          }
        } while (true);
    }

    public void updateCustomer() {
        System.out.println(
            "\n" + Messages.APP_MENU + " > " + Messages.APP_MENU_CUSTOMER + " > " + Messages.APP_MENU_UPDATE);
        long customerId;
        Customer customer;

        do {
            customerId = InputUtils.getValidLongInput("Digite o código do cliete que você quer editar: ");
            boolean customerExists = customerController.customerExists(customerId);

            if (customerExists) {
                customer = customerController.getCustomerById(customerId);
                break;
            }

            System.out.println(Messages.INVALID_OPTION);
        } while (true);

        String name = InputUtils.getValidStringInput(Messages.ENTER_CUSTOMER_NAME);
        customer.setName(name);

        String phone = InputUtils.getValidStringInput(Messages.ENTER_CUSTOMER_PHONE);
        customer.setPhone(phone);

        String address = InputUtils.getValidStringInput(Messages.ENTER_CUSTOMER_ADDRESS);
        customer.setAddress(address);

        customerController.updateCustomer(customer);
        System.out.println(Messages.SUCCESSFUL);
    }

    public void deleteCustomer() {
        System.out.println(
            "\n" + Messages.APP_MENU + " > " + Messages.APP_MENU_CUSTOMER + " > " + Messages.APP_MENU_DELETE);
        long customerId;

        do {
            customerId = InputUtils.getValidLongInput("Digite o código do cliete que você quer excluir: ");
            boolean customerExists = customerController.customerExists(customerId);

            if (customerExists) {
                customerController.deleteCustomer(customerId);
                System.out.println(Messages.SUCCESSFUL);
                break;
            }

            System.out.println(Messages.INVALID_OPTION);
        } while (true);
    }
}
