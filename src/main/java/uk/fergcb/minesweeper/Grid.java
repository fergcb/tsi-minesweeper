package uk.fergcb.minesweeper;

import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.*;

public class Grid {
    private static final Random random = new Random();

    private static final String MINE = "@";
    private static final String HIDDEN = "#";
    private static final String FLAG = "F";
    private static final String EMPTY = ".";

    private final int width;
    private final int height;
    private final int mineCount;
    private final Tile[][] tiles;

    private boolean gameOver;
    private boolean gameWon;

    public Grid(int width, int height, int mineCount) {
        this.width = width;
        this.height = height;
        this.mineCount = mineCount;
        this.tiles = new Tile[width][height];

        gameOver = gameWon = false;

        this.generate();
    }

    /**
     * Generate the bombs and tiles in the grid,
     *  according to the parameters set in the constructor.
     */
    private void generate() {
        // Generate the mine locations
        List<Point> minePositions = new ArrayList<>();
        while(minePositions.size() < mineCount) {
            int bombX = random.nextInt(width);
            int bombY = random.nextInt(height);
            Point minePosition = new Point(bombX, bombY);

            if (!minePositions.contains(minePosition)) {
                minePositions.add(minePosition);
            }
        }

        // Populate the grid
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Point pos = new Point(x, y);
                if (minePositions.contains(pos)) {
                    this.tiles[y][x] = new Tile(true, 0);
                    continue;
                }

                int adjacentMines = countAdjacentMines(x, y, minePositions);
                this.tiles[y][x] = new Tile(false, adjacentMines);
            }
        }
    }

    /**
     * Get a list of points in the grid adjacent to a given set of coords
     * @param x The x-coordinate of the centre point
     * @param y The y-coordinate of the centre point
     * @return A list of adjacent points
     */
    private List<Point> getAdjacentPoints(int x, int y) {
        List<Point> points = new ArrayList<>();
        for (int yo = -1; yo <= 1; yo++) {
            for (int xo = -1; xo <= 1; xo++) {
                if (xo == 0 && yo == 0) continue;
                int xx = x + xo;
                int yy = y + yo;
                if (isOutOfBounds(xx, yy)) continue;
                Point pos = new Point(xx, yy);
                points.add(pos);
            }
        }
        return points;
    }

    /**
     * Check whether a given point is outside the grid
     * @param x The x-coordinate of the tile to check
     * @param y The y-coordinate of the tile to check
     * @return true if the point is not on the grid, else false
     */
    public boolean isOutOfBounds(int x, int y) {
        return x < 0 || y < 0 || x >= width || y >= width;
    }

    /**
     * Count the number of mines that are directly adjacent to a given point
     * @param x The x-coordinate of the centre point.
     * @param y The y-coordinate of the centre point.
     * @param minePositions A list of mine positions
     * @return The number of mines adjacent to the given point
     */
    private int countAdjacentMines (int x, int y, List<Point> minePositions) {
        return (int)getAdjacentPoints(x, y)
                .stream()
                .filter(minePositions::contains)
                .count();
    }

    /**
     * Flag a given tile
     * @param x The x-coordinate of the tile to flag
     * @param y The y-coordinate of the tile to flag
     */
    public void flag (int x, int y) {
        Tile tile = tiles[y][x];
        if (!tile.isHidden()) {
            System.out.println("You can't flag a revealed square!");
            return;
        }
        tile.toggleFlagged();
        gameWon = checkWin();
    }

    /**
     * Reveal a given tile and any adjacent revealable tiles. End the game the tile is a mine.
     * @param x The x-coordinate of the tile to reveal
     * @param y The y-coordinate of the tile to reveal
     */
    public void reveal (int x, int y) {
        Tile tile = tiles[y][x];
        tile.reveal();
        if (tile.isMine) {
            gameOver = true;
            return;
        }
        floodReveal(x, y);
        gameWon = checkWin();
    }

    /**
     * Reveal all empty tiles adjacent to a given tile. Stop at tiles bordering mines.
     * @param x The x-coordinate of the origin tile
     * @param y The y-coordinate of the origin tile
     */
    private void floodReveal (int x, int y) {
        List<Point> visited = new ArrayList<>();
        Queue<Point> queue = new LinkedList<>();

        queue.add(new Point(x, y));

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            visited.add(current);
            Tile currentTile = tiles[current.y][current.x];
            if (currentTile.isFlagged() || currentTile.isMine) continue;
            currentTile.reveal();
            if (currentTile.adjacentMines > 0) continue;
            for (Point next : getAdjacentPoints(current.x, current.y)) {
                if (visited.contains(next)) continue;
                queue.add(next);
            }
        }
    }

    /**
     * Check whether the game has been won
     * @return the value of the gameWon field
     */
    public boolean isGameWon () {
        return gameWon;
    }

    /**
     * Check whether the game has been won or lost
     * @return true if the game is won or lost, else false
     */
    public boolean isComplete () {
        return gameOver || gameWon;
    }

    /**
     * Check whether the game has been won
     * @return true if all mines are flagged and all non-mines are revealed, else false
     */
    public boolean checkWin () {
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile.isMine && !tile.isFlagged()) return false;
                if (!tile.isMine && tile.isHidden()) return false;
            }
        }
        return true;
    }

    /**
     * Print a user-friendly string representation of the grid to stdout
     * @param drawMines Whether to show mines on the board.
     */
    public void prettyPrint (boolean drawMines) {
        StringBuilder sb = new StringBuilder();
        // Print the X axis labels
        sb.append("  ");
        for(int x = 0; x < width; x++) {
            sb.append(Text.blue(Character.toString(Coords.toBase36(x))));
            sb.append(" ");
        }
        sb.append("\n");

        for (int y = 0; y < height; y++) {
            // Print the row label
            sb.append(Text.blue(Character.toString(Coords.toBase36(y))));
            sb.append(" ");
            // Iterate the tiles in the row
            Tile[] row = tiles[y];
            for (Tile tile : row) {
                // Draw an appropriate symbol based on the state of the tile
                if (tile.isMine && drawMines) sb.append(Text.red(MINE));
                else if (tile.isFlagged()) sb.append(Text.green(FLAG));
                else if (tile.isHidden()) sb.append(HIDDEN);
                else if (tile.adjacentMines > 0) sb.append(Text.cyan(Integer.toString(tile.adjacentMines)));
                else sb.append(Text.white(EMPTY));
                sb.append(" ");
            }
            sb.append("\n");
        }

        String map = sb.toString();
        System.out.print(map);
    }
}
