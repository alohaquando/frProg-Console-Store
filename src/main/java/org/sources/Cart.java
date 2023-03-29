package org.sources;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Cart implements Comparable<Cart> {
    private final int id;
    private final HashSet<String> itemsInCart;
    final static double BASE_FEE = 0.1;

    public Cart() {
        App.addToAllCarts(this);
        this.itemsInCart = new HashSet<>();
        this.id = (App.getAllCarts().size());
    }

    public boolean addItem(String productName) {
        Product product = App.getProduct(productName);
        if (product == null) return false; // If product doesn't exist
        if (itemsInCart.contains(product.getName())) return false; // If product already in cart
        if (product.addToCart()) {
            itemsInCart.add(product.getName());
            cartAmount();

            return true;
        } else return false;
    }

    public boolean removeItem(String productName) {
        if (itemsInCart.contains(productName)) {
            itemsInCart.remove(productName);

            Product product = App.getAllProducts().get(productName);

            product.quantityAddOne();
            cartAmount();

            return true;
        } else return false;
    }

    private double calculateAmountNoShippingFee() {
        double result = 0;
        for (String productName :
                itemsInCart) {
            Product product = App.getProduct(productName);
            result += product.getPrice();
        }
        return BigDecimal.valueOf(result)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private double calculateWeight() {
        double result = 0;
        for (String productName :
                itemsInCart) {
            if (App.getProduct(productName) instanceof PhysicalProduct) {
                result += ((PhysicalProduct) App.getProduct(productName)).getWeight();
            }
        }
        return BigDecimal.valueOf(result)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
    }

    // REF:
    // https://stackoverflow.com/questions/14845937/java-how-to-set-precision-for-double-value
    private double calculateShippingFee() {
        return BigDecimal.valueOf(calculateWeight() * BASE_FEE)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private double cartAmount() {
        return BigDecimal.valueOf(calculateAmountNoShippingFee() + calculateShippingFee())
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
    }

    @Override
    public int compareTo(Cart cart) {
        return Double.compare(this.calculateWeight(), cart.calculateWeight());
    }

    public int getId() {
        return id;
    }

    public double getTotalAmount() {
        return cartAmount();
    }

    public double getWeight() {
        return calculateWeight();
    }

    public HashSet<String> getItemsInCart() {
        return itemsInCart;
    }

    public int getItemsInCartSize() {
        return itemsInCart.size();
    }

    public double getAmountWithoutShippingFee() {
        return calculateAmountNoShippingFee();
    }

    public double getTotalShippingFee() {
        return calculateShippingFee();
    }

    private String getItemsInCartPrettified() {

        if (getItemsInCart().isEmpty()) {
            return "No item added";
        } else {
            StringBuilder itemsInCart = new StringBuilder();
            itemsInCart.append(getItemsInCartSize());
            getItemsInCart().forEach(item -> {
                itemsInCart.append("\n     └─ ").append(App.getProduct(item).getTypedName());
            });
            return itemsInCart.toString();
        }


    }

    public String getAmountBreakdown() {
        return
                "\n   | Amount (NO shipping fee): " + getAmountWithoutShippingFee() +
                        "\n   | Shipping fee: " + getTotalShippingFee() +
                        "\n   | Total amount (w/  shipping fee): " + getTotalAmount()
                ;
    }

    @Override
    public String toString() {
        return "\uD83D\uDED2 Cart" +
                "\n   | Cart number: " + getId() +
                "\n   | Items in cart: " + getItemsInCartPrettified() +
                "\n   | Weight: " + getWeight() +
                "\n   | Amount (NO shipping fee): " + getAmountWithoutShippingFee() +
                "\n   | Shipping fee: " + getTotalShippingFee() +
                "\n   | Total amount (w/ shipping fee): " + getTotalAmount()
                ;
    }
}
