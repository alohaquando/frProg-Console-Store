package S3800978.sources;

/**
 * A Physical Product which extends from the main Product class.
 *
 * @author Quan Hoang DO - S3800978
 */
public class PhysicalProduct extends Product implements Giftable {
    /**
     * The weight of the physical product.
     */
    private double weight; // REQUIREMENT
    /**
     * Indicates whether the physical product can be given as a gift.
     */
    private boolean giftable;
    /**
     * An optional message to include with the gift of the physical product.
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
     * @param weight            - The weight of the physical product.
     */
    public PhysicalProduct(String name, String description, int availableQuantity, double price, double weight) {
        super(name, description, availableQuantity, price);
        setWeight(weight);
        setGiftable(false);
    }

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
    //endregion

    //region Validators

    /**
     * Validates that the weight of a physical product is greater than 0.
     *
     * @param weight - The weight of the product to validate.
     * @return {number} The validated weight.
     * @throws IllegalArgumentException -  If the weight is less than or equal to 0.
     */
    static double validateWeight(double weight) throws IllegalArgumentException {
        if (weight <= 0)
            throw new IllegalArgumentException("Product weight (Entered: \"" + weight + "\") must be higher than 0");
        return weight;
    }
    //endregion

    //region Getters and Setters
    // REQUIREMENT

    /**
     * Returns the weight of the object.
     *
     * @return The weight of the object.
     */
    public double getWeight() {
        return weight;
    }

    // REQUIREMENT

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
     * Return the string "PHYSICAL - " concatenated with the name of the
     * object.
     *
     * @return The string "PHYSICAL - " concatenated with the value of the getName() method.
     */
    // REQUIREMENT
    public String getTypedName() {
        return "PHYSICAL - " + getName();
    }
    //endregion

    //region toString()
    // REQUIREMENT

    /**
     * The String representation of the physical product, containing information from the Product class
     * and specific information of the PhysicalProduct class.
     *
     * @return The String representation of the product.
     */
    @Override
    public String toString() {
        return super.toString() + "\n   | Weight: " + getWeight() + "\n   | Giftable: " + getGiftable() + (getGiftable() ? "\n   | Message: " + getMessage() : "");
    }
    //endregion

    //region Giftable Interface implementation

    /**
     * Returns the value of the giftable variable.
     *
     * @return The giftable boolean is being returned.
     */
    @Override
    public boolean getGiftable() {
        return giftable;
    }

    /**
     * Sets the giftable attribute of the PhysicalProduct object and updates the message attribute if the physical product is no longer giftable.
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
