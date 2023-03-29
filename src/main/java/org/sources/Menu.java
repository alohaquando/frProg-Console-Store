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


    protected static void mainMenu() {
        System.out.println(PrettifyConsole.intro);
        while (running) {
            options();
            Input.enterToContinue();
        }
    }

    private static void options() {
        System.out.println(PrettifyConsole.ANSI_BLUE +
                "\n─────────────────────────────────────────────" + PrettifyConsole.ANSI_RESET);
        System.out.println(PrettifyConsole.ANSI_BLUE + "\nPick an option by entering its number." +
                "\n[EXTRA] options are included for ease of use." + PrettifyConsole.ANSI_RESET);
        System.out.println(PrettifyConsole.ANSI_BLUE + "\n\uD83D\uDCE6 • PRODUCT" + PrettifyConsole.ANSI_RESET);
        System.out.println("1  | Create new product");
        System.out.println("2  | Edit existing product");
        System.out.println("3  | [EXTRA] Search for product");
        System.out.println("4  | [EXTRA] List all products");
        System.out.println(PrettifyConsole.ANSI_BLUE + "\n\uD83D\uDED2 • CART" + PrettifyConsole.ANSI_RESET);
        System.out.println("5  | Create new cart");
        System.out.println("6  | Add item to an existing cart");
        System.out.println("7  | Remove item from an existing cart");
        System.out.println("8  | Display amount of an existing cart");
        System.out.println("9  | Display all cart, from light → heavy");
        System.out.println("10 | [EXTRA] Display cart information");
        System.out.println(PrettifyConsole.ANSI_BLUE + "\n\uD83D\uDD37 • SYSTEM" + PrettifyConsole.ANSI_RESET);
        System.out.println("0  | Quit");

        System.out.print("\nYour choice: ");

        int choice = -1;

        try {
            choice = parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            PrettifyConsole.printError("Please enter a number");
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
            default ->
                    PrettifyConsole.printError("Entered choice doesn't match any available options. Please try again.");
        }
    }

    private static void createProduct() {
        PrettifyConsole.title("Create new product");
        switch (Input.getChoice("Select a type", new String[]{"Physical product", "Digital product"})) {
            case 1 -> createPhysicalProduct();
            case 2 -> createDigitalProduct();
        }
    }

    private static void createPhysicalProduct() {
        PrettifyConsole.title("Create physical product");
        String name = Input.getString("Name", "not blank, unique", Product::validateName);
        String description = Input.getString("Description");
        int availableQuantity = Input.getInt("Available quantity", "larger than 0", Product::validateAvailableQuantity);
        double price = Input.getDouble("Price", "larger than 0", Product::validatePrice);
        double weight = Input.getDouble("Weight", "larger than 0", PhysicalProduct::validateWeight);
        boolean giftable = Input.getBoolean("Giftable");
        String message = null;
        if (giftable) message = Input.getString("Message");

        PhysicalProduct physicalProduct = new PhysicalProduct(name, description, availableQuantity, price, weight, giftable, message);
        PrettifyConsole.printSuccess("Physical production creation successful.");
        PrettifyConsole.printSuccess(String.valueOf(physicalProduct));
    }

    private static void createDigitalProduct() {
        PrettifyConsole.title("Create digital product");
        String name = Input.getString("Name", "not blank, unique", Product::validateName);
        String description = Input.getString("Description");
        int availableQuantity = Input.getInt("Available quantity", "larger than 0", Product::validateAvailableQuantity);
        double price = Input.getDouble("Price", "larger than 0", Product::validatePrice);
        boolean giftable = Input.getBoolean("Giftable");
        String message = null;
        if (giftable) message = Input.getString("Message");

        DigitalProduct digitalProduct = new DigitalProduct(name, description, availableQuantity, price, giftable, message);
        PrettifyConsole.printSuccess("Digital production creation successful.");
        PrettifyConsole.printSuccess(String.valueOf(digitalProduct));
    }

    private static Product getProduct() {
        String query = Input.getString("Product name", "not empty", Input::validateStringNotBlank);
        if (App.getAllProducts().containsKey(query)) {
            System.out.println("\nProduct found.");
            return App.getAllProducts().get(query);
        } else {
            System.out.println("\nProduct not found.");
            return null;
        }
    }

    private static void editProduct() {
        PrettifyConsole.title("Edit existing product");

        if (App.getAllProducts().isEmpty()) {
            System.out.println("No product to edit. Create one first.");
            return;
        }

        Product product = getProduct();

        if (product == null) return;

        // Build the available fields to edit
        ArrayList<String> productFields = new ArrayList<>(Arrays.asList("Name", "Description", "Available quantity", "Price"));
        switch (product.getClass().getSimpleName()) {
            case "PhysicalProduct" -> {
                productFields.add("Weight");
                productFields.add("Giftable");
            }
            case "DigitalProduct" -> productFields.add("Giftable");
        }
        if (((Giftable) product).getGiftable()) productFields.add("Message");


        boolean editing = true;
        int choice;
        do {
            System.out.println(product);
            choice = Input.getChoiceWithQuit("Select field to edit", productFields.toArray(new String[0]));

            switch (choice) {
                case 1 -> product.setName(Input.getString("Name", "not blank, unique", Product::validateName));
                case 2 -> product.setDescription(Input.getString("Description"));
                case 3 ->
                        product.setAvailableQuantity(Input.getInt("Available quantity", "larger than 0", Product::validateAvailableQuantity));
                case 4 -> product.setPrice(Input.getDouble("Price", "larger than 0", Product::validatePrice));
                default -> editing = false;
            }
        } while (editing);
    }


    private static void getProductInformation() {
        PrettifyConsole.title("Search for product");

        if (App.getAllProducts().isEmpty()) {
            System.out.println("No product to show. Create one first.");
            return;
        }

        Product product = getProduct();
        if (product != null) System.out.println(product);
    }


    private static void getAllProducts() {
        PrettifyConsole.title("List all products");

        if (App.getAllProducts().isEmpty()) {
            System.out.println("No product to show. Create one first.");
            return;
        }

        App.getAllProducts().values().forEach(product ->
                System.out.println("\n" + product));

    }

    // Carts

    private static void createCart() {
        PrettifyConsole.title("Create new cart");

        Cart cart = new Cart();
        PrettifyConsole.printSuccess("Cart creation successful.");
        PrettifyConsole.printSuccess(String.valueOf(cart));
    }

    private static Cart getCart() {
        int query = Input.getInt("Cart number", "not empty", Menu::validateCartExist);
        query -= 1;
        if (App.getAllCarts().get(query) != null) {
            System.out.println("\nCart found.");
            return App.getAllCarts().get(query);
        } else {
            System.out.println("\nCart not found.");
            return null;
        }
    }

    private static int validateCartExist(int cartNumber) throws IllegalArgumentException {
        if (cartNumber < 1 || cartNumber > App.getAllCarts().size())
            throw new IllegalArgumentException("Cart number (entered: \"" + cartNumber + "\") must match a created cart");
        return cartNumber;
    }

    private static void addToCart() {
        PrettifyConsole.title("Add product to existing cart");

        if (App.getAllCarts().isEmpty()) {
            System.out.println("No cart to add to. Create one first.");
            return;
        }
    }

    private static void removeFromCart() {
        PrettifyConsole.title("Remove product from existing cart");

        if (App.getAllCarts().isEmpty()) {
            System.out.println("No cart to remove from. Create one first.");
            return;
        }
    }

    private static void getCartAmount() {
        PrettifyConsole.title("Display existing cart's amount");

        if (App.getAllCarts().isEmpty()) {
            System.out.println("No cart to display. Create one first.");
            return;
        }

        Cart cart = getCart();

        if (cart == null) return;

        System.out.println(cart.getName() + " amount breakdown: " + cart.getAmountBreakdown());
    }

    private static void getAllCartsSorted() {
        PrettifyConsole.title("Display all cart, from light → heavy");

        if (App.getAllCarts().isEmpty()) {
            System.out.println("No cart to show. Create one first");
            return;
        }

        App.getAllCarts(true).forEach(cart ->
                System.out.println("\n" + cart));
    }

    private static void getCartInformation() {
        PrettifyConsole.title("Display cart information");

        if (App.getAllCarts().isEmpty()) {
            System.out.println("No cart to show. Create one first");
            return;
        }

        Cart cart = getCart();
        if (cart != null) System.out.println(cart);
    }

    private static void endProgram() {
        PrettifyConsole.title("Quit program");
        System.out.println("Quitting program...");
        System.out.println("Thank you for using.");
        running = false;
    }
}

