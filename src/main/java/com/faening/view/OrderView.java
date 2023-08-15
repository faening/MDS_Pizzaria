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
        boolean menuOpen = true;
        long menuOption;

        do {
            System.out.println("\n" + Messages.APP_MENU + " > " + Messages.APP_MENU_ORDER);
            showAllOrders();

            System.out.print(
                "[1] " + Messages.APP_MENU_ORDER_SELLER + "   " +
                "[2] " + Messages.APP_MENU_READ + "   " +
                "[0] " + Messages.APP_MENU_RETURN + "\n" +
                "Digite a opção desejada: ");
            menuOption = scanner.nextInt();

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

    private void showAllOrders() {
        List<Order> orders = orderController.getAllOrders();

        for (Order order : orders) {
            System.out.println(order.showOrder());
        }
        System.out.println();
    }

    public void addOrder() {
        System.out.println(
            "\n" + Messages.APP_MENU + " > " + Messages.APP_MENU_ORDER + " > " + Messages.APP_MENU_ORDER_SELLER);
        Order order = new Order();

        // #1 Customer
        System.out.println(Messages.STEP_CUSTOMER);
        showAllCustomers();
        Customer customer = addCustomerInOrder();
        order.setCustomer( customer );
        System.out.printf("Cliente selecionado: %s \n\n", customer.toString());

        // #2 Pizzas
        System.out.println(Messages.STEP_PIZZA);
        showAllPizzas();
        List<Pizza> pizzas = addItemsInOrder(pizzaController::getPizzaById, Messages.ENTER_PIZZA_CODE);
        order.setPizzas( pizzas );

        // #3 Drinks
        System.out.println(Messages.STEP_DRINK);
        showAllDrinks();
        List<Drink> drinks = addItemsInOrder(drinkController::getDrinkById, Messages.ENTER_DRINK_CODE);
        order.setDrinks(drinks);

        // #4 Delivery Type
        System.out.println(Messages.STEP_DELIVERY_TYPE);
        DeliveryType deliveryType = getDeliveryType();
        order.setDeliveryType(deliveryType);

        order.setOrderDate(new Date());
        order.setAmount();
        long orderId = orderController.addOrder(order);
        order.setId(orderId);
        System.out.println( order.showOrderDetails());
    }

    private void showAllCustomers() {
        List<Customer> customers = customerController.getAllCustomers();

        for (Customer customer : customers) {
            System.out.println(customer.toString());
        }
        System.out.println();
    }

    private void showAllPizzas() {
        List<Pizza> pizzas = pizzaController.getAllPizzas();

        for (Pizza pizza : pizzas) {
            System.out.println(pizza.toString());
        }
        System.out.println();
    }

    private void showAllDrinks() {
        List<Drink> drinks = drinkController.getAllDrinks();

        for (Drink drink : drinks) {
            System.out.println(drink.toString());
        }
        System.out.println();
    }

    public Customer addCustomerInOrder() {
        do {
            long customerId = InputUtils.getValidLongInput(Messages.ENTER_CUSTOMER_CODE);
            boolean customerExists = customerController.customerExists(customerId);

            if (customerExists) {
                return customerController.getCustomerById(customerId);
            } else {
                System.out.println(Messages.INVALID_INPUT);
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
        long addMoreOption = InputUtils.getValidLongInput("\n" + Messages.ADD_MORE_ITEMS);
        System.out.println();
        return addMoreOption != 1;
    }

    private DeliveryType getDeliveryType() {
        long deliveryTypeOption;

        do {
            deliveryTypeOption = InputUtils.getValidLongInput(Messages.ENTER_DELIVERY_OPTION);

            if (deliveryTypeOption == DeliveryType.IN_PERSON.getValue() ||
                deliveryTypeOption == DeliveryType.DELIVERY.getValue()) {
                return DeliveryType.valueOf((int) deliveryTypeOption);
            } else {
                System.out.println(Messages.INVALID_OPTION);
            }
        } while (true);
    }

    public void showOrder() {
        System.out.println(
            "\n" + Messages.APP_MENU + " > " + Messages.APP_MENU_ORDER + " > " + Messages.APP_MENU_READ);

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
