package S3800978.sources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private Product physicalProduct;
    private Product digitalProduct;

    @BeforeEach
    void setUp() {
        App.getAllProducts().clear();
        physicalProduct = new PhysicalProduct("Physical Product", "Description", 2, 29.99, 5, true, "message");
        digitalProduct = new DigitalProduct("Digital Product", "Description", 0, 50, true, "message");
    }

    @Test
    @DisplayName("Only unique name")
    void uniqueName() {
        //noinspection ObviousNullCheck
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new PhysicalProduct("Physical Product", "Desc", 1, 1, 1)),
                () -> assertThrows(IllegalArgumentException.class, () -> new DigitalProduct("Physical Product", "Desc", 1, 1, true, "hello")),
                () -> assertNotNull(new DigitalProduct("Unique Name", "Description", 1, 2, true, "Message")));
    }

    @Test
    void getName() {
        assertEquals("Digital Product", digitalProduct.getName());
    }

    @Test
    void setName() {
        digitalProduct.setName("New name");
        assertEquals("New name", digitalProduct.getName());
    }

    @Test
    @DisplayName("Name shows with prefix")
    void getTypedName() {
        assertAll(
                //  Return correct name with prefix
                () -> assertEquals("PHYSICAL - Physical Product", physicalProduct.getTypedName()),
                () -> assertEquals("DIGITAL - Digital Product", digitalProduct.getTypedName()),

                // Type not mixed
                () -> assertNotEquals("DIGITAL - Physical Product", physicalProduct.getTypedName()),
                () -> assertNotEquals("PHYSICAL - Digital Product", digitalProduct.getTypedName()),

                // Prefix correct
                () -> assertTrue(physicalProduct.getTypedName().matches("^\\bPHYSICAL - .*"), "Prefix for PHYSICAL type incorrect"),
                () -> assertTrue(digitalProduct.getTypedName().matches("^\\bDIGITAL - .*"), "Prefix for DIGITAL type incorrect"),

                // Prefix not mixed
                () -> assertFalse(digitalProduct.getTypedName().matches("^\\bPHYSICAL - .*")),
                () -> assertFalse(physicalProduct.getTypedName().matches("^\\bDIGITAL - .*"))
        );
    }

    @Test
    void getDescription() {
        assertEquals("Description", digitalProduct.getDescription());
    }

    @Test
    void setDescription() {
        digitalProduct.setDescription("New description");
        assertEquals("New description", digitalProduct.getDescription());
    }

    @Test
    @DisplayName("setDescription() with blank string uses default \"No description\"")
    void setDescriptionDefault() {
        physicalProduct.setDescription("");
        assertEquals("No description", physicalProduct.getDescription());
    }

    @Test
    void getAvailableQuantity() {
        assertAll(
                () -> assertEquals(2, physicalProduct.getAvailableQuantity()),
                () -> assertEquals(0, digitalProduct.getAvailableQuantity())
        );
    }

    @Test
    @DisplayName("setAvailableQuantity(), non-negative")
    void setAvailableQuantity() {
        assertAll(
                () -> {
                    digitalProduct.setAvailableQuantity(5);
                    assertEquals(5, digitalProduct.getAvailableQuantity());
                },
                () -> {
                    digitalProduct.setAvailableQuantity(0);
                    assertEquals(0, digitalProduct.getAvailableQuantity());
                },
                () -> assertThrows(IllegalArgumentException.class, () -> digitalProduct.setAvailableQuantity(-5), "Setting negative value is supposed to throw but didn't")
        );
    }

    @Test
    void getPrice() {
        assertAll(
                () -> assertEquals(29.99, physicalProduct.getPrice()),
                () -> assertEquals(50, digitalProduct.getPrice())
        );
    }

    @Test
    @DisplayName("setPrice(), non-negative, non-zero")
    void setPrice() {
        assertAll(
                () -> {
                    physicalProduct.setPrice(259.12345);
                    assertEquals(259.12345, physicalProduct.getPrice());
                },
                () -> assertThrows(IllegalArgumentException.class, () -> digitalProduct.setPrice(-5.52), "Setting negative value is supposed to throw but didn't"),
                () -> assertThrows(IllegalArgumentException.class, () -> digitalProduct.setPrice(0), "Setting value = 0 value is supposed to throw but didn't")
        );
    }

    @Test
    void addToCart() {
        assertAll(
                () -> {
                    digitalProduct.setAvailableQuantity(5);
                    assertAll(
                            () -> assertTrue(digitalProduct.addToCart()),
                            () -> assertEquals(4, digitalProduct.getAvailableQuantity())
                    );
                },
                () -> {
                    physicalProduct.setAvailableQuantity(0);
                    assertAll(
                            () -> assertFalse(physicalProduct.addToCart()),
                            () -> assertEquals(0, physicalProduct.getAvailableQuantity())
                    );
                }
        );
    }

    @Test
    void quantityAddOne() {
        int prevQuantity = digitalProduct.getAvailableQuantity();
        digitalProduct.quantityAddOne();
        assertEquals(prevQuantity + 1, digitalProduct.getAvailableQuantity());
    }

    @Test
    @DisplayName("toString() returns full product details with formatting")
    void testToString() {
        assertAll(
                () -> assertEquals("""
                        \uD83D\uDCE6 Product
                           | Name: PHYSICAL - Physical Product
                           | Description: Description
                           | Available quantity: 2
                           | Price: 29.99
                           | Type: PhysicalProduct
                           | Weight: 5.0
                           | Giftable: true
                           | Message: message""", physicalProduct.toString()),
                () -> assertEquals("""
                        \uD83D\uDCE6 Product
                           | Name: DIGITAL - Digital Product
                           | Description: Description
                           | Available quantity: 0
                           | Price: 50.0
                           | Type: DigitalProduct
                           | Giftable: true
                           | Message: message""", digitalProduct.toString())
        );
    }
}