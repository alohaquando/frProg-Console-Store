package S3800978.sources;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Menu for user to interact with the program
 *
 * @author Quan Hoang DO - S3800978
 */
abstract class Menu {
    /**
     * Menu loop will continue running while "running" is true
     */
    private static boolean running = true;
    /**
     * Check if defaultData is added and prevent the same data from being added twice
     */
    private static boolean defaultDataAdded = false;

    //region Menu functions

    /**
     * Prints the intro at start, then begin the main menu loop.
     * Waits for users to press "Enter" at the end of every function run, so they can view the output before continuing.
     */
    static void mainMenu() {
        Console.printIntro();
        while (running) {
            options();
            Input.enterToContinue();
        }
    }

    /**
     * Prints out the available options, asks the user to enter a number, and then executes the
     * corresponding function
     */
    private static void options() {
        // Options
        //// Title
        Console.printHeading("─────────────────────────────────────────────");
        Console.printHeading("Pick an option by entering its number." + "\n[EXTRA] options are included for ease of use.");

        //// Product
        Console.printHeading("\n\uD83D\uDCE6 • PRODUCT");
        System.out.println("1  | Create new product");
        System.out.println("2  | Edit existing product");
        System.out.println("3  | [EXTRA] Search for product");
        System.out.println("4  | [EXTRA] List all products");

        //// Cart
        Console.printHeading("\n\uD83D\uDED2 • CART");
        System.out.println("5  | Create new cart");
        System.out.println("6  | Add item to an existing cart");
        System.out.println("7  | Remove item from an existing cart");
        System.out.println("8  | Display amount of an existing cart");
        System.out.println("9  | Display all cart, from light → heavy");
        System.out.println("10 | [EXTRA] Display cart information");

        //// System
        Console.printHeading("\n\uD83D\uDD37 • SYSTEM");
        System.out.println("99 | [EXTRA] Add pre-made products and carts");
        System.out.println("0  | Quit");

        // Get choice and execute function. Re-run loop if choice doesn't match any options
        switch (Input.getChoice()) {
            case 0 -> endProgram();
            case 1 -> createProduct();
            case 2 -> editProduct();
            case 3 -> getProductInformation();
            case 4 -> getAllProducts();
            case 5 -> createCart();
            case 6 -> addToCart();
            case 7 -> removeFromCart();
            case 8 -> getCartAmount();
            case 9 -> getAllCartsSorted();
            case 10 -> getCartInformation();
            case 99 -> addDefaultData();
            default -> Console.printError("Entered choice doesn't match any available options. Please try again.");
        }
    }
    //endregion

    //region Product functions

    //region Create Product functions

    /**
     * Create a new product by asking the user to select a type, then calling the appropriate function
     * to create that type of product.
     */
    private static void createProduct() {
        Console.printTitle("Create new product");
        switch (Input.getChoice("Select a type", new String[]{"Physical product", "Digital product"})) {
            case 1 -> createPhysicalProduct();
            case 2 -> createDigitalProduct();
        }
    }

    /**
     * Create a physical product by asking the user for the name, description, available quantity,
     * price, weight, giftable, and message, and then print the created physical product.
     */
    private static void createPhysicalProduct() {
        Console.printTitle("Create physical product");

        // Get fields
        String name = Input.getString("Name", "not blank, unique", Product::validateName);
        String description = Input.getString("Description");
        int availableQuantity = Input.getInt("Available quantity", "0 or more", Product::validateAvailableQuantity);
        double price = Input.getDouble("Price", "larger than 0", Product::validatePrice);
        double weight = Input.getDouble("Weight", "larger than 0", PhysicalProduct::validateWeight);
        boolean giftable = Input.getBoolean("Giftable");
        String message = null;
        if (giftable) message = Input.getString("Message");

        // Print result
        Console.printSuccess("Physical production creation successful.");
        Console.printSuccess(String.valueOf(new PhysicalProduct(name, description, availableQuantity, price, weight, giftable, message)));
    }

