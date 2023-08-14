package com.faening.model;

public class Drink extends Product {
    private String packingSize;

    public Drink() { }

    public Drink(Long id, String description, Float price, String packingSize) {
        super(id, description, price);
        this.packingSize = packingSize;
    }

    public String getPackingSize() {
        return packingSize;
    }

    public void setPackingSize(String packingSize) {
        this.packingSize = packingSize;
    }

    @Override
    public String toString() {
        return "[Cód: " + getId() + "] " +
               "Preço: R$ " + getPrice() + ". " +
               "Descrição: " + getDescription() + " " + packingSize;
    }
}
