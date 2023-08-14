package com.faening.view;

import com.faening.controller.CustomerController;
import com.faening.controller.DrinkController;
import com.faening.controller.OrderController;
import com.faening.controller.PizzaController;
import com.faening.model.*;
import com.faening.utils.InputUtils;
import com.faening.utils.Messages;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public final class OrderView {
    private final OrderController orderController;
    private final CustomerController customerController;
    private final PizzaController pizzaController;
    private final DrinkController drinkController;
    private final static Scanner scanner = new Scanner(System.in);

    public OrderView(Connection connection) {
        this.orderController = new OrderController(connection);
        this.customerController = new CustomerController(connection);
        this.pizzaController = new PizzaController(connection);
        this.drinkController = new DrinkController(connection);
    }

    public void showMenu() {
        System.out.println("\n--- Menu: Pedidos ---\n");
        boolean menuOpen = true;
        long menuOption;

        do {
            showAllOrders();

            menuOption = InputUtils.getValidLongInput(
                "[1] Vender \n" +
                "[2] Consultar Pedido \n" +
                "[0] Voltar \n" +
                "Digite a opção desejada: ");

            switch ((int) menuOption) {
                case 0 -> {
                    menuOpen = false;
                    System.out.println();
                }
                case 1 -> addOrder();
                case 2 -> showOrder();
                default -> System.out.println(Messages.INVALID_OPTION);
            }
        } while (menuOpen);
    }

    public void addOrder() {
        System.out.println("\n--- Menu: Pedidos -> Venda ---");
        Order order = new Order();

        // Cliente
        System.out.println(Messages.STEP_CUSTOMER);
        showAllCustomers();
        Customer customer = addCustomerInOrder();
        order.setCustomer( customer );
        System.out.printf("Você selecionou o cliente: %s \n\n", customer.toString());

        // Pizzas
        System.out.println(Messages.STEP_PIZZA);
        showAllPizzas();
        List<Pizza> pizzas = addItemsInOrder(pizzaController::getPizzaById, Messages.ENTER_PIZZA_CODE);
        order.setPizzas( pizzas );

        // Bebidas
        System.out.println(Messages.STEP_DRINK);
        showAllDrinks();
        List<Drink> drinks = addItemsInOrder(drinkController::getDrinkById, Messages.ENTER_DRINK_CODE);
        order.setDrinks(drinks);

        // Tipo de entrega
        System.out.println(Messages.STEP_DELIVERY_TYPE);
        DeliveryType deliveryType = getDeliveryType();
        order.setDeliveryType(deliveryType);

        order.setOrderDate(new Date());
        order.setAmount();
        long orderId =orderController.addOrder(order);
        order.setId(orderId);
        System.out.println( order.showOrderDetails() + "\n");
    }

    public Customer addCustomerInOrder() {
        do {
            long customerId = InputUtils.getValidLongInput("\nDigite o código do cliente: ");
            boolean customerExists = customerController.customerExists(customerId);

            if (customerExists) {
                return customerController.getCustomerById(customerId);
            } else {
                System.out.println("Informe um código válido! \n");
            }
        } while (true);
    }

    private <T> List<T> addItemsInOrder(Function<Long, T> getItemById, String inputMessage) {
        List<T> items = new ArrayList<>();
        do {
            long itemId = InputUtils.getValidLongInput(inputMessage);
            if (getItemById.apply(itemId) != null) {
                items.add(getItemById.apply(itemId));
            } else {
                System.out.println(Messages.INVALID_OPTION);
                continue;
            }
            if (!addMoreItems()) {
                break;
            }
        } while (true);
        return items;
    }

    private boolean addMoreItems() {
        long addMoreOption = InputUtils.getValidLongInput(Messages.ADD_MORE_ITEMS);
        return addMoreOption != 1;
    }

    private DeliveryType getDeliveryType() {
        long deliveryTypeOption;
        do {
            deliveryTypeOption = InputUtils.getValidLongInput(Messages.ENTER_DELIVERY_OPTION);
            if (deliveryTypeOption == DeliveryType.IN_PERSON.getValue() || deliveryTypeOption == DeliveryType.DELIVERY.getValue()) {
                return DeliveryType.valueOf((int) deliveryTypeOption);
            } else {
                System.out.println(Messages.INVALID_OPTION);
            }
        } while (true);
    }

    private void showAllOrders() {
        System.out.println("Últimos pedidos registrados: ");
        List<Order> orders = orderController.getAllOrders();

        for (Order order : orders) {
            System.out.println(order.showOrder());
        }
        System.out.println();
    }

    private void showAllCustomers() {
        List<Customer> customers = customerController.getAllCustomers();

        for (Customer customer : customers) {
            System.out.println(customer.toString());
        }
    }

    private void showAllPizzas() {
        List<Pizza> pizzas = pizzaController.getAllPizzas();

        for (Pizza pizza : pizzas) {
            System.out.println(pizza.toString());
        }
    }

    private void showAllDrinks() {
        List<Drink> drinks = drinkController.getAllDrinks();

        for (Drink drink : drinks) {
            System.out.println(drink.toString());
        }
    }

    public void showOrder() {
        System.out.println("\n--- Menu: Pedidos -> Consultar ---");

        do {
            long orderId = InputUtils.getValidLongInput("Digite o código do pedido: ");
            boolean orderExists = orderController.orderExists(orderId);

            if (orderExists) {
                System.out.println( orderController.getOrderById(orderId).showOrderDetails() );
                break;
            } else {
                System.out.println(Messages.INVALID_OPTION);
            }
        } while (true);
    }
}
