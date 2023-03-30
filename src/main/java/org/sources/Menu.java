package org.sources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * It's a menu that allows the user to create products and carts, and add products to carts
 *
 * @author Quan Hoang DO - S3800978
 */
public class Menu {
    static Scanner scanner = new Scanner(System.in);
    static boolean running = true;
    static boolean defaultDataAdded = false;


    /**
     * This function prints the intro, then runs the options function, then waits for the user to press
     * enter to continue
     */
    static void mainMenu() {
        System.out.println(Console.intro);
        while (running) {
            options();
            Input.enterToContinue();
        }
    }

    /**
     * It prints out the available options, asks the user to enter a number, and then executes the
     * corresponding function
     */
    private static void options() {
        System.out.println(Console.ANSI_BLUE + "\n─────────────────────────────────────────────" + Console.ANSI_RESET);
        System.out.println(Console.ANSI_BLUE + "\nPick an option by entering its number." + "\n[EXTRA] options are included for ease of use." + Console.ANSI_RESET);
        System.out.println(Console.ANSI_BLUE + "\n\uD83D\uDCE6 • PRODUCT" + Console.ANSI_RESET);
        System.out.println("1  | Create new product");
        System.out.println("2  | Edit existing product");
        System.out.println("3  | [EXTRA] Search for product");
        System.out.println("4  | [EXTRA] List all products");
        System.out.println(Console.ANSI_BLUE + "\n\uD83D\uDED2 • CART" + Console.ANSI_RESET);
        System.out.println("5  | Create new cart");
        System.out.println("6  | Add item to an existing cart");
        System.out.println("7  | Remove item from an existing cart");
        System.out.println("8  | Display amount of an existing cart");
        System.out.println("9  | Display all cart, from light → heavy");
        System.out.println("10 | [EXTRA] Display cart information");
        System.out.println(Console.ANSI_BLUE + "\n\uD83D\uDD37 • SYSTEM" + Console.ANSI_RESET);
        System.out.println("99 | [EXTRA] Add pre-made products and carts");
        System.out.println("0  | Quit");

        System.out.print("\nYour choice: ");

        int choice = -1;

        try {
            choice = parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            Console.printError("Please enter a number");
        }

        System.out.println();

        switch (choice) {
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

    // Product

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
        String name = Input.getString("Name", "not blank, unique", Product::validateName);
        String description = Input.getString("Description");
        int availableQuantity = Input.getInt("Available quantity", "0 or more", Product::validateAvailableQuantity);
        double price = Input.getDouble("Price", "larger than 0", Product::validatePrice);
        double weight = Input.getDouble("Weight", "larger than 0", PhysicalProduct::validateWeight);
        boolean giftable = Input.getBoolean("Giftable");
        String message = null;
        if (giftable) message = Input.getString("Message");

        PhysicalProduct physicalProduct = new PhysicalProduct(name, description, availableQuantity, price, weight, giftable, message);
        Console.printSuccess("Physical production creation successful.");
        Console.printSuccess(String.valueOf(physicalProduct));
    }

    /**
     * Create a digital product by asking the user for the name, description, available quantity,
     * price, giftable, and message
     */
    private static void createDigitalProduct() {
        Console.printTitle("Create digital product");
        String name = Input.getString("Name", "not blank, unique", Product::validateName);
        String description = Input.getString("Description");
        int availableQuantity = Input.getInt("Available quantity", "0 or more", Product::validateAvailableQuantity);
        double price = Input.getDouble("Price", "larger than 0", Product::validatePrice);
        boolean giftable = Input.getBoolean("Giftable");
        String message = null;
        if (giftable) message = Input.getString("Message");

        DigitalProduct digitalProduct = new DigitalProduct(name, description, availableQuantity, price, giftable, message);
        Console.printSuccess("Digital production creation successful.");
        Console.printSuccess(String.valueOf(digitalProduct));
    }

    /**
     * Prompts the user to enter a product name, validates the input, and returns the corresponding product object
     * if it exists in the application's product list.
     *
     * @return {Product|null} The product object if it exists, or null if it does not.
     */
    private static Product getProduct() {
        String query = Input.getString("Product name", "not empty", Input::validateStringNotBlank);
        if (App.getAllProducts().containsKey(query)) {
            Console.printSuccess("\nProduct found.");
            return App.getAllProducts().get(query);
        } else {
            Console.printError("\nProduct not found.");
            return null;
        }
    }

    /**
     * Prompts the user to enter a product name using a custom prompt, validates the input, and returns the corresponding
     * product object if it exists in the application's product list.
     *
     * @param prompt The prompt to display to the user when requesting the product name.
     * @return {Product|null} The product object if it exists, or null if it does not.
     */
    private static Product getProduct(String prompt) {
        String query = Input.getString(prompt, "not empty", Input::validateStringNotBlank);
        if (App.getAllProducts().containsKey(query)) {
            Console.printSuccess("Product found.");
            return App.getAllProducts().get(query);
        } else {
            Console.printError("Product not found.");
            return null;
        }
    }

    /**
     * Edits an existing product, allowing the user to modify its properties such as description, available quantity, and price.
     * The product to be edited is selected by prompting the user to input the name of the product.
     * If the product does not exist, the user is notified and returned to the main menu.
     * The method then loops through available fields to edit based on the product type, allowing the user to make the desired changes.
     * For PhysicalProduct, the weight and giftable fields are available to edit, and for DigitalProduct, only the giftable field is available.
     * If the product is giftable, the message field is also available to edit.
     */
    private static void editProduct() {
        Console.printTitle("Edit existing product");

        if (App.getAllProducts().isEmpty()) {
            Console.printError("No product to edit. Create one first.");
            return;
        }

        Product product = getProduct();

        if (product == null) return;

        boolean editing = true;
        int choice;
        do {
            // Build the available fields to edit
            ArrayList<String> productFields = new ArrayList<>(Arrays.asList("Description", "Available quantity", "Price"));
            String productType = product.getClass().getSimpleName();
            switch (productType) {
                case "PhysicalProduct" -> {
                    productFields.add("Weight");
                    productFields.add("Giftable");
                }
                case "DigitalProduct" -> productFields.add("Giftable");
            }
            if (((Giftable) product).getGiftable()) productFields.add("Message");

            System.out.println(product);
            choice = Input.getChoiceWithQuit("Select field to edit", productFields.toArray(new String[0]));


            switch (choice) {
                case 1 -> product.setDescription(Input.getString("Description"));
                case 2 ->
                        product.setAvailableQuantity(Input.getInt("Available quantity", "0 or more", Product::validateAvailableQuantity));
                case 3 -> product.setPrice(Input.getDouble("Price", "larger than 0", Product::validatePrice));
                case 0 -> editing = false;
                default -> {
                }
            }

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

        if (App.getAllProducts().isEmpty()) {
            Console.printError("No product to show. Create one first.");
            return;
        }

        Product product = getProduct();
        if (product != null) System.out.println(product);
    }

    /**
     * Prints all products available in the App.
     * If there are no products, it prints an error message.
     **/
    private static void getAllProducts() {
        Console.printTitle("List all products");

        if (App.getAllProducts().isEmpty()) {
            Console.printError("No product to show. Create one first.");
            return;
        }

        App.getAllProducts().values().forEach(product -> System.out.println("\n" + product));
    }

    // Carts

    /**
     * Creates a new empty cart and prints its details.
     *
     */
    private static void createCart() {
        Console.printTitle("Create new cart");

        Cart cart = new Cart();
        Console.printSuccess("Cart creation successful.");
        Console.printSuccess(String.valueOf(cart));
    }

    /**
     * Retrieves a cart from the list of all available carts in the App, based on the user's input.
     *
     * @return {Cart} The selected Cart object, or null if not found.
     */
    private static Cart getCart() {
        int query = Input.getInt("Cart number", "not empty", Menu::validateCartExist);
        query -= 1;

        return App.getCart(query);
    }

    /**
     * Retrieves a cart from the list of all carts based on the user's input.
     * @param prompt - The prompt message to display to the user when requesting input.
     * @return {Cart|null} - The retrieved cart object, or null if not found.
     */
    private static Cart getCart(String prompt) {
        int query = Input.getInt(prompt, "not empty", Menu::validateCartExist);
        query -= 1;
        if (App.getAllCarts().get(query) != null) {
            Console.printSuccess("\nCart found.");
            return App.getAllCarts().get(query);
        } else {
            Console.printError("\nCart not found.");
            return null;
        }
    }

    /**
     * Validates that the given cart number corresponds to an existing cart in the system.
     *
     * @param cartNumber the cart number to validate
     * @return the validated cart number, if it corresponds to an existing cart
     * @throws IllegalArgumentException if the cart number is less than 1 or greater than the number of created carts
     */
    private static int validateCartExist(int cartNumber) throws IllegalArgumentException {
        if (cartNumber < 1 || cartNumber > App.getAllCarts().size())
            throw new IllegalArgumentException("Cart number (entered: \"" + cartNumber + "\") must match a created cart");
        return cartNumber;
    }

    /**
     * Prompts user to add a product to an existing cart.
     * If no carts or products are available, an error message will be displayed.
     * If the cart and product are valid, the product will be added to the cart.
     * Otherwise, an error message will be displayed.
     */
    private static void addToCart() {
        Console.printTitle("Add product to existing cart");

        if (App.getAllCarts().isEmpty()) {
            Console.printError("No cart to add to. Create one first.");
            return;
        }
        if (App.getAllProducts().isEmpty()) {
            Console.printError("No product to add. Create one first.");
            return;
        }

        Cart cart = getCart("Cart number to add product to");
        Console.printSuccess(String.valueOf(cart));

        Product product = getProduct("Name of product to add");
        if (product != null) Console.printSuccess(String.valueOf(product));

        if (product == null) return;
        assert cart != null;
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

        if (App.getAllCarts().isEmpty()) {
            Console.printError("No cart to add to. Create one first.");
            return;
        }
        if (App.getAllProducts().isEmpty()) {
            Console.printError("No product to add. Create one first.");
            return;
        }

        Cart cart = getCart("Cart number remove product from");
        assert cart != null;
        if (cart.getItemsInCartSize() == 0) {
            Console.printError("This cart has no product to remove. Add one first.");
            return;
        }
        Console.printSuccess(String.valueOf(cart));

        Product product = getProduct("Name of product to remove");
        Console.printSuccess(String.valueOf(product));

        if (product == null) return;
        if (cart.removeItem(product.getName())) {
            Console.printSuccess("Product " + product.getName() + " successfully removed from cart number " + cart.getId());
            Console.printSuccess(cart.toString());
        } else {
            Console.printError("Product " + product.getName() + " NOT removed from cart number " + cart.getId());
        }
    }

    /**
     * It displays the amount breakdown of a cart
     */
    private static void getCartAmount() {
        Console.printTitle("Display existing cart's amount");

        if (App.getAllCarts().isEmpty()) {
            Console.printError("No cart to display. Create one first.");
            return;
        }

        Cart cart = getCart();

        if (cart == null) return;

        Console.printSuccess("Cart " + cart.getId() + " amount breakdown: " + cart.getAmountBreakdown());
    }

    /**
     * Display all cart, from light → heavy
     */
    private static void getAllCartsSorted() {
        Console.printTitle("Display all cart, from light → heavy");

        if (App.getAllCarts().isEmpty()) {
            Console.printError("No cart to show. Create one first");
            return;
        }

        App.getAllCartsSorted().forEach(cart -> System.out.println("\n" + cart));
    }

    /**
     * It displays the information of a cart
     */
    private static void getCartInformation() {
        Console.printTitle("Display cart information");

        if (App.getAllCarts().isEmpty()) {
            Console.printError("No cart to show. Create one first");
            return;
        }

        Cart cart = getCart();
        if (cart != null) Console.printSuccess(String.valueOf(cart));
    }

    /**
     * The above code is adding default data to the program.
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
            App.getAllCartsSorted().forEach(cart -> System.out.println("\n" + cart));

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
}

/**
 * Functions used to display options and get user's input
 */
@SuppressWarnings("SameParameterValue")
class Input {

    /**
     * "Get a string from the user, and validate it using the given validator function."
     * The function is protected, so it can only be called from within the same class or from a
     * subclass
     * 
     * @param prompt The prompt to display to the user.
     * @param guide The guide is the text that will be displayed in the console to help the user
     * understand what the input should be.
     * @param validator A function that takes a string and returns a string. If the string is valid, it
     * returns the string. If it's not valid, it returns an error message.
     * @return A string
     */
    protected static String getString(String prompt, String guide, Function<String, String> validator) {
        boolean valid = false;
        String prettifiedGuide = Console.guide(" (" + "Text, " + guide + ")");
        return getStringCore(prompt, validator, valid, prettifiedGuide);
    }

    /**
     * It takes a prompt, a validation function, a boolean, and a prettified guide, and returns a
     * string
     * 
     * @param prompt The prompt to display to the user.
     * @param validator a function that takes in a string and returns a string. This function is used
     * to validate the input.
     * @param valid boolean - whether the input is valid or not
     * @param prettifiedGuide This is the guide that will be displayed to the user.
     * @return The input from the user.
     */
    private static String getStringCore(String prompt, Function<String, String> validator, boolean valid, String prettifiedGuide) {
        String input;

        do {
            System.out.print("\n" + prompt + prettifiedGuide + ": ");
            input = Menu.scanner.nextLine();
            try {
                input = validator.apply(input);
                valid = true;
            } catch (IllegalArgumentException e) {
                Console.printError(e.getMessage());
            }
        } while (!valid);
        return input;
    }

    /**
     * "Get an integer from the user, and validate it using the given validator function."
     * The validator function is a function that takes an integer and returns an integer. It's used to
     * validate the user's input
     * 
     * @param prompt The prompt to display to the user.
     * @param guide This is the guide that will be displayed to the user.
     * @param validator A function that takes an integer and returns an integer. This is used to
     * validate the user's input.
     * @return The return value of the getIntCore method.
     */
    protected static int getInt(String prompt, String guide, Function<Integer, Integer> validator) {
        boolean valid = false;
        String prettifiedGuide = Console.guide(" (" + "Whole number, " + guide + ")");
        return getIntCore(prompt, validator, valid, prettifiedGuide);
    }

    /**
     * "Get an integer from the user, validate it, and return it."
     * The function takes in a prompt, a validator, a boolean, and a prettified guide. The prompt is
     * the question that the user will see. The validator is a function that takes in an integer and
     * returns an integer. The boolean is used to determine whether the user has entered a valid
     * input. The prettified guide is a string that is used to guide the user on what to enter
     * 
     * @param prompt The prompt to display to the user.
     * @param validator a function that takes an integer and returns an integer. This is used to
     * validate the input.
     * @param valid boolean
     * @param prettifiedGuide The guide that will be displayed to the user.
     * @return The method returns an integer.
     */
    private static int getIntCore(String prompt, Function<Integer, Integer> validator, boolean valid, String prettifiedGuide) {
        String input;
        int parsedInput = 0;

        do {
            System.out.print("\n" + prompt + prettifiedGuide + ": ");
            input = Menu.scanner.nextLine();
            try {
                parsedInput = parseInt(input);
                parsedInput = validator.apply(parsedInput);
                valid = true;
            } catch (NumberFormatException e) {
                Console.printError(prompt + "(entered: \"" + input + "\") must be a number");
            } catch (IllegalArgumentException e) {
                Console.printError(e.getMessage());
            }
        } while (!valid);

        return parsedInput;
    }

    /**
     * It takes a prompt, a function that validates the input, a boolean that determines whether the
     * input is valid, and a string that is used to prettify the prompt
     * 
     * @param prompt The prompt to display to the user.
     * @param validator a function that takes a double and returns a double. This is used to validate
     * the input.
     * @param valid boolean - whether the input is valid
     * @param prettifiedGuide This is the text that will be displayed after the prompt.
     * @return A double
     */
    private static double getDoubleCore(String prompt, Function<Double, Double> validator, boolean valid, String prettifiedGuide) {
        String input;
        double parsedInput = 0;

        do {
            System.out.print("\n" + prompt + prettifiedGuide + ": ");
            input = Menu.scanner.nextLine();
            try {
                parsedInput = parseDouble(input);
                parsedInput = validator.apply(parsedInput);
                valid = true;
            } catch (NumberFormatException e) {
                Console.printError(prompt + " (entered: \"" + input + "\") must be a number (decimal point optional)");
            } catch (IllegalArgumentException e) {
                Console.printError(e.getMessage());
            }
        } while (!valid);

        return parsedInput;
    }

    /**
     * "Get a double from the user, and validate it using the given validator function."
     * The function is protected, so it can only be called from within the class or from a subclass
     *
     * @param prompt The prompt to display to the user.
     * @param guide This is the guide that will be displayed to the user.
     * @param validator A function that takes a double and returns a double. This is used to validate
     * the input.
     * @return A double value.
     */
    protected static double getDouble(String prompt, String guide, Function<Double, Double> validator) {
        boolean valid = false;
        String prettifiedGuide = Console.guide(" (" + "Real number, " + guide + ")");
        return getDoubleCore(prompt, validator, valid, prettifiedGuide);
    }

    /**
     * `getBoolean` is a function that returns a boolean value, and it takes a string as a parameter
     * 
     * @param prompt The prompt to be displayed to the user.
     * @return A boolean
     */
    protected static boolean getBoolean(String prompt) {
        String prettifiedGuide = Console.guide(" (" + "y/n" + ")");
        String input;

        do {
            System.out.print(prompt + prettifiedGuide + ": ");
            input = Menu.scanner.nextLine();
            switch (input) {
                case "Y", "y", "yes", "Yes", "True", "true", "1" -> {
                    return true;
                }
                case "N", "n", "no", "No", "False", "false", "0" -> {
                    return false;
                }
                default -> Console.printError(prompt + " (entered: \"" + input + "\") must be a boolean\n(\"Yes\" • \"No\" | \"True\" • \"False\" | \"1\" • \"0\"");
            }
        } while (true);
    }

    /**
     * It takes a prompt, prettifies it, prints it, and returns the user's input
     * 
     * @param prompt The prompt to be displayed to the user.
     * @return A string
     */
    protected static String getString(String prompt) {
        String prettifiedGuide = Console.guide(" (" + "Text" + ")");
        System.out.print("\n" + prompt + prettifiedGuide + ": ");
        String input = Menu.scanner.nextLine();
        if (input.isBlank())
            System.out.print(Console.ANSI_GREEN + "<Nothing entered, using default value>" + Console.ANSI_RESET + "\n");
        return input;
    }

    /**
     * This function waits for the user to press the Enter key before continuing.
     */
    static void enterToContinue() {
        System.out.println("\nPress \"Enter\" to continue...");
        Menu.scanner.nextLine();
    }

    /**
     * It takes a title and an array of options, and returns the user's choice
     * 
     * @param title The title of the menu
     * @param options The options that the user can choose from.
     * @return The choice of the user
     */
    static int getChoice(String title, String[] options) {
        int choice = -1;
        boolean valid = false;

        do {
            System.out.println("\n" + title);
            for (int i = 0; i < options.length; i++) {
                System.out.println(i + 1 + "  | " + options[i]);
            }
            System.out.print("\nYour choice: ");
            try {
                choice = parseInt(Menu.scanner.nextLine());
                if (choice >= 1 && choice <= options.length) {
                    valid = true;
                } else {
                    Console.printError("Please pick only from available options");
                }
            } catch (NumberFormatException e) {
                Console.printError("Please enter a number only");
            }
        } while (!valid);

        return choice;
    }

    /**
     * It takes in a title and an array of options, and returns the user's choice
     * 
     * @param title The title of the menu
     * @param options The options that the user can choose from.
     * @return The choice that the user has made.
     */
    static int getChoiceWithQuit(String title, String[] options) {
        int choice = -1;
        boolean valid = false;

        do {
            System.out.println("\n" + title);
            for (int i = 0; i < options.length; i++) {
                System.out.println(i + 1 + "  | " + options[i]);
            }
            System.out.println("0  | " + "Quit");
            System.out.print("\nYour choice: ");
            try {
                choice = parseInt(Menu.scanner.nextLine());
                if (choice >= 0 && choice <= options.length) {
                    valid = true;
                } else {
                    Console.printError("Please pick only from available options");
                }
            } catch (NumberFormatException e) {
                Console.printError("Please enter a number only");
            }
        } while (!valid);

        return choice;
    }

    public static String validateStringNotBlank(String string) throws IllegalArgumentException {
        if (string.isBlank())
            throw new IllegalArgumentException("Input (entered: \"" + string + "\") must not be blank");
        return string;
    }
}

/**
 * Functions used to make the console easier to understand and more visually appealing.
 */
class Console {
    protected static final String ANSI_RESET = "\u001B[0m";
    protected static final String ANSI_GREEN = "\u001B[32m";
    protected static final String ANSI_YELLOW = "\u001B[33m";
    protected static final String ANSI_BLUE = "\u001B[34m";
    protected static final String ANSI_PURPLE = "\u001B[35m";
    protected static final String ANSI_CYAN = "\u001B[36m";
    static final String intro = ANSI_CYAN + "\n─────────────────────────────────────────────" + "\nQuan Hoang DO - S3800978" + "\nFurther Programming - Individual Project" + "\n─────────────────────────────────────────────" + ANSI_RESET;

    /**
     * `printError` is a function that takes a string as an argument and prints it to the console in
     * yellow
     * 
     * @param text The text to be printed
     */
    protected static void printError(String text) {
        System.out.println("\n" + ANSI_YELLOW + text + ANSI_RESET + "\n");
    }

    /**
   * Prints the text in green color.
   * 
   * @param text The text to be printed
   */
    protected static void printSuccess(String text) {
        System.out.println("\n" + ANSI_GREEN + text + ANSI_RESET);
    }

   /**
    * It prints the title of the section in purple.
    * 
    * @param title The title of the section.
    */
    static void printTitle(String title) {
        System.out.println(ANSI_PURPLE + "\n────────\n" + title + ANSI_RESET + "\n");
    }

/**
 * It takes a string as an argument and returns a string with the argument string in cyan
 * 
 * @param guideText The text that you want to be displayed in the console.
 * @return The method is returning a string that is the guideText parameter with the ANSI_CYAN and
 * ANSI_RESET strings added to the beginning and end of the string.
 */
    static String guide(String guideText) {
        return (ANSI_CYAN + guideText + ANSI_RESET);
    }
}