class Input {

    protected static String getString(String prompt, Function<String, String> validator) {
        boolean valid = false;
        String prettifiedGuide = PrettifyConsole.guide(" (" + "Text" + ")");
        return getStringCore(prompt, validator, valid, prettifiedGuide);
    }

    protected static String getString(String prompt, String guide, Function<String, String> validator) {
        boolean valid = false;
        String prettifiedGuide = PrettifyConsole.guide(" (" + "Text, " + guide + ")");
        return getStringCore(prompt, validator, valid, prettifiedGuide);
    }

    private static String getStringCore(String prompt, Function<String, String> validator, boolean valid, String prettifiedGuide) {
        String input;

        do {
            System.out.print(prompt + prettifiedGuide + ": ");
            input = Menu.scanner.nextLine();
            try {
                input = validator.apply(input);
                valid = true;
            } catch (IllegalArgumentException e) {
                PrettifyConsole.printError(e.getMessage());
            }
        } while (!valid);
        return input;
    }

    protected static int getInt(String prompt, Function<Integer, Integer> validator) {
        boolean valid = false;
        String prettifiedGuide = PrettifyConsole.guide(" (" + "Whole number" + ")");
        return getIntCore(prompt, validator, valid, prettifiedGuide);
    }

    protected static int getInt(String prompt, String guide, Function<Integer, Integer> validator) {
        boolean valid = false;
        String prettifiedGuide = PrettifyConsole.guide(" (" + "Whole number, " + guide + ")");
        return getIntCore(prompt, validator, valid, prettifiedGuide);
    }

