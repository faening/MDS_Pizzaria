package com.faening.view;

import com.faening.controller.CustomerController;
import com.faening.model.Customer;

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
        List<Customer> customers;
        boolean openedMenu = true;

        do {
            System.out.println("\n--- Menu: Clientes ---");
            customers = customerController.getAllCustomers();
            customers.forEach(customer -> System.out.println(customer.toString()));

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
                case 1 -> customerController.addCustomer( addCustomer() );
                case 2 -> System.out.println( customerController.getCustomerById( showCustomer() ) );
                case 3 -> customerController.updateCustomer( updateCustomer() );
                case 4 -> customerController.deleteCustomer( deleteCustomer() );
                default -> System.out.println("Opção inválida!");
            }
        } while (openedMenu);
    }

    public Customer addCustomer() {
        System.out.println("\n--- Menu: Clientes -> Cadastrar ---");
        Customer customer = new Customer();

        do {
            System.out.print("Nome (Ex: John Doe): ");
            String name = scanner.nextLine().trim();

            if (!name.isEmpty()) {
                customer.setName(name);
                break;
            }

            System.out.println("Por favor, informe um nome correto! \n");
        } while (true);

        do {
            System.out.print("Telefone (Ex: 33 8405-9422): ");
            String phone = scanner.nextLine().trim();

            if (!phone.isEmpty()) {
                customer.setPhone(phone);
                break;
            }

            System.out.println("Por favor, informe um telefone correto! \n");
        } while (true);

        do {
            System.out.print("Endereço (Ex: R Castle, 98, Principal): ");
            String address = scanner.nextLine().trim();

            if (!address.isEmpty()) {
                customer.setAddress(address);
                break;
            }

            System.out.println("Por favor, informe um endereço correto! \n");
        } while (true);

        return customer;
    }

    public long showCustomer() {
        System.out.println("\n--- Menu: Clientes -> Consultar ---");
        long customerId;

        do {
            System.out.print("Qual é o código do cliente que você deseja consultar? (Ex: 3): ");
            String input = scanner.nextLine().trim();

            try {
                customerId = Long.parseLong(input);

                if (customerId >= 0) {
                    break;
                } else {
                    System.out.println("Por favor, informe um código maior ou igual a 0)!\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, informe um código válido!\n");
            }
        } while (true);

        return customerId;
    }

    public Customer updateCustomer() {
        System.out.println("\n--- Menu: Clientes -> Editar ---");
        long customerId;

        do {
            System.out.print("Qual é o código do cliente você quer editar? (Ex: 3): ");
            String input = scanner.nextLine().trim();
            customerId = Long.parseLong(input);

            if (customerId >=0)
                break;

            System.out.println("Por favor, informe um código correto! \n");
        } while (true);

        Customer customer = customerController.getCustomerById( customerId );

        do {
            System.out.printf("Nome (Atual: %s): ", customer.getName());
            String name = scanner.nextLine().trim();

            if (!name.isEmpty()) {
                customer.setName(name);
                break;
            }

            System.out.println("Por favor, informe um nome correto! \n");
        } while (true);

        do {
            System.out.printf("\nTelefone (Atual: %s): ", customer.getPhone());
            String phone = scanner.nextLine().trim();

            if (!phone.isEmpty()) {
                customer.setPhone(phone);
                break;
            }

            System.out.println("Por favor, informe um telefone correto! \n");
        } while (true);

        do {
            System.out.printf("\nEndereço (Atual: %s): ", customer.getAddress());
            String address = scanner.nextLine().trim();

            if (!address.isEmpty()) {
                customer.setAddress(address);
                break;
            }

            System.out.println("Por favor, informe um endereço correto! \n");
        } while (true);

        return customer;
    }

    public long deleteCustomer() {
        System.out.println("\n--- Menu: Clientes -> Excluir ---");
        long customerId;

        do {
            System.out.print("Qual é o código do cliente que você deseja excluir? (Ex: 3): ");
            String input = scanner.nextLine().trim();

            try {
                customerId = Long.parseLong(input);

                if (customerId >= 0) {
                    break;
                } else {
                    System.out.println("Por favor, informe um código maior ou igual a 0)!\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, informe um código válido!\n");
            }
        } while (true);

        return customerId;
    }
}
