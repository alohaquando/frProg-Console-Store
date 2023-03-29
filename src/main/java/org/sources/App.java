package org.sources;

import java.util.*;

public class App {
    private static final Map<String, Product> allProducts = new HashMap<>();
    private static final Map<Integer,Cart> allCarts = new HashMap<>();

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

    public static Product getProduct(String productName) {
        return App.allProducts.get(productName);
    }

    public static Map<Integer,Cart> getAllCarts() {
        return App.allCarts;
    }

    public static Cart getCart(int cartNumber) {
        return App.allCarts.get(cartNumber);
    }

    public static void addToAllCarts(Cart cart) {
        App.allCarts.put(App.allCarts.size(), cart);
    }

    public static ArrayList<Cart> getAllCartsSorted() {
            ArrayList<Cart> carts = new ArrayList<>(App.allCarts.values());
            Collections.sort(carts);
        return carts;

    }
}
