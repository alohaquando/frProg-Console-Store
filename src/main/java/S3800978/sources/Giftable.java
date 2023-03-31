package S3800978.sources;

/**
 * The Giftable interface provides methods for determining if a product can be given as a gift and for setting and getting an optional message to include with the gift.
 *
 * @author Quan Hoang DO - S3800978
 *
 */
interface Giftable {
    /**
     * Gets whether the product can be given as a gift.
     *
     * @return {boolean} - True if the product can be given as a gift; false otherwise.
     */
    boolean getGiftable();

    /**
     * Sets whether the product can be given as a gift.
     *
     * @param giftable - True if the product can be given as a gift; false otherwise.
     */
    void setGiftable(boolean giftable);

    // REQUIREMENT
    /**
     * Gets the message to include with the gift, if any.
     *
     * @return {string|null} - The message to include with the gift, or null if no message has been set.
     */
    String getMessage();

    // REQUIREMENT
    /**
     * Sets the message to include with the gift.
     *
     * @param message - The message to include with the gift, or null to remove any existing message.
     */
    void setMessage(String message);
}
