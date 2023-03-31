package S3800978.sources;

/**
 * A Digital Product which extends from the main Product class.
 *
 * @author Quan Hoang DO - S3800978
 */
public class DigitalProduct extends Product implements Giftable {
    /**
     * Indicates whether the digital product is giftable.
     */
    private boolean giftable;
    /**
     * The message to include when gifting the digital product.
     */
    private String message;

    //region Constructors
    /**
     * Creates a new instance of the PhysicalProduct class.
     *
     * @param name              - The name of the physical product.
     * @param description       - A brief description of the physical product.
     * @param availableQuantity - The quantity of the physical product available for purchase.
     * @param price             - The price of the physical product.
     */
    public DigitalProduct(String name, String description, int availableQuantity, double price) {
        super(name, description, availableQuantity, price);
        setGiftable(false);
    }

    /**
     * Creates a new DigitalProduct object with the given properties.
     *
     * @param name              - The name of the digital product.
     * @param description       - A brief description of the digital product.
     * @param availableQuantity - The quantity of the digital product that is currently available for purchase.
     * @param price             - The price of the digital product.
     * @param giftable          - Indicates whether the digital product is giftable.
     */
    public DigitalProduct(String name, String description, int availableQuantity, double price, boolean giftable) {
        super(name, description, availableQuantity, price);
        setGiftable(giftable);
    }

    /**
     * Creates a new DigitalProduct object with the given properties.
     *
     * @param name              - The name of the digital product.
     * @param description       - A brief description of the digital product.
     * @param availableQuantity - The quantity of the digital product that is currently available for purchase.
     * @param price             - The price of the digital product.
     * @param giftable          - Indicates whether the digital product is giftable.
     * @param message           - An optional message to include with the digital product if it is purchased as a gift.
     */
    public DigitalProduct(String name, String description, int availableQuantity, double price, boolean giftable, String message) {
        super(name, description, availableQuantity, price);
        setGiftable(giftable);
        if (this.giftable) setMessage(message);
    }
    //endregion

    //region Getters and Setters
    // REQUIREMENT

    /**
     * Returns the name of the digital product, with the prefix "DIGITAL - ".
     *
     * @return {string} The typed name of the digital product.
     */
    public String getTypedName() {
        return "DIGITAL - " + getName();
    }
    //endregion

    //region toString()
    // REQUIREMENT

    /**
     * Returns a string representation of the DigitalProduct object.
     *
     * @return {string} The string representation of the DigitalProduct object, including information on whether it is giftable and an optional gift message.
     */
    @Override
    public String toString() {
        return super.toString() + "\n   | Giftable: " + getGiftable() + (getGiftable() ? "\n   | Message: " + getMessage() : "");
    }
    //endregion

    //region Giftable Interface implementation

    /**
     * Returns the giftable status of the product
     *
     * @return The value of the giftable variable.
     */
    @Override
    public boolean getGiftable() {
        return giftable;
    }

    /**
     * Sets the giftable attribute of the DigitalProduct object and updates the message attribute if the digital product is no longer giftable.
     *
     * @param giftable - Indicates whether the digital product is giftable.
     */
    @Override
    public void setGiftable(boolean giftable) {
        this.giftable = giftable;
        if (!giftable) message = null;
    }

    // REQUIREMENT

    /**
     * Return the message for a giftable product
     *
     * @return The message is being returned.
     */
    @Override
    public String getMessage() {
        if (message == null) return null;
        return (message.isBlank()) ? "No message" : message;
    }

    // REQUIREMENT

    /**
     * Sets the message of a giftable product.
     *
     * @param message - The message to set.
     * @throws IllegalStateException - If the product is not giftable.
     */
    @Override
    public void setMessage(String message) {
        if (!giftable) throw new IllegalStateException("Product is not set as giftable so message cannot be set.");
        this.message = (message.isBlank()) ? "No message" : message;
    }
    //endregion
}
