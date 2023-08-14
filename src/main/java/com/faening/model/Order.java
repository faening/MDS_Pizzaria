package com.faening.model;

import java.util.Date;
import java.util.List;

public class Order {
    private final float DELIVERY_TAX = 10.0f;

    private Long id;
    private Customer customer;
    private List<Pizza> pizzas;
    private List<Drink> drinks;
    private Float amount;
    private DeliveryType deliveryType;
    private Date orderDate;

    public Order() { }

    public Order(Long id,
                 Customer customer,
                 List<Pizza> pizzas,
                 List<Drink> drinks,
                 Float amount,
                 DeliveryType deliveryType,
                 Date orderDate) {
        this.id = id;
        this.customer = customer;
        this.pizzas = pizzas;
        this.drinks = drinks;
        this.amount = amount;
        this.deliveryType = deliveryType;
        this.orderDate = orderDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) { this.customer = customer; }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) { this.pizzas = pizzas; }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<Drink> drinks) { this.drinks = drinks; }

    public Float getAmount() {
        return amount;
    }

    public void setAmount() {
        float totalAmount = 0.0f;

        for (Pizza pizza : pizzas) {
            totalAmount += pizza.getPrice();
        }

        for (Drink drink : drinks) {
            totalAmount += drink.getPrice();
        }

        if (deliveryType == DeliveryType.DELIVERY) {
            totalAmount += DELIVERY_TAX;
        }

        this.amount = totalAmount;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String showOrder() {
        return "[Pedido " + id + "] " +
                "Cliente: " + customer.getName() + ". " +
                "TOTAL R$: " + amount + ". ";
    }

    public String showOrderDetails() {
        StringBuilder orderDetail = new StringBuilder();

        orderDetail.append("\nPEDIDO Nº: ").append(id).append("\n");
        orderDetail.append("CLIENTE: ").append(customer.getId()).append(" - ").append(customer.getName()).append("\n");
        orderDetail.append("QTDE / PRODUTO:").append("\n");

        if (!getPizzas().isEmpty()) {
            getPizzas().forEach(pizza -> orderDetail.append("1 x ").append( pizza.toString() ).append("\n") );
        }

        if (!getDrinks().isEmpty()) {
            getDrinks().forEach(drink -> orderDetail.append("1 x ").append( drink.toString() ).append("\n") );
        }

        orderDetail.append("TIPO ENTREGA: ").append(getDeliveryType()).append("\n");
        orderDetail.append("VALOR TOTAL: R$ ").append(amount);

        return orderDetail.toString();
    }
}
