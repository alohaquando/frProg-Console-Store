package S3800978.sources;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;

/**
 * This class represents a shopping cart, and it has an id, a set of items, and
 * a base fee.
 *
 * @author Quan Hoang DO - S3800978
 */
public class Cart implements Comparable<Cart> {
    /**
     * The base fee for the cart.
     */
    final private static double BASE_FEE = 0.1;
    /**
     * An integer representing the ID of the cart.
     */
    private final int id;
    /**
     * A set containing the names of items in the cart.
     */
    private final HashSet<String> itemsInCart; // REQUIREMENT

    /**
     * Creates a new Cart instance and adds it to the list of all carts in the App.
     * Initializes an empty set of items in the cart and assigns a unique ID to the cart.
     */
    public Cart() {
        App.addToAllCarts(this);
        this.itemsInCart = new HashSet<>();
        this.id = (App.getAllCarts().size());
    }

    //region Product-related functions

    // REQUIREMENT
    /**
     * If the product exists, isn't already in the cart, and can be added to the
     * cart, then add it to
     * the cart and return true. Otherwise, return false
     *
     * @param productName - The name of the product to be added to the
     *                    cart.
     * @return A boolean value
     */
    public boolean addItem(String productName) {
        Product product = App.getProduct(productName);

        // If product doesn't exist
        if (product == null) try {
            throw new IllegalArgumentException("Product not found");
        } catch (IllegalArgumentException e) {
            Console.printError(e.getMessage());
            return false;
        }

        // If product already in cart
        if (itemsInCart.contains(product.getName())) {
            try {
                throw new IllegalStateException("Product " + product.getName() + " is already in cart " + getId());
            } catch (IllegalStateException e) {
                Console.printError(e.getMessage());
                return false;
            }
        }

        if (product.addToCart()) {
            itemsInCart.add(product.getName());
            cartAmount();
            return true;
        } else return false;
    }

    // REQUIREMENT
    /**
     * If the product is in the cart, remove it from the cart and add one to the
     * quantity of the
     * product
     *
     * @param productName - The name of the product to be removed from the
     *                    cart.
     * @return A boolean value.
     */
    public boolean removeItem(String productName) {
        if (itemsInCart.contains(productName)) {
            itemsInCart.remove(productName);
            Product product = App.getProduct(productName);
            product.quantityAddOne();
            cartAmount();
            return true;
        } else return false;
    }
    //endregion

    //region Calculator functions

    /**
     * Calculate the amount of the order without shipping fee.
     *
     * @return The total amount of the items in the cart without shipping fee.
     */
    private double calculateAmountNoShippingFee() {
        double result = 0;
        for (String productName : itemsInCart) {
            Product product = App.getProduct(productName);
            result += product.getPrice();
        }
        return BigDecimal.valueOf(result).setScale(3, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Calculate the total weight of all physical products in the cart.
     *
     * @return The weight of all the physical products in the cart.
     */
    private double calculateWeight() {
        double result = 0;
        for (String productName : itemsInCart) {
            if (App.getProduct(productName) instanceof PhysicalProduct) {
                result += ((PhysicalProduct) App.getProduct(productName)).getWeight();
            }
        }
        return BigDecimal.valueOf(result).setScale(3, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Calculate the shipping fee by multiplying the weight by the base fee, then
     * round the result to 3
     * decimal places.
     *
     * @return The shipping fee is being returned.
     */
    private double calculateShippingFee() {
        return BigDecimal.valueOf(calculateWeight() * BASE_FEE).setScale(3, RoundingMode.HALF_UP).doubleValue();
    }

    // REQUIREMENT
    /**
     * Calculates the total amount of the cart, including shipping fee
     *
     * @return The total amount of the cart.
     */
    public double cartAmount() {
        return BigDecimal.valueOf(calculateAmountNoShippingFee() + calculateShippingFee()).setScale(3, RoundingMode.HALF_UP).doubleValue();
    }
    //endregion

    //region Getters

    /**
     * Returns the id of the cart
     *
     * @return The id of the cart.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the weight of the cart.
     *
     * @return The weight of the cart.
     */
    public double getWeight() {
        return calculateWeight();
    }

    /**
     * Returns a HashSet of Strings that contains all the items in the
     * cart.
     *
     * @return A HashSet of Strings of products in cart
     */
    public HashSet<String> getItemsInCart() {
        return itemsInCart;
    }

    /**
     * Returns the number of items in the cart.
     *
     * @return {number} The number of items in the cart.
     */
    public int getItemsInCartSize() {
        return itemsInCart.size();
    }

    /**
     * Returns the amount without shipping fee.
     *
     * @return The amount of the cart without the shipping fee.
     */
    public double getAmountWithoutShippingFee() {
        return calculateAmountNoShippingFee();
    }

    /**
     * Return total shipping fee
     *
     * @return The total shipping fee.
     */
    public double getTotalShippingFee() {
        return calculateShippingFee();
    }

    /**
     * If the cart is empty, return "No item added", else return the number of
     * items in the cart and
     * the name of each item in the cart
     *
     * @return A string of the items in the cart.
     */
    public String getItemsInCartPrettified() {
        if (getItemsInCart().isEmpty()) {
            return "No item added";
        } else {
            StringBuilder itemsInCart = new StringBuilder();
            itemsInCart.append(getItemsInCartSize());
            getItemsInCart().forEach(item -> itemsInCart.append("\n     └─ ").append(App.getProduct(item).getTypedName()));
            return itemsInCart.toString();
        }
    }

    /**
     * Returns a string that contains the amount breakdown of the cart
     *
     * @return The amount breakdown of the cart.
     */
    public String getAmountBreakdown() {
        return "\n   | Amount (NO shipping fee): " + getAmountWithoutShippingFee() + "\n   | Shipping fee: " + getTotalShippingFee() + "\n   | Total amount (w/ shipping fee): " + cartAmount();
    }
    //endregion

    //region Overrides

    /**
     * Override the compareTo() function so carts can be compared and sorted by weight.
     */
    @Override
    public int compareTo(Cart cart) {
        return Double.compare(this.calculateWeight(), cart.calculateWeight());
    }

    /**
     * Returns a string that contains the cart number, the items in the cart, the weight
     * of the cart, the amount of the cart without the shipping fee, the shipping fee, and the total
     * amount of the cart
     *
     * @return A string representation of the cart object.
     */
    @Override
    public String toString() {
        return "\uD83D\uDED2 Cart" + "\n   | Cart number: " + getId() + "\n   | Items in cart: " + getItemsInCartPrettified() + "\n   | Weight: " + getWeight() + "\n   | Amount (NO shipping fee): " + getAmountWithoutShippingFee() + "\n   | Shipping fee: " + getTotalShippingFee() + "\n   | Total amount (w/ shipping fee): " + cartAmount();
    }
    //endregion
}
