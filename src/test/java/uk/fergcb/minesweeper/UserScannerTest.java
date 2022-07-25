package uk.fergcb.minesweeper;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class UserScannerTest {
    @Test
    void takeLine_takes_a_line() {
        String input = """
                First line
                Second line
                """;
        InputStream in = new ByteArrayInputStream(input.getBytes());
        UserScanner us = new UserScanner(in);

        String expected = "FIRST LINE";
        assertEquals(expected, us.takeLine(), "UserScanner::takeLine should take, trim, and uppercase a line.");
    }

    @Test
    void query_rejects_on_N() {
        String input = "n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        UserScanner us = new UserScanner(in);

        assertFalse(us.query("prompt"), "UserScanner::query should return false on negative input.");
    }

    @Test
    void query_accept_on_Y() {
        String input = "y";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        UserScanner us = new UserScanner(in);

        assertTrue(us.query("prompt"), "UserScanner::query should return true on positive input.");
    }

    @Test
    void query_fallback_on_empty() {
        String input = "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        UserScanner us = new UserScanner(in);
        assertTrue(us.query("prompt", true), "UserScanner::query should return the fallback value on empty input.");
    }

    @Test
    void query_fallback_on_invalid() {
        String input = "invalid\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        UserScanner us = new UserScanner(in);
        assertTrue(us.query("prompt", true), "UserScanner::query should return the fallback value on invalid input.");
    }

}
