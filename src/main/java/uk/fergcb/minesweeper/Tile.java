package uk.fergcb.minesweeper;

/**
 * A data class to represent a single cell in the minesweeper grid.
 */
public class Tile {

    public final boolean isMine;
    public final int adjacentMines;

    private boolean isFlagged, isHidden;

    public Tile (boolean isMine, int adjacentMines) {
        this.isMine = isMine;
        this.adjacentMines = adjacentMines;

        this.isHidden = true;
        this.isFlagged = false;
    }

    public void toggleFlagged() {
        isFlagged = !isFlagged;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void reveal() {
        this.isHidden = false;
    }

    public boolean isHidden() {
        return isHidden;
    }
}
