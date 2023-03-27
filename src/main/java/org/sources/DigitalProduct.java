package org.sources;

public class DigitalProduct extends Product implements Giftable {
    boolean giftable;
    String message;

    public DigitalProduct(String name, String description, int availableQuantity, double price) {
        super(name, description, availableQuantity, price);
    }

    public DigitalProduct(String name, String description, int availableQuantity, double price, boolean giftable) {
        super(name, description, availableQuantity, price);
        setGiftable(giftable);
    }

    public DigitalProduct(String name, String description, int availableQuantity, double price, boolean giftable, String message) {
        super(name, description, availableQuantity, price);
        setGiftable(giftable);
        if (this.giftable) setMessage(message);
    }

    public String getTypedName() {
        return "DIGITAL - " + getName();
    }

    @Override
    public String toString() {
        return super.toString() +
                "\n   | Giftable: " + getGiftable() +
                (getGiftable() ? "\n   | Message: " + getMessage() : "");
    }

    @Override
    public void setGiftable(boolean giftable) {
        this.giftable = giftable;
        if (!giftable) message = null;
    }

    @Override
    public boolean getGiftable() {
        return giftable;
    }

    @Override
    public void setMessage(String message) {
        if (!giftable) throw new IllegalStateException("Product is not set as giftable so message cannot be set.");
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
