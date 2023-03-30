package org.sources;

/**
 * Represents a physical product that can be sold and shipped to customers.
 *
 * @author Quan Hoang DO - S3800978
 */
public class PhysicalProduct extends Product implements Giftable {
    /**
     * The weight of the physical product.
     */
    private double weight;
    /**
     * Indicates whether the physical product can be given as a gift.
     */
    private boolean giftable;
    /**
     * An optional message to include with the gift of the physical product.
     */
    private String message;

    /**
     * Creates a new instance of the PhysicalProduct class.
     *
     * @param name              - The name of the physical product.
     * @param description       - A brief description of the physical product.
     * @param availableQuantity - The quantity of the physical product available for purchase.
     * @param price             - The price of the physical product.
     * @param weight            - The weight of the physical product.
     * @param giftable          - Indicates whether the physical product can be given as a gift.
     */
    public PhysicalProduct(String name, String description, int availableQuantity, double price, double weight, boolean giftable) {
        super(name, description, availableQuantity, price);
        setWeight(weight);
        setGiftable(giftable);
    }

    /**
     * Creates a new instance of the PhysicalProduct class with an optional message to include with the gift of the physical product.
     *
     * @param name              - The name of the physical product.
     * @param description       - A brief description of the physical product.
     * @param availableQuantity - The quantity of the physical product available for purchase.
     * @param price             - The price of the physical product.
     * @param weight            - The weight of the physical product.
     * @param giftable          - Indicates whether the physical product can be given as a gift.
     * @param message           - An optional message to include with the gift of the physical product.
     */
    public PhysicalProduct(String name, String description, int availableQuantity, double price, double weight, boolean giftable, String message) {
        super(name, description, availableQuantity, price);
        setWeight(weight);
        setGiftable(giftable);
        if (this.giftable) setMessage(message);
    }

    /**
     * Validates that the weight of a physical product is greater than 0.
     *
     * @param weight - The weight of the product to validate.
     * @return {number} The validated weight.
     * @throws IllegalArgumentException -  If the weight is less than or equal to 0.
     */
    public static double validateWeight(double weight) throws IllegalArgumentException {
        if (weight <= 0)
            throw new IllegalArgumentException("Product weight (Entered: \"" + weight + "\") must be higher than 0");
        return weight;
    }

    /**
     * This function returns the weight of the object.
     *
     * @return The weight of the object.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * If the weight is less than 0, set the weight to 0. Otherwise, set the weight to the value passed
     * in.
     *
     * @param weight - The weight of the item.
     */
    public void setWeight(double weight) {
        this.weight = validateWeight(weight);
    }

    /**
     * The function returns a string that contains the toString() of the superclass, the weight of the
     * object, whether the object is giftable, and if the object is giftable, the message that
     * is associated with the object
     *
     * @return The toString method is being overridden to return the superclass toString method, the
     * weight, giftable, and message.
     */
    @Override
    public String toString() {
        return super.toString() + "\n   | Weight: " + getWeight() + "\n   | Giftable: " + getGiftable() + (getGiftable() ? "\n   | Message: " + getMessage() : "");
    }

    /**
     * If the type is PHYSICAL, then return the string "PHYSICAL - " concatenated with the name of the
     * object.
     *
     * @return The string "PHYSICAL - " concatenated with the value of the getName() method.
     */
    public String getTypedName() {
        return "PHYSICAL - " + getName();
    }

    /**
     * It returns the value of the giftable variable.
     *
     * @return The giftable boolean is being returned.
     */
    @Override
    public boolean getGiftable() {
        return giftable;
    }

    /**
     * If the giftable boolean is set to false, then the message is set to null.
     *
     * @param giftable - Whether the item is giftable.
     */
    @Override
    public void setGiftable(boolean giftable) {
        this.giftable = giftable;
        if (!giftable) message = null;
    }

    /**
     * The function `getMessage()` returns the message of the exception
     *
     * @return The message is being returned.
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * If the product is not giftable, throw an exception.
     *
     * @param message - The message to be displayed on the gift card.
     */
    @Override
    public void setMessage(String message) {
        if (!giftable) throw new IllegalStateException("Product is not set as giftable so message cannot be set.");
        this.message = message;
    }
}
