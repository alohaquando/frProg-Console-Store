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
     * Then add this product to the all products collection in App.
     *
     * @param name              - The name of the product.
     * @param description       - The description of the product.
     * @param availableQuantity - The available quantity of the product.
     * @param price             - The price of the product.
     * @throws IllegalArgumentException - If the available quantity or price is less than or equal to 0.
     */
    Product(String name, String description, int availableQuantity, double price) {
        setName(name);
        setDescription(description);
        setAvailableQuantity(availableQuantity);
        setPrice(price);
        App.addToAllProducts(this);
    }

    /**
     * Validates the given product name and throws an exception if it is already used by another product or blank.
     *
     * @param name the product name to validate
     * @return the validated product name
     * @throws IllegalArgumentException if the product name is already used by another product or is blank
     */
    public static String validateName(String name) throws IllegalArgumentException {
        if (App.getAllProducts().containsKey(name))
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
     * @throws IllegalArgumentException If the price is less than or equal to 0.
     * @return {number} The validated price.
     */
    public static double validatePrice(double price) throws IllegalArgumentException {
        if (price <= 0)
            throw new IllegalArgumentException("Product price (entered: \"" + price + "\") must be higher than 0");
        return price;
    }

    /**
     * Adds the product to the shopping cart if it is available.
     * @return True if the product is added to the cart, otherwise false.
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
     * This function adds one to the available quantity.
     */
    public void quantityAddOne() {
        availableQuantity++;
    }

    /**
     * This function returns the name of the product.
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * If the name is valid, set it, otherwise throw an exception.
     * @param name - The name of the parameter.
     */
    public void setName(String name) {
        this.name = validateName(name);
    }

    /**
     * Returns the name of the type of this object.
     * @return The name of the type of the object.
     */
    public abstract String getTypedName();

    /**
     * > This function returns the description of the object
     * @return The description of the item.
     */
    public String getDescription() {
        return description;
    }

    /**
     * If the description is blank, set it to "No description". Otherwise, set it to the description
     * @param description - The description of the command.
     */
    public void setDescription(String description) {
        this.description = description.isBlank() ? "No description" : description;
    }

    /**
     * This function returns the available quantity of the product
     * @return The available quantity of the item.
     */
    public int getAvailableQuantity() {
        return availableQuantity;
    }

    /**
     * This function sets the available quantity of the product to the given value, after validating
     * that the given value is a valid available quantity.
     * @param availableQuantity - The number of items available for sale.
     */
    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = validateAvailableQuantity(availableQuantity);
    }

    /**
     * This function returns the price of the item.
     * @return The price of the item.
     */
    public double getPrice() {
        return price;
    }

    /**
     * If the price is less than zero, throw an exception, otherwise set the price.
     * @param price - The price of the item.
     */
    public void setPrice(double price) {
        this.price = validatePrice(price);
    }

    /**
     * The function returns a string that contains the product's name, description, available quantity,
     * price, and type
     * @return The toString method is being returned.
     */
    @Override
    public String toString() {
        return "\uD83D\uDCE6 Product" + "\n   | Name: " + getTypedName() + "\n   | Description: " + getDescription() + "\n   | Available quantity: " + getAvailableQuantity() + "\n   | Price: " + getPrice() + "\n   | Type: " + getClass().getSimpleName();
    }
}

