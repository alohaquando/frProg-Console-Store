package S3800978.sources;

/**
 * Functions used to make the console easier to understand and more visually appealing.
 */
abstract class Console {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String intro = ANSI_CYAN + "\n─────────────────────────────────────────────" + "\nQuan Hoang DO - S3800978" + "\nFurther Programming - Individual Project" + "\n─────────────────────────────────────────────" + ANSI_RESET;

    /**
     * Takes a string as an argument and prints it to the console in yellow. Used for warnings and errors.
     *
     * @param text The text to be printed
     */
    static void printError(String text) {
        System.out.println("\n" + ANSI_YELLOW + text + ANSI_RESET);
    }

    /**
     * Prints the text in green color. Used to indicate a successful result.
     *
     * @param text The text to be printed
     */
    static void printSuccess(String text) {
        System.out.println("\n" + ANSI_GREEN + text + ANSI_RESET);
    }

    /**
     * Print the given text in cyan. Used for headings.
     *
     * @param text The title of the section.
     */
    static void printHeading(String text) {
        System.out.println("\n" + ANSI_BLUE + text + ANSI_RESET);
    }

    /**
     * Print the given text in purple. Used for titles.
     *
     * @param text The title of the section.
     */
    static void printTitle(String text) {
        System.out.println(ANSI_PURPLE + "\n────────\n" + text + ANSI_RESET + "\n");
    }

    static void printIntro() {
        System.out.println(intro);
    }

    /**
     * Return the given string, formatted in blue. Used for guide text.
     *
     * @param guideText The text to format.
     * @return The guideText, formatted in blue.
     */
    static String guide(String guideText) {
        return (ANSI_CYAN + guideText + ANSI_RESET);
    }
}
