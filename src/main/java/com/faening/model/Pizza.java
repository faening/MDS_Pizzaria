package com.faening.model;

public class Pizza extends Product {
    private String ingredients;

    public Pizza() { }

    public Pizza(Long id, String description, Float price, String ingredients) {
        super(id, description, price);
        this.ingredients = ingredients;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "[Cód: " + getId() + "] " +
               "Preço: R$ " + getPrice() + ". " +
               "Descrição: " + getDescription() + " " +
               "(Ingredientes: " + ingredients + ")";
    }
}
