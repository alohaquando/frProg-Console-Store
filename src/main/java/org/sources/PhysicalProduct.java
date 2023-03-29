package org.sources;

public class PhysicalProduct extends Product implements Giftable {
    private double weight;
    private boolean giftable;
    private String message;

    public PhysicalProduct(String name, String description, int availableQuantity, double price, double weight) {
        super(name, description, availableQuantity, price);
        setWeight(weight);
    }

    public PhysicalProduct(String name, String description, int availableQuantity, double price, double weight, boolean giftable) {
        super(name, description, availableQuantity, price);
        setWeight(weight);
        setGiftable(giftable);
    }

    public PhysicalProduct(String name, String description, int availableQuantity, double price, double weight, boolean giftable, String message) {
        super(name, description, availableQuantity, price);
        setWeight(weight);
        setGiftable(giftable);
        if (this.giftable) setMessage(message);
    }

    public static double validateWeight(double weight) throws IllegalArgumentException {
        if (weight <= 0)
            throw new IllegalArgumentException("Product weight (Entered: \"" + weight + "\") must be higher than 0");
        return weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = validateWeight(weight);
    }

    @Override
    public String toString() {
        return super.toString() +
                "\n   | Weight: " + getWeight() +
                "\n   | Giftable: " + getGiftable() +
                (getGiftable() ? "\n   | Message: " + getMessage() : "");
    }

    public String getTypedName() {
        return "PHYSICAL - " + getName();
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
