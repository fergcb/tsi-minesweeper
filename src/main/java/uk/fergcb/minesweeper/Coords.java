package uk.fergcb.minesweeper;

/**
 * A util class to handle coordinate-related processes
 */
public class Coords {

    private Coords() {
        throw new IllegalStateException("Coords is a utility class and should not be instantiated.");
    }

    /**
     * Take a move input by the user and parse it into a Move object.
     * @param moveString The move entered by the user
     * @return the parsed Move object
     */
    public static Move parseMove(String moveString) {
        return new Move(
                fromBase36(moveString.charAt(0)),
                fromBase36(moveString.charAt(1)),
                moveString.length() > 2 && moveString.charAt(2) == 'F'
        );
    }

    /**
     * Check if a string represents a valid move
     * @param moveString The string entered by the user
     * @return true if the move is valid, else false.
     */
    public static boolean isValidMove(String moveString) {
        // Move must be 2-3 characters long
        if (moveString.length() < 2 || moveString.length() > 3) return false;
        // The first two characters must be base 36 digits
        if (!isBase36(moveString.charAt(0)) || !isBase36(moveString.charAt(1))) return false;
        // The third character must be 'F', if present
        return moveString.length() <= 2 || moveString.charAt(2) == 'F';
    }

    /**
     * Check if a char is in the ranges 0-9 or A-Z
     * @param c The character to check
     * @return true if the char is valid base 36, else false.
     */
    public static boolean isBase36(char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z');
    }

    /**
     * Convert a character from a base 36 digit to an integer
     * @param c The character to convert
     * @return A denary integer
     */
    public static int fromBase36(char c) {
        return c <= '9' ? (c - '0') : (c - 'A') + 10;
    }

    /**
     * Convert an integer 0-35 to a base 36 digit
     * @param i The integer to convert
     * @return A char in the ranges 0-9 or A-Z
     */
    public static char toBase36(int i) {
        return (char)(i < 10 ? ('0' + i) : ('A' + (i - 10)));
    }
}
