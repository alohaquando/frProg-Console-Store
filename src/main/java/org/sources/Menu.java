package org.sources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * @author Quan Hoang DO - S3800978
 */

public class Menu {
    static Scanner scanner = new Scanner(System.in);
    static boolean running = true;
    static boolean premadeDataAdded= false;


    protected static void mainMenu() {
        System.out.println(Console.intro);
        while (running) {
            options();
            Input.enterToContinue();
        }
    }

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
            case 99 -> addPremadeData();
            default -> Console.printError("Entered choice doesn't match any available options. Please try again.");
        }
    }

    // Product

    private static void createProduct() {
        Console.printTitle("Create new product");
        switch (Input.getChoice("Select a type", new String[]{"Physical product", "Digital product"})) {
            case 1 -> createPhysicalProduct();
            case 2 -> createDigitalProduct();
        }
    }

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


    private static void getProductInformation() {
        Console.printTitle("Search for product");

        if (App.getAllProducts().isEmpty()) {
            Console.printError("No product to show. Create one first.");
            return;
        }

        Product product = getProduct();
        if (product != null) System.out.println(product);
    }


    private static void getAllProducts() {
        Console.printTitle("List all products");

        if (App.getAllProducts().isEmpty()) {
            Console.printError("No product to show. Create one first.");
            return;
        }

        App.getAllProducts().values().forEach(product -> System.out.println("\n" + product));

    }

    // Carts

    private static void createCart() {
        Console.printTitle("Create new cart");

        Cart cart = new Cart();
        Console.printSuccess("Cart creation successful.");
        Console.printSuccess(String.valueOf(cart));
    }

    private static Cart getCart() {
        int query = Input.getInt("Cart number", "not empty", Menu::validateCartExist);
        query -= 1;

        return App.getCart(query);
    }

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

    private static int validateCartExist(int cartNumber) throws IllegalArgumentException {
        if (cartNumber < 1 || cartNumber > App.getAllCarts().size())
            throw new IllegalArgumentException("Cart number (entered: \"" + cartNumber + "\") must match a created cart");
        return cartNumber;
    }

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

    private static void getAllCartsSorted() {
        Console.printTitle("Display all cart, from light → heavy");

        if (App.getAllCarts().isEmpty()) {
            Console.printError("No cart to show. Create one first");
            return;
        }

        App.getAllCartsSorted().forEach(cart -> System.out.println("\n" + cart));
    }

    private static void getCartInformation() {
        Console.printTitle("Display cart information");

        if (App.getAllCarts().isEmpty()) {
            Console.printError("No cart to show. Create one first");
            return;
        }

        Cart cart = getCart();
        if (cart != null) Console.printSuccess(String.valueOf(cart));
    }

    private static void addPremadeData() {

    if (!premadeDataAdded) {
        Product p1 = new PhysicalProduct("Camera", "HD Point-and-Shoot Sany camera to save your moments forever", 5, 249.99, 5.75, true, "For your loved ones!");
        Product p2 = new PhysicalProduct("Laptop", "New OrangeBook Pro 14' with M1000 chipset", 0, 700, 2, false);
        Product p3 = new PhysicalProduct("TV", "Next-gen Sungsam TV with Googly TV", 1, 800, 100, false);
        Product p4 = new DigitalProduct("Gift card", "Spend at your local CircleMart", 4, 9.99, false);
        Product p5 = new DigitalProduct("Music subscription", "Listen all day with this subscription to SpotMP3", 0, 9.99, true, "For the music lovers in your life");
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

        premadeDataAdded = true;
        Console.printSuccess("Pre-made data added successfully.");
    } else Console.printError("Pre-made data already added.");
    }

    private static void endProgram() {
        Console.printTitle("Quit program");
        System.out.println("Quitting program...");
        System.out.println("Thank you for using.");
        running = false;
    }
}

class Input {

    protected static String getString(String prompt, Function<String, String> validator) {
        boolean valid = false;
        String prettifiedGuide = Console.guide(" (" + "Text" + ")");
        return getStringCore(prompt, validator, valid, prettifiedGuide);
    }

    protected static String getString(String prompt, String guide, Function<String, String> validator) {
        boolean valid = false;
        String prettifiedGuide = Console.guide(" (" + "Text, " + guide + ")");
        return getStringCore(prompt, validator, valid, prettifiedGuide);
    }

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

    protected static int getInt(String prompt, Function<Integer, Integer> validator) {
        boolean valid = false;
        String prettifiedGuide = Console.guide(" (" + "Whole number" + ")");
        return getIntCore(prompt, validator, valid, prettifiedGuide);
    }

    protected static int getInt(String prompt, String guide, Function<Integer, Integer> validator) {
        boolean valid = false;
        String prettifiedGuide = Console.guide(" (" + "Whole number, " + guide + ")");
        return getIntCore(prompt, validator, valid, prettifiedGuide);
    }

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

    protected static double getDouble(String prompt, Function<Double, Double> validator) {
        boolean valid = false;
        String prettifiedGuide = Console.guide(" (" + "Real number" + ")");
        return getDoubleCore(prompt, validator, valid, prettifiedGuide);
    }

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

    protected static double getDouble(String prompt, String guide, Function<Double, Double> validator) {
        boolean valid = false;
        String prettifiedGuide = Console.guide(" (" + "Real number, " + guide + ")");
        return getDoubleCore(prompt, validator, valid, prettifiedGuide);
    }

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
                default -> {
                    Console.printError(prompt + " (entered: \"" + input + "\") must be a boolean\n(\"Yes\" • \"No\" | \"True\" • \"False\" | \"1\" • \"0\"");
                }
            }
        } while (true);
    }

    protected static String getString(String prompt) {
        String prettifiedGuide = Console.guide(" (" + "Text" + ")");
        System.out.print("\n" + prompt + prettifiedGuide + ": ");
        String input = Menu.scanner.nextLine();
        if (input.isBlank())
            System.out.print(Console.ANSI_GREEN + "<Nothing entered, using default value>" + Console.ANSI_RESET + "\n");
        return input;
    }

    static void enterToContinue() {
        System.out.println("\nPress \"Enter\" to continue...");
        Menu.scanner.nextLine();
    }

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
                ;
            } catch (NumberFormatException e) {
                Console.printError("Please enter a number only");
            }
        } while (!valid);

        return choice;
    }

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
                ;
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

class Console {
    protected static final String ANSI_RESET = "\u001B[0m";
    protected static final String ANSI_GREEN = "\u001B[32m";
    protected static final String ANSI_YELLOW = "\u001B[33m";
    protected static final String ANSI_BLUE = "\u001B[34m";
    protected static final String ANSI_PURPLE = "\u001B[35m";
    protected static final String ANSI_CYAN = "\u001B[36m";
    static final String intro = ANSI_CYAN + "\n─────────────────────────────────────────────" + "\nQuan Hoang DO - S3800978" + "\nFurther Programming - Individual Project" + "\n─────────────────────────────────────────────" + ANSI_RESET;

    protected static void printError(String text) {
        System.out.println("\n" + ANSI_YELLOW + text + ANSI_RESET + "\n");
    }

    protected static void printError() {
        System.out.println("\n" + ANSI_YELLOW + "Entered value invalid" + ANSI_RESET);
    }

    protected static void printSuccess(String text) {
        System.out.println("\n" + ANSI_GREEN + text + ANSI_RESET);
    }

    static void printTitle(String title) {
        System.out.println(ANSI_PURPLE + "\n────────\n" + title + ANSI_RESET + "\n");
    }

    static String guide(String guideText) {
        return (ANSI_CYAN + guideText + ANSI_RESET);
    }
}