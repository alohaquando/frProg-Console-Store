package S3800978.sources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Class containing all products and all carts.
 *
 * @author Quan Hoang DO - S3800978
 */
public abstract class App {
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

    //region Product
    /**
     * Add a product to the allProducts HashMap.
     *
     * @param product - The product to be added to the list of all products.
     */
    static void addToAllProducts(Product product) {
        App.allProducts.put(product.getName(), product);
    }

    /**
     * Returns a map of all products.
     *
     * @return App.allProducts - A map of all products.
     */
    static Map<String, Product> getAllProducts() {
        return App.allProducts;
    }

    /**
     * Given a product name, return the product object.
     *
     * @param productName - The name of the product to get.
     * @return Product - A product with the corresponding name.
     */
    static Product getProduct(String productName) {
        return App.allProducts.get(productName);
    }

    /**
     * Prompts the user to enter a product name using a custom prompt, validates the input, and returns the corresponding
     * product object if it exists in the application's product list.
     *
     * @param prompt The prompt to display to the user when requesting the product name.
     * @return {Product|null} The product object if it exists, or null if it does not.
     */
    static Product queryProduct(String prompt) {
        if (getAllProducts().isEmpty()) {
            Console.printError("No product has been added. Create one first.");
            return null;
        }

        Product product = getProduct(Input.getString(prompt, "no prefix, not empty", Input::validateStringNotBlank));

        if (product != null) {
            Console.printSuccess("\nProduct found.");
            return product;
        } else {
            Console.printError("\nProduct not found.");
            return null;
        }
    }
    //endregion

    //region Cart
    /**
     * Adds a cart to the collection of all carts
     *
     * @param cart - The cart to add to the list of all carts.
     */
    static void addToAllCarts(Cart cart) {
        App.allCarts.put(App.allCarts.size(), cart);
    }

    /**
     * Returns all carts in the system.
     *
     * @return A map of all the carts in the system.
     */
    static Map<Integer, Cart> getAllCarts() {
        return App.allCarts;
    }

    /**
     * Return all carts, sorted by weight, from lightest to heaviest.
     *
     * @return An array list of all carts, sorted by weight.
     */
    static ArrayList<Cart> queryAllCartsSorted() {
        ArrayList<Cart> carts = new ArrayList<>(App.allCarts.values());
        Collections.sort(carts);
        return carts;
    }

    /**
     * Get the cart with the given cart number.
     *
     * @param cartNumber - The number of the cart to get.
     * @return The cart object that is associated with the cart number.
     */
    static Cart getCart(int cartNumber) {
        return App.allCarts.get(cartNumber);
    }

    /**
     * Retrieves a cart from the list of all carts based on the user's input.
     *
     * @param prompt - The prompt message to display to the user when requesting input.
     * @return {Cart|null} - The retrieved cart object, or null if not found.
     */
    static Cart queryCart(String prompt) {
        if (getAllProducts().isEmpty()) {
            Console.printError("No product to add. Create one first.");
            return null;
        }

        Cart cart = getCart(Input.getInt(prompt, "not empty", Input::validateCartExist) - 1);

        if (cart != null) {
            Console.printSuccess("\nCart found.");
            return cart;
        } else {
            Console.printError("\nCart not found.");
            return null;
        }
    }
    //endregion
}
