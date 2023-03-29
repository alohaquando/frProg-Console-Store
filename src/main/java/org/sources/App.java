package org.sources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Quan Hoang DO - S3800978
 */
public class App {

    private static final Map<String, Product> allProducts = new HashMap<>();
    private static final Map<Integer, Cart> allCarts = new HashMap<>();

    /**
     * Main function to start program
     */
    public static void start() {
        Menu.mainMenu();
    }

    static void addToAllProducts(Product product) {
        App.allProducts.put(product.getName(), product);
    }

    static Map<String, Product> getAllProducts() {
        return App.allProducts;
    }

    static Product getProduct(String productName) {
        return App.allProducts.get(productName);
    }

    static Map<Integer, Cart> getAllCarts() {
        return App.allCarts;
    }

    static Cart getCart(int cartNumber) {
        return App.allCarts.get(cartNumber);
    }

    static void addToAllCarts(Cart cart) {
        App.allCarts.put(App.allCarts.size(), cart);
    }

    static ArrayList<Cart> getAllCartsSorted() {
        ArrayList<Cart> carts = new ArrayList<>(App.allCarts.values());
        Collections.sort(carts);
        return carts;
    }
}
