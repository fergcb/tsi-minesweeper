package uk.fergcb.minesweeper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TileTest {

    @Test
    void toggleFlagged_toggles_isFlagged () {
        Tile tile = new Tile(false, 0);

        tile.toggleFlagged();

        assertTrue(tile.isFlagged(), "Tile::toggleFlagged should flag an unflagged tile.");
    }

    @Test
    void reveal_sets_isHidden_false () {
        Tile tile = new Tile(false, 0);

        tile.reveal();

        assertFalse(tile.isHidden(), "Tile::reveal should set isHidden to false.");
    }

}
