package uk.fergcb.minesweeper;

import java.util.List;
import java.util.stream.Stream;

public class Main {

    private static final UserScanner us = new UserScanner();

    /**
     * Application entry point
     * @param args Command-line args
     */
    public static void main(String[] args) {
        System.out.println("Welcome to TSInesweeper!\n");
        do {
            Grid grid = configureGrid();
            startGame(grid);
        } while(us.query("Do you want to keep playing?"));
    }

    /**
     * Get the user's difficulty preference and build a grid accordingly
     * @return the configured Grid object
     */
    private static Grid configureGrid() {
        System.out.println("Please select a difficulty mode:");
        for (Difficulty difficulty : Difficulty.values()) {
            System.out.printf(
                    "%s (%d x %d, %d mines)%n",
                    difficulty.consoleName, difficulty.getWidth(), difficulty.getHeight(), difficulty.getMineCount()
            );
        }

        String prompt = " > ";
        List<String> options = Stream.of(Difficulty.values())
                .map(Difficulty::name)
                .toList();
        String diffString;
        do {
            System.out.print(prompt);
            diffString = us.takeLine();
        } while (!options.contains(diffString));

        Difficulty difficulty = Difficulty.valueOf(diffString);

        return new Grid(difficulty.getWidth(), difficulty.getHeight(), difficulty.getMineCount());
    }

    /**
     * Enter the game loop
     * @param grid The minesweeper board to play on
     */
    private static void startGame(Grid grid) {

        int currentRound = 1;
        do {
            System.out.printf("== Round #%d ==%n", currentRound++);
            grid.prettyPrint(false);
            Move move = getPlayerMove(grid);
            if (move.shouldFlag()) grid.flag(move.x(), move.y());
            else grid.reveal(move.x(), move.y());
        } while (!grid.isComplete());

        grid.prettyPrint(true);
        if (grid.isGameWon()) System.out.println("You won the game!");
        else System.out.println("You stepped on a mine!");

    }

    /**
     * Get coordinates from the player to reveal/flag
     * @return A string describing the player's move
     */
    private static Move getPlayerMove(Grid grid) {
        String prompt = " > ";
        Move move;
        while (true) {
            System.out.print(prompt);

            String moveString = us.takeLine();
            if (!Coords.isValidMove(moveString)) {
                System.out.println("Invalid move.\nA move should consist of XY coords and an optional 'f' to flag a tile.");
                continue;
            }

            move = Coords.parseMove(moveString);
            if (grid.isOutOfBounds(move.x(), move.y())) {
                System.out.println("Invalid move. The coordinates you entered aren't on the grid.");
                continue;
            }
            break;
        }

        return move;
    }
}
