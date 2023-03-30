package org.sources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Class containing all products and all carts.
 *
 * @author Quan Hoang DO - S3800978
 */
public class System {
    /**
     * A HashMap of all products, with each product's name as the key
     */
    private static final Map<String, Product> allProducts = new HashMap<>();
    /**
     * A HashMap of all carts, with each cart's ID as the key
     */
    private static final Map<Integer, Cart> allCarts = new HashMap<>();

    /**
     * Program entry. Starts the main menu loop.
     */
    public static void start() {
        Menu.mainMenu();
    }

    /**
     * Add a product to the allProducts HashMap.
     *
     * @param product - The product to be added to the list of all products.
     */
    static void addToAllProducts(Product product) {
        System.allProducts.put(product.getName(), product);
    }

    /**
     * Returns a map of all products.
     *
     * @return App.allProducts - A map of all products.
     */
    static Map<String, Product> getAllProducts() {
        return System.allProducts;
    }

    /**
     * Given a product name, return the product object.
     *
     * @param productName - The name of the product to get.
     * @return Product - A product with the corresponding name.
     */
    static Product getProduct(String productName) {
        return System.allProducts.get(productName);
    }

    /**
     * Returns all carts in the system.
     *
     * @return A map of all the carts in the system.
     */
    static Map<Integer, Cart> getAllCarts() {
        return System.allCarts;
    }

    /**
     * Get the cart with the given cart number.
     *
     * @param cartNumber - The number of the cart to get.
     * @return The cart object that is associated with the cart number.
     */
    static Cart getCart(int cartNumber) {
        return System.allCarts.get(cartNumber);
    }

    /**
     * Adds a cart to the collection of all carts
     *
     * @param cart - The cart to add to the list of all carts.
     */
    static void addToAllCarts(Cart cart) {
        System.allCarts.put(System.allCarts.size(), cart);
    }

    /**
     * Return all carts, sorted by weight, from lightest to heaviest.
     *
     * @return An array list of all carts, sorted by weight.
     */
    static ArrayList<Cart> getAllCartsSorted() {
        ArrayList<Cart> carts = new ArrayList<>(System.allCarts.values());
        Collections.sort(carts);
        return carts;
    }
}
