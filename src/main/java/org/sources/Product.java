package org.sources;

import java.util.*;

/**
 * @author Quan Hoang DO - S3800978
 */
abstract public class Product {
    private String name;
    private String description;
    private int availableQuantity;
    private double price;
    private static Map<String, Product> allProducts = new HashMap<>();

    Product(String name, String description, int availableQuantity, double price) {
        this.name = validateName(name);
        this.description = description.isBlank() ? "No description" : description;
        this.availableQuantity = validateAvailableQuantity(availableQuantity);
        this.price = validatePrice(price);
        allProducts.put(this.name, this);
    }

    public static Map<String, Product> getAllProducts() {
        return allProducts;
    }

    public boolean addToCart() {
        if (availableQuantity > 0) {
            availableQuantity--;
            return true;
        } else return false;
    }

    public static String getAllProductNames() {
        return allProducts.keySet().toString();
    }

    public void removeFromCart() {
        availableQuantity++;
    }

    /**
     * Validators
     */
    public static String validateName(String name) throws IllegalArgumentException {
        if (allProducts.containsKey(name))
            throw new IllegalArgumentException("Product name (entered: \"" + name + "\") has already been used");
        if (name.isBlank())
            throw new IllegalArgumentException("Product name (entered: \"" + name + "\") must not be blank");
        return name;
    }

    public static int validateAvailableQuantity(int availableQuantity) throws IllegalArgumentException {
        if (availableQuantity < 0)
            throw new IllegalArgumentException("Product available quantity (entered: \"" + availableQuantity + "\") must be 0 or higher");
        return availableQuantity;
    }

    public static double validatePrice(double price) throws IllegalArgumentException {
        if (price < 0)
            throw new IllegalArgumentException("Product price (entered: \"" + price + "\") must be 0 or higher");
        return price;
    }

    public String getName() {
        return name;
    }

    public abstract String getTypedName();

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "\uD83D\uDCE6 Product" +
                "\n   | Name: " + getTypedName() +
                "\n   | Description: " + getDescription() +
                "\n   | Available quantity: " + getAvailableQuantity() +
                "\n   | Price: " + getPrice() +
                "\n   | Type: " + getClass().getSimpleName()
                ;
    }
}
