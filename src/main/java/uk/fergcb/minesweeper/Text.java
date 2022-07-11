package uk.fergcb.minesweeper;

public class Text {
    private static final String ANSI_BLACK = "\u001b[30m";
    private static final String ANSI_RED = "\u001b[31m";
    private static final String ANSI_GREEN = "\u001b[32m";
    private static final String ANSI_YELLOW = "\u001b[33m";
    private static final String ANSI_BLUE = "\u001b[34m";
    private static final String ANSI_MAGENTA = "\u001b[35m";
    private static final String ANSI_CYAN = "\u001b[36m";
    private static final String ANSI_WHITE = "\u001b[37m";
    private static final String ANSI_RESET = "\u001b[0m";

    public static String black(String text) {
        return ANSI_BLACK + text + ANSI_RESET;
    }

    public static String red(String text) {
        return ANSI_RED + text + ANSI_RESET;
    }

    public static String green(String text) {
        return ANSI_GREEN + text + ANSI_RESET;
    }

    public static String yellow(String text) {
        return ANSI_YELLOW + text + ANSI_RESET;
    }

    public static String blue(String text) {
        return ANSI_BLUE + text + ANSI_RESET;
    }

    public static String magenta(String text) {
        return ANSI_MAGENTA + text + ANSI_RESET;
    }

    public static String cyan(String text) {
        return ANSI_CYAN + text + ANSI_RESET;
    }

    public static String white(String text) {
        return ANSI_WHITE + text + ANSI_RESET;
    }

}