    /**
     * Create a digital product by asking the user for the name, description, available quantity,
     * price, giftable, and message
     */
    private static void createDigitalProduct() {
        Console.printTitle("Create digital product");

        // Get fields
        String name = Input.getString("Name", "not blank, unique", Product::validateName);
        String description = Input.getString("Description");
        int availableQuantity = Input.getInt("Available quantity", "0 or more", Product::validateAvailableQuantity);
        double price = Input.getDouble("Price", "larger than 0", Product::validatePrice);
        boolean giftable = Input.getBoolean("Giftable");
        String message = null;
        if (giftable) message = Input.getString("Message");

        // Print result
        Console.printSuccess("Digital production creation successful.");
        Console.printSuccess(String.valueOf(new DigitalProduct(name, description, availableQuantity, price, giftable, message)));
    }
    //endregion

    /**
     * Edits an existing product, allowing the user to modify its properties such as description, available quantity, and price.
     * The product to be edited is selected by prompting the user to input the name of the product.
     * If the product does not exist, the user is notified and returned to the main menu.
     * The method then loops through available fields to edit based on the product type, allowing the user to make the desired changes.
     * For PhysicalProduct, the weight and giftable fields are available to edit
     * For DigitalProduct, only the giftable field is available.
     * If the product is giftable, the message field is also available to edit.
     */
    private static void editProduct() {
        Console.printTitle("Edit existing product");

        // Get product
        Product product = App.queryProduct("Name of product to edit");
        if (product == null) return;
        String productType = product.getClass().getSimpleName();

        // Editing loop
        boolean editing = true;
        do {
            // Build the available fields to edit
            ArrayList<String> productFields = new ArrayList<>(Arrays.asList("Description", "Available quantity", "Price"));
            switch (productType) {
                case "PhysicalProduct" -> {
                    productFields.add("Weight");
                    productFields.add("Giftable");
                }
                case "DigitalProduct" -> productFields.add("Giftable");
            }
            if (((Giftable) product).getGiftable()) productFields.add("Message");

            // Print current product information
            System.out.println(product);

            // Get user's choice
            int choice = Input.getChoiceWithQuit("Select field to edit", productFields.toArray(new String[0]));

            // Execute functions based on choice - for all product type
            switch (choice) {
                case 1 -> product.setDescription(Input.getString("Description"));
                case 2 ->
                        product.setAvailableQuantity(Input.getInt("Available quantity", "0 or more", Product::validateAvailableQuantity));
                case 3 -> product.setPrice(Input.getDouble("Price", "larger than 0", Product::validatePrice));
                case 0 -> editing = false;
                default -> {
                }
            }

            // Execute functions based on choice - extra functions for specific types
            switch (productType) {
                case ("PhysicalProduct") -> {
                    switch (choice) {
                        case 4 ->
                                ((PhysicalProduct) product).setWeight(Input.getDouble("Weight", "larger than 0", PhysicalProduct::validateWeight));
                        case 5 -> ((PhysicalProduct) product).setGiftable(Input.getBoolean("Giftable"));

                    }
                    if (((Giftable) product).getGiftable()) {
                        if (choice == 6) {
                            ((PhysicalProduct) product).setMessage(Input.getString("Message"));
                        }
                    }
                }
                case ("DigitalProduct") -> {
                    if (choice == 4) {
                        ((DigitalProduct) product).setGiftable(Input.getBoolean("Giftable"));
                    }
                    if (((Giftable) product).getGiftable()) {
                        if (choice == 5) {
                            ((DigitalProduct) product).setMessage(Input.getString("Message"));
                        }
                    }
                }
            }
        } while (editing);
    }

    /**
     * Prompts the user to search for a product and displays its information if found.
     */
    private static void getProductInformation() {
        Console.printTitle("Search for product");

        Product product = App.queryProduct("Name of product to view");
        if (product != null) System.out.println(product);
    }

    /**
     * Prints all products available in the App.
     * If there are no products, it prints an error message.
     **/
    private static void getAllProducts() {
        Console.printTitle("List all products");

        if (App.getAllProducts().isEmpty()) {
            Console.printError("No product have been added");
        } else {
            App.getAllProducts().values().forEach(product -> System.out.println("\n" + product));
        }
    }

    //endregion

    //region Cart functions

    /**
     * Creates a new empty cart and prints its details.
     */
    private static void createCart() {
        Console.printTitle("Create new cart");

        Console.printSuccess("Cart creation successful.");
        Console.printSuccess(String.valueOf(new Cart()));
    }

