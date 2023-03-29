package org.sources;

import java.util.*;

public class Cart implements Comparable<Cart> {
    private final String name;
    private final HashSet<String> itemsInCart;
    private double totalAmount;
    private double amountWithoutShippingFee;
    private double totalShippingFee;
    private double weight;
    final static double BASE_FEE = 0.1;

    public Cart() {
        App.addToAllCarts(this);
        this.itemsInCart = new HashSet<>();
        this.totalAmount = 0;
        this.amountWithoutShippingFee = 0;
        this.totalShippingFee = 0;
        this.name = "Cart #" + App.getAllCarts().size();
    }

    public boolean addItem(String productName) {
        Product product = App.getAllProducts().get(productName);
        if (product == null) return false; // If product doesn't exist
        if (itemsInCart.contains(product.getName())) return false; // If product already in cart
        if (product.addToCart()) {
            itemsInCart.add(product.getName());
            amountWithoutShippingFee += product.getPrice();
            totalAmount += product.getPrice();
            if (product instanceof PhysicalProduct) {
                weight += ((PhysicalProduct) product).getWeight();
                totalShippingFee += ((PhysicalProduct) product).getWeight() * BASE_FEE;
                totalAmount += ((PhysicalProduct) product).getWeight() * BASE_FEE;
            }
            ;

            return true;
        }
        return false;
    }

    public boolean removeItem(String productName) {
        if (itemsInCart.contains(productName)) {
            itemsInCart.remove(productName);

            Product product = App.getAllProducts().get(productName);

            product.quantityAddOne();
            amountWithoutShippingFee -= product.getPrice();

            if (product instanceof PhysicalProduct) {
                totalAmount -= ((PhysicalProduct) product).getWeight() * BASE_FEE;
                weight -= ((PhysicalProduct) product).getWeight();
            }
            ;

            return true;
        } else return false;
    }

//    private double doubleCheckAmount() {
//        var wrapper = new Object() {
//            double tmpAmount = 0;
//            double tmpWeight = 0;
//        };
//        double totalWeight = 0;
//        itemsInCart.forEach(productName -> {
//            Product product = App.getAllProducts().get(productName);
//            wrapper.tmpAmount += product.getPrice();
//            if (product instanceof PhysicalProduct) wrapper.tmpWeight += ((PhysicalProduct) product).getWeight();
//        });
//        wrapper.tmpAmount += wrapper.tmpWeight * BASE_FEE;
//        return wrapper.tmpAmount;
//    }


    @Override
    public int compareTo(Cart cart) {
        return Double.compare(this.weight, cart.weight);
    }

    public String getName() {
        return name;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getWeight() {
        return weight;
    }

    public HashSet<String> getItemsInCart() {
        return itemsInCart;
    }

    public double getAmountWithoutShippingFee() {
        return amountWithoutShippingFee;
    }

    public double getTotalShippingFee() {
        return totalShippingFee;
    }

    private String getItemsInCartPrettified() {
        return getItemsInCart().isEmpty() ? "No item added" : getItemsInCart().toString();
    }

    public String getAmountBreakdown() {
        return
                "\n   | Amount (NO shipping fee): " + getAmountWithoutShippingFee() +
                "\n   | Shipping fee: " + getTotalShippingFee() +
                "\n   | Total amount (including shipping fee): " + getTotalAmount()
                ;
    }

    @Override
    public String toString() {
        return "\uD83D\uDED2 Cart" +
                "\n   | Name: " + getName() +
                "\n   | Items in cart: " + getItemsInCartPrettified() +
                "\n   | Weight: " + getWeight() +
                "\n   | Amount (NO shipping fee): " + getAmountWithoutShippingFee() +
                "\n   | Shipping fee: " + getTotalShippingFee() +
                "\n   | Total amount (including shipping fee): " + getTotalAmount()
                ;
    }
}
