package uk.fergcb.minesweeper;

import java.io.InputStream;
import java.util.Locale;
import java.util.Scanner;

public class UserScanner {
    private final Scanner scanner;

    public UserScanner (InputStream in) {
        this.scanner = new Scanner(in);
    }

    public UserScanner () {
        this(System.in);
    }

    /**
     * Read a line from the scanner and normalise it
     *
     * @return A line of cleaned user input
     */
    public String takeLine() {
        return scanner.nextLine()
                .trim()
                .toUpperCase(Locale.ROOT);
    }

    /**
     * Ask a yes/no question
     *
     * @param prompt   The user-facing question string
     * @param fallback The default response if none is given
     * @return true for input starting with "y", false for "n", otherwise return fallback
     */
    public boolean query(String prompt, boolean fallback) {
        final String msg = prompt + (fallback ? " (Y/n) " : " (y/N) ");
        System.out.print(msg);

        final String res = scanner
                .nextLine()
                .trim()
                .toLowerCase(Locale.ROOT);

        return switch (res) {
            case "y", "yes", "yeah", "yep" -> true;
            case "n", "no", "nah", "nope" -> false;
            default -> fallback;
        };
    }

    /**
     * Ask a yes/no question, defaulting to false if no answer given.
     *
     * @param prompt The user-facing question string
     * @return true for input starting with "y", otherwise false
     */
    public boolean query(String prompt) {
        return query(prompt, false);
    }
}
