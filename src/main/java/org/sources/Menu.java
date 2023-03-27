package org.sources;

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

    public static void start() {
        System.out.println(PrettifyConsole.intro);
        mainMenu();
    }

    private static void mainMenu() {
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
        System.out.println("3  | [EXTRA] Display product information");
        System.out.println("4  | [EXTRA] List all products");
        System.out.println(PrettifyConsole.ANSI_BLUE + "\n\uD83D\uDED2 • CART" + PrettifyConsole.ANSI_RESET);
        System.out.println("5  | Create new cart");
        System.out.println("6  | Add item to existing cart");
        System.out.println("7  | Remove item from existing cart");
        System.out.println("8  | Display existing cart's amount");
        System.out.println("9  | Display all cart - sorted by weight");
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
            case 3 -> getProduct();
            case 4 -> getAllProducts();
            case 5 -> createCart();
            case 6 -> addToCart();
            case 7 -> removeFromCart();
            case 8 -> getCartAmount();
            case 9 -> getAllCartsSorted();
            case 10 -> getCart();
            default -> PrettifyConsole.printError("Entered choice doesn't match any available options. Please try again.");
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
        String name = Input.getString("Name", Product::validateName);
        String description = Input.getString("Description");
        int availableQuantity = Input.getInt("Available quantity", Product::validateAvailableQuantity);
        double price = Input.getDouble("Price", Product::validatePrice);
        double weight = Input.getDouble("Weight", PhysicalProduct::validateWeight);
        boolean giftable = Input.getBoolean("Giftable");
        String message = null;
        if (giftable) message = Input.getString("Message");

        PhysicalProduct physicalProduct = new PhysicalProduct(name, description, availableQuantity, price, weight, giftable, message);
        PrettifyConsole.printSuccess("Physical production creation successful.");
        PrettifyConsole.printSuccess(String.valueOf(physicalProduct));
    }

    private static void createDigitalProduct() {
        PrettifyConsole.title("Create digital product");
        String name = Input.getString("Name", Product::validateName);
        String description = Input.getString("Description");
        int availableQuantity = Input.getInt("Available quantity", Product::validateAvailableQuantity);
        double price = Input.getDouble("Price", Product::validatePrice);
        boolean giftable = Input.getBoolean("Giftable");
        String message = null;
        if (giftable) message = Input.getString("Message");

        DigitalProduct digitalProduct = new DigitalProduct(name, description, availableQuantity, price, giftable, message);
        PrettifyConsole.printSuccess("Physical production creation successful.");
        PrettifyConsole.printSuccess(String.valueOf(digitalProduct));
    }

    private static void editProduct() {
        PrettifyConsole.title("Edit existing product");
    }


    private static void getProduct() {
        PrettifyConsole.title("Display product information");
    }

    private static void getAllProducts() {
        PrettifyConsole.title("List all products");
    }

    private static void createCart() {
        PrettifyConsole.title("Create new cart");
    }

    private static void addToCart() {
        PrettifyConsole.title("Add product to existing cart");
    }

    private static void removeFromCart() {
        PrettifyConsole.title("Remove product from existing cart");
    }

    private static void getCartAmount() {
        PrettifyConsole.title("Display existing cart's amount");
    }

    private static void getAllCartsSorted() {
        PrettifyConsole.title("Display all cart - sorted by weight");
    }

    private static void getCart() {
        PrettifyConsole.title("Display cart information");
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
        String input;

        do {
            System.out.print(prompt + ": ");
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
        String input;
        int parsedInput = 0;

        do {
            System.out.print(prompt + ": ");
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
        String input;
        double parsedInput = 0;

        do {
            System.out.print(prompt + ": ");
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

    protected static boolean getBoolean(String prompt) {
        String input;

        do {
            System.out.print(prompt + ": ");
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
        System.out.print(prompt + ": ");
        String input = Menu.scanner.nextLine();
        if (input.isBlank()) System.out.print(PrettifyConsole.ANSI_GREEN + "<Nothing entered, using default value>" + PrettifyConsole.ANSI_RESET + "\n");
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
        System.out.println(ANSI_PURPLE + "\n────────\n"  + title + ANSI_RESET + "\n");
    }
}