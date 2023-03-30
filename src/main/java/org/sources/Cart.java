package org.sources;

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
    final static double BASE_FEE = 0.1;
    /**
     * An integer representing the ID of the cart.
     */
    private final int id;
    /**
     * A HashSet containing the names of items in the cart.
     */
    private final HashSet<String> itemsInCart;

    /**
     * Creates a new Cart instance and adds it to the list of all carts in the App.
     * Initializes an empty set of items in the cart and assigns a unique ID to the cart.
     */
    public Cart() {
        App.addToAllCarts(this);
        this.itemsInCart = new HashSet<>();
        this.id = (App.getAllCarts().size());
    }

    /**
     * If the product exists, isn't already in the cart, and can be added to the
     * cart, then add it to
     * the cart and return true. Otherwise, return false
     *
     * @param productName - The name of the product to be added to the
     *                 cart.
     * @return A boolean value
     */
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

    /**
     * If the product is in the cart, remove it from the cart and add one to the
     * quantity of the
     * product
     *
     * @param productName - The name of the product to be removed from the
     *                 cart.
     * @return A boolean value.
     */
    public boolean removeItem(String productName) {
        if (itemsInCart.contains(productName)) {
            itemsInCart.remove(productName);
            Product product = App.getAllProducts().get(productName);
            product.quantityAddOne();
            cartAmount();
            return true;
        } else return false;
    }

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

    // REF:
    // https://stackoverflow.com/questions/14845937/java-how-to-set-precision-for-double-value

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

    /**
     * Calculates the total amount of the cart, including shipping fee
     *
     * @return The total amount of the cart.
     */
    private double cartAmount() {
        return BigDecimal.valueOf(calculateAmountNoShippingFee() + calculateShippingFee()).setScale(3, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    // Comparing the weight of the current cart with the weight of the cart that is
    // being passed in.
    public int compareTo(Cart cart) {
        return Double.compare(this.calculateWeight(), cart.calculateWeight());
    }

    /**
     * This function returns the id of the object
     *
     * @return The id of the object.
     */
    public int getId() {
        return id;
    }

    /**
     * This function returns the id of the object
     *
     * @return The id of the object.
     */
    public double getTotalAmount() {
        return cartAmount();
    }

    /**
     * This function returns the weight of the object.
     *
     * @return The weight of the package.
     */
    public double getWeight() {
        return calculateWeight();
    }

    /**
     * This function returns a HashSet of Strings that contains all the items in the
     * cart.
     *
     * @return A HashSet of Strings
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
     * This function calculates the amount without shipping fee.
     *
     * @return The amount of the order without the shipping fee.
     */
    public double getAmountWithoutShippingFee() {
        return calculateAmountNoShippingFee();
    }

    /**
     * This function calculates the shipping fee and returns it.
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
    private String getItemsInCartPrettified() {
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
     * This function returns a string that contains the amount breakdown of the order
     *
     * @return The amount breakdown of the order.
     */
    public String getAmountBreakdown() {
        return "\n   | Amount (NO shipping fee): " + getAmountWithoutShippingFee() + "\n   | Shipping fee: " + getTotalShippingFee() + "\n   | Total amount (w/  shipping fee): " + getTotalAmount();
    }

    /**
     * The function returns a string that contains the cart number, the items in the cart, the weight
     * of the cart, the amount of the cart without the shipping fee, the shipping fee, and the total
     * amount of the cart
     *
     * @return A string representation of the cart object.
     */
    @Override
    public String toString() {
        return "\uD83D\uDED2 Cart" + "\n   | Cart number: " + getId() + "\n   | Items in cart: " + getItemsInCartPrettified() + "\n   | Weight: " + getWeight() + "\n   | Amount (NO shipping fee): " + getAmountWithoutShippingFee() + "\n   | Shipping fee: " + getTotalShippingFee() + "\n   | Total amount (w/ shipping fee): " + getTotalAmount();
    }
}
