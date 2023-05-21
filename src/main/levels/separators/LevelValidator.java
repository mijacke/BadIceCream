package main.levels.separators;

import main.levels.Level;

/**
 * The LevelValidator class is responsible for validating movements and positions within the level.
 * It provides methods to check if a position is empty or if an enemy can move to a position.
 */
public class LevelValidator {
    private final Level level;

    /**
     * Constructs a LevelValidator object with the specified level.
     *
     * @param level The level to validate.
     */
    public LevelValidator(Level level) {
        this.level = level;
    }

    /**
     * Checks if a position is empty and can be moved to.
     *
     * @param x      The x-coordinate of the position.
     * @param y      The y-coordinate of the position.
     * @param width  The width of the object.
     * @param height The height of the object.
     * @return True if the position is empty and can be moved to, false otherwise.
     */
    public boolean canMoveTo(int x, int y, int width, int height) {
        int cellWidth = this.level.getCellWidth();
        int cellHeight = this.level.getCellHeight();

        return this.isEmpty(x / cellWidth, y / cellHeight) &&
                this.isEmpty((x + width - 1) / cellWidth, y / cellHeight) &&
                this.isEmpty(x / cellWidth, (y + height - 1) / cellHeight) &&
                this.isEmpty((x + width - 1) / cellWidth, (y + height - 1) / cellHeight);
    }

    /**
     * Checks if a position is empty.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @return True if the position is empty, false otherwise.
     */
    public boolean isEmpty(int x, int y) {
        if (x < 0 || x >= this.level.getLevelLayout().layout()[0].length || y < 0 || y >= this.level.getLevelLayout().layout().length) {
            return false;
        }

        return this.level.getLevelLayout().layout()[y][x] == Level.EMPTY;
    }

    /**
     * Checks if an enemy can move to a position.
     *
     * @param x      The x-coordinate of the position.
     * @param y      The y-coordinate of the position.
     * @param width  The width of the enemy.
     * @param height The height of the enemy.
     * @return True if the enemy can move to the position, false otherwise.
     */
    public boolean canEnemyMoveTo(int x, int y, int width, int height) {
        int cellWidth = this.level.getCellWidth();
        int cellHeight = this.level.getCellHeight();

        return this.isWall(x / cellWidth, y / cellHeight) &&
                this.isWall((x + width - 1) / cellWidth, y / cellHeight) &&
                this.isWall(x / cellWidth, (y + height - 1) / cellHeight) &&
                this.isWall((x + width - 1) / cellWidth, (y + height - 1) / cellHeight);
    }

    /**
     * Checks if a position is a wall.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @return True if the position is a wall, false otherwise.
     */
    public boolean isWall(int x, int y) {
        if (x < 0 || x >= this.level.getLevelLayout().layout()[0].length || y < 0 || y >= this.level.getLevelLayout().layout().length) {
            return false;
        }

        return this.level.getLevelLayout().layout()[y][x] != this.level.WALL;
    }
}
