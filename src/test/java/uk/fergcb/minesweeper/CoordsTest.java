package uk.fergcb.minesweeper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CoordsTest {
    @Test
    void parseMove_parses_valid_move() {
        String input = "A5";
        Move expected = new Move(10, 5, false);

        assertEquals(expected, Coords.parseMove(input), "Coords::parseMove should parse a Move from a valid input string.");
    }

    @Test
    void parseMove_parses_valid_move_with_flag() {
        String input = "2ZF";
        Move expected = new Move(2, 35, true);

        assertEquals(expected, Coords.parseMove(input), "Coords::parseMove should parse a Move from a valid input string with a flag.");
    }

    @Test
    void isValidMove_accepts_move_without_flag() {
        String input = "3D";

        assertTrue(Coords.isValidMove(input), "Coords::isValidMove should return true for a two-char string of coords in range.");
    }

    @Test
    void isValidMove_accepts_move_with_flag() {
        String input = "4EF";

        assertTrue(Coords.isValidMove(input), "Coords::isValidMove should return true for a three-char string of coords followed by 'F'.");
    }

    @Test
    void isValidMove_rejects_short_input() {
        String input = "A";

        assertFalse(Coords.isValidMove(input), "Coords::isValidMove should return false for input shorter than 2 chars.");
    }

    @Test
    void isValidMove_rejects_long_input() {
        String input = "4EFX";

        assertFalse(Coords.isValidMove(input), "Coords::isValidMove should return false for input longer than 3 chars.");
    }

    @Test
    void isValidMove_rejects_invalid_flag_char() {
        String input = "4Ex";

        assertFalse(Coords.isValidMove(input), "Coords::isValidMove should return false if the third char is not F.");
    }

    @Test
    void isValidMove_rejects_if_not_base36() {
        String input = "4Ex";

        assertFalse(Coords.isValidMove(input), "Coords::isValidMove should return false if the third char is not F.");
    }
}
