package uk.fergcb.minesweeper;

public enum Difficulty {
    EASY(8, 8, 8, Text.green("Easy")),
    MEDIUM(10, 10, 20, Text.yellow("Medium")),
    HARD(25, 25, 150, Text.red("Hard"));

    private final int width;
    private final int height;
    private final int mineCount;
    public final String consoleName;

    Difficulty(int width, int height, int mineCount, String consoleName) {
        this.width = width;
        this.height = height;
        this.mineCount = mineCount;
        this.consoleName = consoleName;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMineCount() {
        return mineCount;
    }
}
