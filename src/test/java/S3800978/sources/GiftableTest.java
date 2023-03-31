package S3800978.sources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GiftableTest {
    private PhysicalProduct physicalProduct;
    private DigitalProduct digitalProduct;

    @BeforeEach
    void setUp() {
        App.getAllProducts().clear();
        physicalProduct = new PhysicalProduct("Physical Product", "Description", 2, 29.99, 5, true, "message");
        digitalProduct = new DigitalProduct("Digital Product", "Description", 0, 50);
    }

    @Test
    void getGiftable() {
        assertAll(
                () -> assertTrue(physicalProduct.getGiftable()),
                () -> assertFalse(digitalProduct.getGiftable())
        );
    }

    @Test
    void setGiftable() {
        assertAll(
                () -> {
                    physicalProduct.setGiftable(false);
                    assertFalse(physicalProduct.getGiftable());
                },
                () -> {
                    digitalProduct.setGiftable(true);
                    assertTrue(digitalProduct.getGiftable());
                },
                () -> {
                    DigitalProduct digitalProduct1 = new DigitalProduct("Digital2", "Desc", 1,1,true, "This is long message");
                    digitalProduct1.setGiftable(false);
                    assertNull(digitalProduct1.getMessage(), "Setting giftable=false should have set message=null");
                }
        );
    }

    @Test
    void getMessage() {
        assertAll(
                () -> assertEquals("message", physicalProduct.getMessage()),
                () -> assertNull(digitalProduct.getMessage(), "Supposed to return null if no message set")
        );
    }

    @Test
    void setMessage() {
        assertAll(
                () -> {
                    physicalProduct.setMessage("Hello");
                    assertEquals("Hello", physicalProduct.getMessage());
                },
                () -> assertThrows(IllegalStateException.class, () -> digitalProduct.setMessage("Hello")),
                () -> {
                    physicalProduct.setMessage("");
                    assertEquals("No message", physicalProduct.getMessage(), "Supposed to set message as \"No message\" if blank message set");
                }
        );
    }

    @Test
    @DisplayName("setMessage() throws if giftable = false")
    void nonGiftableNoMessage() {
        assertAll(
                () -> {
                    PhysicalProduct physicalProduct2 = new PhysicalProduct("Physical Product 2", "Description", 2, 29.99, 5);
                    assertThrows(IllegalStateException.class, () -> physicalProduct2.setMessage("Illegal message"));
                },
                () -> {
                    DigitalProduct digitalProduct2 = new DigitalProduct("Digital Product 2", "Description", 2, 29.99, false, "message");
                    assertThrows(IllegalStateException.class, () -> digitalProduct2.setMessage("Illegal message"));
                }
        );
    }
}