    /**
     * Prompts user to add a product to an existing cart.
     * If no carts or products are available, an error message will be displayed.
     * If the cart and product are valid, the product will be added to the cart.
     * Otherwise, an error message will be displayed.
     */
    private static void addToCart() {
        Console.printTitle("Add product to existing cart");

        Cart cart = App.queryCart("Cart number to add product to");
        assert cart != null;
        Console.printSuccess(String.valueOf(cart));

        Product product = App.queryProduct("Name of product to add");
        if (product == null) return;
        Console.printSuccess(String.valueOf(product));

        if (cart.addItem(product.getName())) {
            Console.printSuccess("Product " + product.getName() + " successfully added to cart number " + cart.getId());
            Console.printSuccess(cart.toString());
        } else {
            Console.printError("Product " + product.getName() + " NOT added to cart number " + cart.getId());
        }
    }

    /**
     * Remove a product from an existing cart
     */
    private static void removeFromCart() {
        Console.printTitle("Remove product from existing cart");

        Cart cart = App.queryCart("Cart number to remove product from");
        assert cart != null;
        Console.printSuccess(String.valueOf(cart));

        Product product = App.queryProduct("Name of product to remove");
        if (product == null) return;
        Console.printSuccess(String.valueOf(product));

        if (cart.removeItem(product.getName())) {
            Console.printSuccess("Product " + product.getName() + " successfully removed from cart number " + cart.getId());
            Console.printSuccess(cart.toString());
        } else {
            Console.printError("Product " + product.getName() + " NOT removed from cart number " + cart.getId());
        }
    }

    /**
     * Displays the amount breakdown of a cart
     */
    private static void getCartAmount() {
        Console.printTitle("Display existing cart's amount");

        Cart cart = App.queryCart("Cart to display amount");
        assert cart != null;

        Console.printSuccess("Cart " + cart.getId() + " amount breakdown: " + cart.getAmountBreakdown());
    }

    /**
     * Display all cart, sorted by weight, from lightest → heaviest
     */
    private static void getAllCartsSorted() {
        Console.printTitle("Display all cart, from light → heavy");

        if (App.getAllCarts().isEmpty()) {
            Console.printError("No cart have been added.");
        } else {
            App.queryAllCartsSorted().forEach(cart -> System.out.println("\n" + cart));
        }
    }

    /**
     * Displays the information of a cart
     */
    private static void getCartInformation() {
        Console.printTitle("Display cart information");

        Cart cart = App.queryCart("Number of the cart to view");
        assert cart != null;
        Console.printSuccess(String.valueOf(cart));
    }
    //endregion

    //region System functions

    /**
     * Add some default products and carts to help with testing.
     */
    private static void addDefaultData() {
        if (!defaultDataAdded) {
            Product p1 = new PhysicalProduct("Camera", "HD Point-and-Shoot Sany camera to save your moments forever", 5, 249.99, 5.75, true, "For your loved ones!");
            new PhysicalProduct("Laptop", "New OrangeBook Pro 14' with M1000 chipset", 0, 700, 2, false);
            Product p3 = new PhysicalProduct("TV", "Next-gen Sungsam TV with Googly TV", 1, 800, 100, false);
            Product p4 = new DigitalProduct("Gift card", "Spend at your local CircleMart", 4, 9.99, false);
            new DigitalProduct("Music subscription", "Listen all day with this subscription to SpotMP3", 0, 9.99, true, "For the music lovers in your life");
            Cart c1 = new Cart();
            c1.addItem(p4.getName());
            Cart c2 = new Cart();
            c2.addItem(p1.getName());
            c2.addItem(p3.getName());
            c2.addItem(p4.getName());
            Cart c3 = new Cart();
            c3.addItem(p1.getName());
            c3.addItem(p4.getName());

            Console.printSuccess("Products");
            Console.printSuccess("Some products have already been added to carts. Their available quantity shows the amount after they have been added.");
            App.getAllProducts().values().forEach(product -> System.out.println("\n" + product));
            Console.printSuccess("Carts");
            App.queryAllCartsSorted().forEach(cart -> System.out.println("\n" + cart));

            defaultDataAdded = true;
            Console.printSuccess("Pre-made data added successfully.");
        } else Console.printError("Pre-made data already added.");
    }

    /**
     * Ends the program
     */
    private static void endProgram() {
        Console.printTitle("Quit program");
        System.out.println("Quitting program...");
        System.out.println("Thank you for using.");
        running = false;
    }
    //endregion
}