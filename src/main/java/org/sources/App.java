package org.sources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class App {
    private static final Map<String, Product> allProducts = new HashMap<>();
    private static final ArrayList<Cart> allCarts = new ArrayList<>();

    public static void start() {
        Menu.mainMenu();
    }

    protected static void addToAllProducts(Product product) {
        App.allProducts.put(product.getName(), product);
    }

    public static Map<String, Product> getAllProducts() {
        return App.allProducts;
    }

    public static String getAllProductNames() {
        return App.allProducts.keySet().toString();
    }

    public static ArrayList<Cart> getAllCarts() {
        return App.allCarts;
    }

    public static void addToAllCarts(Cart cart) {
        App.allCarts.add(cart);
    }

    public static ArrayList<Cart> getAllCarts(boolean sorted) {
        if (sorted) Collections.sort(App.allCarts);
        return App.allCarts;
    }
}
