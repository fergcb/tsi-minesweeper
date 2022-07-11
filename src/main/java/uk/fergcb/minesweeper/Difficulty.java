package uk.fergcb.minesweeper;

public enum Difficulty {
    EASY(8, 8, 8, Text.green("Easy")),
    MEDIUM(10, 10, 20, Text.yellow("Medium")),
    HARD(25, 25, 150, Text.red("Hard"));

    public final int width, height, mineCount;
    public final String consoleName;

    Difficulty(int width, int height, int mineCount, String consoleName) {
        this.width = width;
        this.height = height;
        this.mineCount = mineCount;
        this.consoleName = consoleName;
    }
}