    private static int getIntCore(String prompt, Function<Integer, Integer> validator, boolean valid, String prettifiedGuide) {
        String input;
        int parsedInput = 0;

        do {
            System.out.print(prompt + prettifiedGuide + ": ");
            input = Menu.scanner.nextLine();
            try {
                parsedInput = parseInt(input);
                parsedInput = validator.apply(parsedInput);
                valid = true;
            } catch (NumberFormatException e) {
                PrettifyConsole.printError(prompt + "(entered: \"" + input + "\") must be a number");
            } catch (IllegalArgumentException e) {
                PrettifyConsole.printError(e.getMessage());
            }
        } while (!valid);

        return parsedInput;
    }

    protected static double getDouble(String prompt, Function<Double, Double> validator) {
        boolean valid = false;
        String prettifiedGuide = PrettifyConsole.guide(" (" + "Real number" + ")");
        return getDoubleCore(prompt, validator, valid, prettifiedGuide);
    }

    private static double getDoubleCore(String prompt, Function<Double, Double> validator, boolean valid, String prettifiedGuide) {
        String input;
        double parsedInput = 0;

        do {
            System.out.print(prompt + prettifiedGuide + ": ");
            input = Menu.scanner.nextLine();
            try {
                parsedInput = parseDouble(input);
                parsedInput = validator.apply(parsedInput);
                valid = true;
            } catch (NumberFormatException e) {
                PrettifyConsole.printError(prompt + " (entered: \"" + input + "\") must be a number (decimal point optional)");
            } catch (IllegalArgumentException e) {
                PrettifyConsole.printError(e.getMessage());
            }
        } while (!valid);

        return parsedInput;
    }

    protected static double getDouble(String prompt, String guide, Function<Double, Double> validator) {
        boolean valid = false;
        String prettifiedGuide = PrettifyConsole.guide(" (" + "Real number, " + guide + ")");
        return getDoubleCore(prompt, validator, valid, prettifiedGuide);
    }

    protected static boolean getBoolean(String prompt) {
        String prettifiedGuide = PrettifyConsole.guide(" (" + "y/n" + ")");
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
                    PrettifyConsole.printError(prompt + " (entered: \"" + input + "\") must be a boolean\n(\"Yes\" • \"No\" | \"True\" • \"False\" | \"1\" • \"0\"");
                }
            }
        } while (true);
    }

    protected static String getString(String prompt) {
        String prettifiedGuide = PrettifyConsole.guide(" (" + "Text" + ")");
        System.out.print(prompt + prettifiedGuide + ": ");
        String input = Menu.scanner.nextLine();
        if (input.isBlank())
            System.out.print(PrettifyConsole.ANSI_GREEN + "<Nothing entered, using default value>" + PrettifyConsole.ANSI_RESET + "\n");
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
                    PrettifyConsole.printError("Please pick only from available options");
                }
                ;
            } catch (NumberFormatException e) {
                PrettifyConsole.printError("Please enter a number only");
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
                    PrettifyConsole.printError("Please pick only from available options");
                }
                ;
            } catch (NumberFormatException e) {
                PrettifyConsole.printError("Please enter a number only");
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

class PrettifyConsole {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    static final String intro = ANSI_CYAN +
            "\n─────────────────────────────────────────────" +
            "\nQuan Hoang DO - S3800978" +
            "\nFurther Programming - Individual Project" +
            "\n─────────────────────────────────────────────" + ANSI_RESET;

    protected static void printError(String text) {
        System.out.println("\n" + ANSI_YELLOW + text + ANSI_RESET);
    }

    protected static void printError() {
        System.out.println("\n" + ANSI_YELLOW + "Entered value invalid" + ANSI_RESET);
    }

    protected static void printSuccess(String text) {
        System.out.println("\n" + ANSI_GREEN + text + ANSI_RESET);
    }

    static void title(String title) {
        System.out.println(ANSI_PURPLE + "\n────────\n" + title + ANSI_RESET + "\n");
    }

    static String guide(String guideText) {
        return (ANSI_CYAN + guideText + ANSI_RESET);
    }
}