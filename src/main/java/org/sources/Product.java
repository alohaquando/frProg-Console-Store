package org.sources;

/**
 * Abstract class representing a product.
 *
 * @author Quan Hoang DO - S3800978
 */
abstract public class Product {
    /**
     * The name of the product.
     */
    private String name;

    /**
     * A brief description of the product.
     */
    private String description;

    /**
     * The number of units of the product that are currently available for purchase.
     */
    private int availableQuantity;

    /**
     * The price of the product.
     */
    private double price;

    /**
     * Constructs a new Product object with the given name, description, available quantity and price.
     * Then add this product to the all products collection in System.
     *
     * @param name              - The name of the product.
     * @param description       - The description of the product.
     * @param availableQuantity - The available quantity of the product.
     * @param price             - The price of the product.
     */
    Product(String name, String description, int availableQuantity, double price) {
        setName(name);
        setDescription(description);
        setAvailableQuantity(availableQuantity);
        setPrice(price);
        System.addToAllProducts(this);
    }

    /**
     * Validates the given product name and throws an exception if it is already used by another product or blank.
     *
     * @param name the product name to validate
     * @return the validated product name
     * @throws IllegalArgumentException if the product name is already used by another product or is blank
     */
    public static String validateName(String name) throws IllegalArgumentException {
        if (System.getAllProducts().containsKey(name))
            throw new IllegalArgumentException("Product name (entered: \"" + name + "\") has already been used");
        if (name.isBlank())
            throw new IllegalArgumentException("Product name (entered: \"" + name + "\") must not be blank");
        return name;
    }

    /**
     * Validates the available quantity of a product.
     *
     * @param availableQuantity The available quantity to validate.
     * @return The validated available quantity.
     * @throws IllegalArgumentException If the available quantity is less than 0.
     */
    public static int validateAvailableQuantity(int availableQuantity) throws IllegalArgumentException {
        if (availableQuantity < 0)
            throw new IllegalArgumentException("Product available quantity (entered: \"" + availableQuantity + "\") must be 0 or higher");
        return availableQuantity;
    }

    /**
     * Validates the price of a product.
     *
     * @param price - The price of the product to be validated.
     * @return {number} The validated price.
     * @throws IllegalArgumentException If the price is less than or equal to 0.
     */
    public static double validatePrice(double price) throws IllegalArgumentException {
        if (price <= 0)
            throw new IllegalArgumentException("Product price (entered: \"" + price + "\") must be higher than 0");
        return price;
    }

    /**
     * Adds the product to the shopping cart if it is available.
     *
     * @return True if the product is added to the cart, otherwise false.
     * @throws IllegalStateException if the product is out of stock.
     */
    public boolean addToCart() {
        if (availableQuantity > 0) {
            availableQuantity--;
            return true;
        } else {
            try {
                throw new IllegalStateException("Product " + getTypedName() + " is out of stock");
            } catch (IllegalStateException e) {
                Console.printError(e.getMessage());
                return false;
            }
        }
    }

    /**
     * Adds one to the available quantity.
     */
    public void quantityAddOne() {
        availableQuantity++;
    }

    /**
     * Returns the name of the product.
     *
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * If the name is valid, set it, otherwise throw an exception.
     *
     * @param name - The name of the parameter.
     */
    public void setName(String name) {
        this.name = validateName(name);
    }

    /**
     * Returns the string representation with product type.
     *
     * @return String representation with product type.
     */
    public abstract String getTypedName();

    /**
     * Returns the description of the product
     *
     * @return The description of the product.
     */
    public String getDescription() {
        return description;
    }

    /**
     * If the description is blank, set it to "No description". Otherwise, set it to the given description
     *
     * @param description - The description of the product.
     */
    public void setDescription(String description) {
        this.description = description.isBlank() ? "No description" : description;
    }

    /**
     * This function returns the available quantity of the product
     *
     * @return The available quantity of the item.
     */
    public int getAvailableQuantity() {
        return availableQuantity;
    }

    /**
     * This function sets the available quantity of the product to the given value, after validating
     * that the given value is a valid available quantity.
     *
     * @param availableQuantity - The number of items available.
     */
    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = validateAvailableQuantity(availableQuantity);
    }

    /**
     * This function returns the price of the product.
     *
     * @return The price of the product.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set the price of the product after validating it is higher than 0.
     *
     * @param price - The price of the item.
     */
    public void setPrice(double price) {
        this.price = validatePrice(price);
    }

    /**
     * The function returns a string that contains the product's name, description, available quantity,
     * price, and type
     *
     * @return String representation of the product.
     */
    @Override
    public String toString() {
        return "\uD83D\uDCE6 Product" + "\n   | Name: " + getTypedName() + "\n   | Description: " + getDescription() + "\n   | Available quantity: " + getAvailableQuantity() + "\n   | Price: " + getPrice() + "\n   | Type: " + getClass().getSimpleName();
    }
}

