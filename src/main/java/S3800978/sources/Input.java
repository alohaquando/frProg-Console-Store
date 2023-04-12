package S3800978.sources;

import java.util.Scanner;
import java.util.function.Function;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * Functions used to display options and get user's input
 *
 * @author Quan Hoang DO - S3800978
 */
@SuppressWarnings("SameParameterValue")
abstract class Input {
    /**
     * Shared scanner for all inputs
     */
    private static final Scanner scanner = new Scanner(System.in);

    //region Get Input functions
    //region getChoice functions

    /**
     * Show a prompt to get a user's choice in as a whole number
     *
     * @return The choice of the user
     */
    static int getChoice() {
        System.out.print("\nYour choice: ");
        int choice = -1;
        try {
            choice = parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            Console.printError("Please enter a number");
        }
        return choice;
    }

    /**
     * Display a title and multiple options, and returns the user's choice
     *
     * @param title   The title of the menu
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
                choice = parseInt(scanner.nextLine());
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
     * Display a title and multiple options, and returns the user's choice, allowing the user to quit the loop
     *
     * @param title   The title of the menu
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
            System.out.println("0  | " + "Done");
            System.out.print("\nYour choice: ");
            try {
                choice = parseInt(scanner.nextLine());
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
    //endregion

    //region getString functions

    /**
     * Get a string from the user, and validate it using the given validator function.
     * Keeps asking until the input is valid.
     *
     * @param prompt    The prompt to display to the user.
     * @param guide     The guide is the text that will be displayed in the console to help the user
     *                  understand what the input should be.
     * @param validator A function that takes a string and returns a string. If the string is valid, it
     *                  returns the string. If it's not valid, it returns an error message.
     * @return A string
     */
    static String getString(String prompt, String guide, Function<String, String> validator) {
        boolean valid = false;
        String prettifiedGuide = Console.guide(" (" + "Text, " + guide + ")");

        String input;

        do {
            System.out.print("\n" + prompt + prettifiedGuide + ": ");
            input = scanner.nextLine();
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
     * Get user input without validation.
     *
     * @param prompt The prompt to be displayed to the user.
     * @return A string
     */
    static String getString(String prompt) {
        String prettifiedGuide = Console.guide(" (" + "Text" + ")");
        System.out.print("\n" + prompt + prettifiedGuide + ": ");
        String input = scanner.nextLine();
        if (input.isBlank())
            System.out.print(Console.guide("<Nothing entered, using default value>"));
        return input;
    }
    //endregion

    /**
     * Get an integer from the user, and validate it using the given validator function.
     * Keeps asking until the input is valid.
     *
     * @param prompt    The prompt to display to the user.
     * @param guide     This is the guide that will be displayed to the user.
     * @param validator A function that takes an integer and returns an integer. This is used to
     *                  validate the user's input.
     * @return The return value of the getIntCore method.
     */
    static int getInt(String prompt, String guide, Function<Integer, Integer> validator) {
        boolean valid = false;
        String prettifiedGuide = Console.guide(" (" + "Whole number, " + guide + ")");

        String input;
        int parsedInput = 0;

        do {
            System.out.print("\n" + prompt + prettifiedGuide + ": ");
            input = scanner.nextLine();
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
     * Get a double from the user, and validate it using the given validator function.
     * Keeps asking until the input is valid.
     *
     * @param prompt    The prompt to display to the user.
     * @param guide     This is the guide that will be displayed to the user.
     * @param validator A function that takes a double and returns a double. This is used to validate
     *                  the input.
     * @return A double value.
     */
    static double getDouble(String prompt, String guide, Function<Double, Double> validator) {
        boolean valid = false;
        String prettifiedGuide = Console.guide(" (" + "Real number, " + guide + ")");
        String input;
        double parsedInput = 0;

        do {
            System.out.print("\n" + prompt + prettifiedGuide + ": ");
            input = scanner.nextLine();
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
     * Get a boolean from the user, validated from their input.
     * Keeps asking until the input is valid.
     *
     * @param prompt The prompt to be displayed to the user.
     * @return A boolean
     */
    static boolean getBoolean(String prompt) {
        String prettifiedGuide = Console.guide(" (" + "y/n" + ")");
        String input;

        do {
            System.out.print("\n" + prompt + prettifiedGuide + ": ");
            input = scanner.nextLine();
            switch (input) {
                case "Y", "y", "yes", "Yes", "True", "true", "1" -> {
                    return true;
                }
                case "N", "n", "no", "No", "False", "false", "0" -> {
                    return false;
                }
                default ->
                        Console.printError(prompt + " (entered: \"" + input + "\") must be a boolean\n(\"Yes\" • \"No\" | \"True\" • \"False\" | \"1\" • \"0\"");
            }
        } while (true);
    }
    //endregion

    //region Validators

    /**
     * Validate that the given string is not blank
     *
     * @param string - Given string
     * @return the given string
     * @throws IllegalArgumentException if given string is blank.
     */
    static String validateStringNotBlank(String string) throws IllegalArgumentException {
        if (string.isBlank())
            throw new IllegalArgumentException("Input (entered: \"" + string + "\") must not be blank");
        return string;
    }

    /**
     * Validates that the given cart number corresponds to an existing cart in the system.
     *
     * @param cartNumber the cart number to validate
     * @return the validated cart number, if it corresponds to an existing cart
     * @throws IllegalArgumentException if the cart number is less than 1 or greater than the number of created carts
     */
    static int validateCartExist(int cartNumber) throws IllegalArgumentException {
        if (cartNumber < 1 || cartNumber > App.getAllCarts().size())
            throw new IllegalArgumentException("Cart number (entered: \"" + cartNumber + "\") must match a created cart");
        return cartNumber;
    }
    //endregion

    //region Extra functions

    /**
     * Waits for the user to press the Enter key before continuing.
     */
    static void enterToContinue() {
        System.out.println("\nPress \"Enter\" to continue...");
        scanner.nextLine();
    }
    //endregion
}
