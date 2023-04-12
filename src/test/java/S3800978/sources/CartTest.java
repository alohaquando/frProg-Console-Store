package S3800978.sources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Quan Hoang DO - S3800978
 */

class CartTest {
    private Cart c1;
    private Cart c2;
    private Cart c3;
    private Cart c4;

    @BeforeEach
    void setUp() {
        App.getAllProducts().clear();
        App.getAllCarts().clear();

        Product p1 = new PhysicalProduct("Camera", "HD Point-and-Shoot Sany camera to save your moments forever", 5, 249.99, 5.75, true, "For your loved ones!");
        new PhysicalProduct("Laptop", "New OrangeBook Pro 14' with M1000 chipset", 1, 700, 2, true);
        Product p3 = new PhysicalProduct("TV", "Next-gen Sungsam TV with Googly TV", 2, 800, 100);
        Product p4 = new DigitalProduct("Gift card", "Spend at your local CircleMart", 4, 9.99);
        new DigitalProduct("Music subscription", "Listen all day with this subscription to SpotMP3", 0, 9.99, true, "For the music lovers in your life");

        c1 = new Cart();
        c1.addItem(p4.getName());
        c2 = new Cart();
        c2.addItem(p1.getName());
        c2.addItem(p3.getName());
        c2.addItem(p4.getName());
        c3 = new Cart();
        c3.addItem(p1.getName());
        c3.addItem(p4.getName());
        c4 = new Cart();
        c4.addItem(p1.getName());
        c4.addItem(p3.getName());
        c4.addItem(p4.getName());
    }

    @Test
    @DisplayName("Carts are comparable by weight")
    void compareTo() {
        assertAll(
                () -> assertEquals(1, c2.compareTo(c3)),
                () -> assertEquals(0, c2.compareTo(c4)),
                () -> assertEquals(1, c2.compareTo(c1))
        );
    }

    @Test
    @DisplayName("toString() display carts details with formatting")
    void testToString() {
        assertEquals("""
                \uD83D\uDED2 Cart
                   | Cart number: 4
                   | Items in cart: 3
                     └─ PHYSICAL - TV
                     └─ DIGITAL - Gift card
                     └─ PHYSICAL - Camera
                   | Weight: 105.75
                   | Amount (NO shipping fee): 1059.98
                   | Shipping fee: 10.575
                   | Total amount (w/ shipping fee): 1070.555""", c4.toString());
    }

    @Test
    void addItem() {
        assertAll(
                () -> assertFalse(c1.addItem("Nonexistent product name"), "Item not in allProducts() should not be addable"),
                () -> assertFalse(c1.addItem("Gift card"), "Items already in cart should not be addable"),
                () -> assertFalse(c1.addItem("Music subscription"), "Items out of stock should not be addable"),
                () -> {
                   int prevQuantity =  App.getProduct("Camera").getAvailableQuantity();
                    assertAll(
                            () -> assertTrue(c1.addItem("Camera")),
                            () -> assertEquals(prevQuantity - 1, App.getProduct("Camera").getAvailableQuantity())
                    );
                }
        );
    }

    @Test
    void removeItem() {
        assertAll(
                () -> assertFalse(c4.removeItem("Music subscription")),
                () -> assertFalse(c4.removeItem("Non-existent product name")),
                () -> {
                    int prevQuantity = App.getProduct("TV").getAvailableQuantity();
                    assertAll(
                            () -> assertTrue(c4.removeItem("TV")),
                            () -> assertEquals(prevQuantity + 1, App.getProduct("TV").getAvailableQuantity())
                    );
                }
        );
    }

    @Test
    void cartAmount() {
        assertEquals((249.99 + 800 + 9.99) + BigDecimal.valueOf((5.75 + 100) * 0.1).setScale(3, RoundingMode.HALF_UP).doubleValue(), c2.cartAmount());
    }

    @Test
    void getId() {
        assertEquals(2, c2.getId());
    }

    @Test
    void getItemsInCart() {
        assertArrayEquals(new String[]{"Gift card", "Camera"}, c3.getItemsInCart().toArray());
    }

    @Test
    void getItemsInCartSize() {
        assertEquals(2, c3.getItemsInCartSize());
    }

    @Test
    void getAmountWithoutShippingFee() {
        assertEquals((249.99 + 800 + 9.99), c4.getAmountWithoutShippingFee());
    }

    @Test
    void getTotalShippingFee() {
        assertEquals(BigDecimal.valueOf((5.75 + 100) * 0.1).setScale(3, RoundingMode.HALF_UP).doubleValue(), c2.getTotalShippingFee());
    }

    @Test
    void getItemsInCartPrettified() {
        assertEquals("""
                3
                     └─ PHYSICAL - TV
                     └─ DIGITAL - Gift card
                     └─ PHYSICAL - Camera""", c4.getItemsInCartPrettified());
    }

    @Test
    void getAmountBreakdown() {
        assertEquals("""

                   | Amount (NO shipping fee): 1059.98
                   | Shipping fee: 10.575
                   | Total amount (w/ shipping fee): 1070.555\
                """, c2.getAmountBreakdown());
    }
}