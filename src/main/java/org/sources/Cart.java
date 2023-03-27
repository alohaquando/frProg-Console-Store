package org.sources;

import java.util.*;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public class Cart implements Comparable<Cart> {
    private String name;
    private Map<String, Product> itemsInCart;
    private double amount;
    private double weight;
    static ArrayList<Cart> allCarts = new ArrayList<>();
    final static double BASE_FEE = 0.1;

    public Cart() {
        allCarts.add(this);
        this.itemsInCart = new HashMap<>();
        this.amount = 0;
        this.name = "Cart #" + allCarts.size();
    }

    public boolean addItem(String productName) {
        Product product = Product.getAllProducts().get(productName);
        if (product == null) return false; // If product doesn't exist
        if (itemsInCart.containsKey(product.getName())) return false; // If product already in cart
        if (product.addToCart()) {
            itemsInCart.put(product.getName(), product);
            amount += product.getPrice();
            if (product instanceof PhysicalProduct) {
                amount += ((PhysicalProduct) product).getWeight() * BASE_FEE;
                weight += ((PhysicalProduct) product).getWeight();
            };
            return true;
        } return false;
    }

    public boolean removeItem(String productName) {
        if (itemsInCart.containsKey(productName)) {
            itemsInCart.remove(productName);
            Product.getAllProducts().get(productName).removeFromCart();
            return true;
        } else return false;
    }

    public double cartAmount() {
        return amount;
    }

    private double doubleCheckAmount() {
        var wrapper = new Object() {
            double tmpAmount = 0;
            double tmpWeight = 0;
        };
        double totalWeight = 0;
        itemsInCart.values().forEach(product -> {
            wrapper.tmpAmount += product.getPrice();
            if (product instanceof PhysicalProduct) wrapper.tmpWeight += ((PhysicalProduct) product).getWeight();
        });
        wrapper.tmpAmount += wrapper.tmpWeight * BASE_FEE;
        return wrapper.tmpAmount;
    }

    public static void displayAllCartsSorted() {
        Collections.sort(allCarts);
        System.out.println(allCarts);
    }

    @Override
    public int compareTo(Cart cart) {
        return Double.compare(this.weight, cart.weight);
    }

    @Override
    public String toString() {
        return "\nCart{" +
                "name='" + name + '\'' +
                ", itemsInCart=" + itemsInCart +
                ", amount=" + amount +
                ", weight=" + weight +
                '}';
    }
}
