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

class MenuBackendTest {
    @BeforeEach
    void setUp() {
        App.getAllProducts().clear();
        App.getAllCarts().clear();
    }

    @Test
    @DisplayName("Create new product")
    void createNewProduct() {
        assertSame(new PhysicalProduct("Physical Product", "Description", 1, 2, 3, true, "Message"), App.getProduct("Physical Product"));
    }

    @Test
    @DisplayName("Edit product")
    void editProduct() {
        PhysicalProduct p = new PhysicalProduct("Physical Product", "Description", 1, 2, 3);

        p.setDescription("New description");
        p.setAvailableQuantity(12345);
        p.setPrice(123.45);
        p.setWeight(67.89);
        p.setGiftable(true);
        p.setMessage("New message");

        assertAll(
                () -> assertEquals("New description", p.getDescription()),
                () -> assertEquals(12345, p.getAvailableQuantity()),
                () -> assertEquals(123.45, p.getPrice()),
                () -> assertEquals(67.89, p.getWeight()),
                () -> assertTrue(p.getGiftable()),
                () -> assertEquals("New message", p.getMessage())
        );
    }

    @Test
    @DisplayName("Create new shopping cart")
    void createNewCart() {
        assertSame(new Cart(), App.getCart(0));
    }

    @Test
    @DisplayName("Add product to cart")
    void addProductToCart() {
        new PhysicalProduct("Camera", "HD Point-and-Shoot Sany camera to save your moments forever", 5, 249.99, 5.75, true, "For your loved ones!");
        Cart c1 = new Cart();
        int prevQuantity = App.getProduct("Camera").getAvailableQuantity();
        assertAll(
                () -> assertTrue(c1.addItem("Camera")),
                () -> assertEquals(prevQuantity - 1, App.getProduct("Camera").getAvailableQuantity())
        );
    }

    @Test
    @DisplayName("Remove product from cart")
    void removeProductFromCart() {
        new PhysicalProduct("Camera", "HD Point-and-Shoot Sany camera to save your moments forever", 5, 249.99, 5.75, true, "For your loved ones!");
        Cart c1 = new Cart();
        c1.addItem("Camera");
        int prevQuantity = App.getProduct("Camera").getAvailableQuantity();
        assertAll(
                () -> assertTrue(c1.removeItem("Camera")),
                () -> assertEquals(prevQuantity + 1, App.getProduct("Camera").getAvailableQuantity())
        );
    }

    @Test
    @DisplayName("Display cart amount")
    void displayCartAmount() {
        new PhysicalProduct("Camera", "HD Point-and-Shoot Sany camera to save your moments forever", 5, 249.99, 5.75, true, "For your loved ones!");
        new PhysicalProduct("TV", "Next-gen Sungsam TV with Googly TV", 2, 800, 100);
        new DigitalProduct("Gift card", "Spend at your local CircleMart", 4, 9.99);

        Cart c1 = new Cart();
        c1.addItem("Camera");
        c1.addItem("TV");
        c1.addItem("Gift card");

        assertEquals((249.99 + 800 + 9.99) + BigDecimal.valueOf((5.75 + 100) * 0.1).setScale(3, RoundingMode.HALF_UP).doubleValue(), c1.cartAmount());
    }

    @Test
    @DisplayName("Create another cart")
    void createAnotherCart() {
        assertSame(new Cart(), App.getCart(0));
        assertSame(new Cart(), App.getCart(1));
    }

    @Test
    @DisplayName("Display all carts based on weight")
    void allCartsBasedOnWeight() {
        Product p1 = new PhysicalProduct("Camera", "HD Point-and-Shoot Sany camera to save your moments forever", 5, 249.99, 5.75, true, "For your loved ones!");
        Product p3 = new PhysicalProduct("TV", "Next-gen Sungsam TV with Googly TV", 2, 800, 100);
        Product p4 = new DigitalProduct("Gift card", "Spend at your local CircleMart", 4, 9.99);

        Cart c1 = new Cart();
        c1.addItem(p4.getName());
        Cart c2 = new Cart();
        c2.addItem(p1.getName());
        c2.addItem(p3.getName());
        c2.addItem(p4.getName());
        Cart c3 = new Cart();
        c3.addItem(p1.getName());
        c3.addItem(p4.getName());
        Cart c4 = new Cart();
        c4.addItem(p1.getName());
        c4.addItem(p3.getName());
        c4.addItem(p4.getName());

        assertAll(
                () -> assertEquals(1, c2.compareTo(c3)),
                () -> assertEquals(0, c2.compareTo(c4)),
                () -> assertEquals(1, c2.compareTo(c1))
        );
    }
}