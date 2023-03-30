package org.sources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * It's a class that holds all the products and carts in the system
 *
 * @author Quan Hoang DO - S3800978
 */
public class App {
    // It's a static variable that holds all the products and carts in the system.
    private static final Map<String, Product> allProducts = new HashMap<>();
    private static final Map<Integer, Cart> allCarts = new HashMap<>();
    
    /**
     * The function `start()` is a static function that is called from the `main()` function in the
     * `Main` class. The `start()` function calls the `mainMenu()` function in the `Menu` class
     */
    public static void start() {
        Menu.mainMenu();
    }

    /**
     * Add a product to the allProducts map.
     * @param product - The product to be added to the list of all products.
     */
    static void addToAllProducts(Product product) {
        App.allProducts.put(product.getName(), product);
    }

    /**
     * This function returns a map of all products.
     * @return App.allProducts - A map of all products.
     */
    static Map<String, Product> getAllProducts() {
        return App.allProducts;
    }

    /**
     * Given a product name, return the product object.
     * @param productName - The name of the product you want to get.
     * @return A product object
     */
    static Product getProduct(String productName) {
        return App.allProducts.get(productName);
    }

    /**
     * This function returns a map of all the carts in the system.
     * @return A map of all the carts in the system.
     */
    static Map<Integer, Cart> getAllCarts() {
        return App.allCarts;
    }

    /**
     * Get the cart with the given cart number.
     * @param cartNumber - The number of the cart you want to get.
     * @return The cart object that is associated with the cart number.
     */
    static Cart getCart(int cartNumber) {
        return App.allCarts.get(cartNumber);
    }

    /**
     * It adds a cart to the allCarts HashMap
     * @param cart - The cart to add to the list of all carts.
     */
    static void addToAllCarts(Cart cart) {
        App.allCarts.put(App.allCarts.size(), cart);
    }

    /**
     * Get all carts, sort them, and return them.
     * @return A list of all the carts sorted by their total price.
     */
    static ArrayList<Cart> getAllCartsSorted() {
        ArrayList<Cart> carts = new ArrayList<>(App.allCarts.values());
        Collections.sort(carts);
        return carts;
    }
}
