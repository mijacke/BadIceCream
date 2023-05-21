package main.levels.managers;

import main.entities.other.Direction;
import main.entities.other.IceBlock;
import main.levels.Level;

import java.awt.Graphics;
/**
 * The IceBlockManager class manages the ice blocks in a level.
 */
public class IceBlockManager {
    private final Level level;
    private IceBlock[][] iceBlocks;

    /**
     * Constructs a new IceBlockManager object with the specified level.
     *
     * @param level The level object.
     */
    public IceBlockManager(Level level) {
        this.level = level;
        this.iceBlocks = level.getIceBlocks();
    }

    /**
     * Interacts with an ice block at the specified coordinates, creating or removing it based on the 'create' parameter.
     *
     * @param x      The x-coordinate of the ice block.
     * @param y      The y-coordinate of the ice block.
     * @param create true to create the ice block, false to remove it.
     */
    public void interactWithIceBlock(int x, int y, boolean create) {
        // Check if the coordinates are out of bounds
        if (x < 0 || x >= this.level.getLevelLayout().layout()[0].length || y < 0 || y >= this.level.getLevelLayout().layout().length) {
            return;
        }

        // Check if creation is requested and if the ice block can be created at the specified location
        if (create && (this.level.getLevelLayout().layout()[y][x] == Level.WALL ||
                (this.level.getEnemyManager().isPresent() && this.level.getEnemyManager().get().isEnemyAt(x, y)) ||
                this.level.getLevelLayout().layout()[y][x] == Level.ICE_BLOCK)) {
            return;
        }

        // Create or remove the ice block based on the 'create' parameter
        if (create) {
            this.level.getLevelLayout().layout()[y][x] = Level.ICE_BLOCK;
            this.iceBlocks[y][x] = new IceBlock(x * this.level.getCellWidth(), y * this.level.getCellHeight(),
                    this.level.getCellWidth(), this.level.getCellHeight());
        } else {
            this.level.getLevelLayout().layout()[y][x] = Level.EMPTY;
            this.iceBlocks[y][x] = null;
        }
    }

    /**
     * Checks if the specified coordinates are out of bounds.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return true if the coordinates are out of bounds, false otherwise.
     */
    public boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= this.level.getLevelLayout().layout()[0].length || y < 0 || y >= this.level.getLevelLayout().layout().length;
    }

    /**
     * Checks if an ice block exists at the specified coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return true if an ice block exists at the coordinates, false otherwise.
     */
    public boolean hasIceBlock(int x, int y) {
        if (x < 0 || x >= this.level.getLevelLayout().layout()[0].length || y < 0 || y >= this.level.getLevelLayout().layout().length) {
            return false;
        }
        return this.level.getLevelLayout().layout()[y][x] == Level.ICE_BLOCK;
    }

    /**
     * Checks if an ice block exists at the specified coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return true if an ice block exists at the coordinates, false otherwise.
     */
    public boolean isIceBlock(int x, int y) {
        return this.level.getLevelLayout().layout()[y][x] == Level.ICE_BLOCK;
    }

    /**
     * Checks if a wall exists at the specified coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return true if a wall exists at the coordinates, false otherwise.
     */
    public boolean isWallAt(int x, int y) {
        if (this.isOutOfBounds(x, y)) {
            return false;
        }
        return this.level.getLevelLayout().layout()[y][x] == Level.WALL;
    }

    /**
     * Checks if an ice block exists at the specified coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return true if an ice block exists at the coordinates, false otherwise.
     */
    public boolean isIceBlockAt(int x, int y) {
        if (this.isOutOfBounds(x, y)) {
            return false;
        }
        return this.level.getLevelLayout().layout()[y][x] == Level.ICE_BLOCK;
    }

    /**
     * Checks if an enemy exists at the specified coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return true if an enemy exists at the coordinates, false otherwise.
     */
    public boolean isEnemyAt(int x, int y) {
        if (this.isOutOfBounds(x, y)) {
            return false;
        }
        return this.level.getEnemyManager().isPresent() && this.level.getEnemyManager().get().isEnemyAt(x, y);
    }

    /**
     * Checks if there is a wall, ice block, or enemy ahead at the specified coordinates and direction.
     *
     * @param x         The x-coordinate.
     * @param y         The y-coordinate.
     * @param direction The direction to check.
     * @return true if there is a wall, ice block, or enemy ahead, false otherwise.
     */
    public boolean isWallOrIceBlockOrEnemyAhead(int x, int y, Direction direction) {
        switch (direction) {
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
        }

        if (this.isOutOfBounds(x, y)) {
            return true;
        }

        return this.isWallAt(x, y) || this.isIceBlockAt(x, y) || this.isEnemyAt(x, y);
    }

    /**
     * Returns the array of ice blocks.
     *
     * @return The ice block array.
     */
    public IceBlock[][] getIceBlocks() {
        return this.iceBlocks;
    }

    /**
     * Melts the ice blocks around the specified coordinates within the specified heat radius.
     *
     * @param x          The x-coordinate.
     * @param y          The y-coordinate.
     * @param heatRadius The heat radius.
     */
    public void meltIceAround(int x, int y, int heatRadius) {
        int cellWidth = this.level.getCellWidth();
        int cellHeight = this.level.getCellHeight();

        // Convert pixel coordinates to cell coordinates
        int cellX = x / cellWidth;
        int cellY = y / cellHeight;

        // Determine the range of indices
        int minX = Math.max(0, cellX - heatRadius);
        int maxX = Math.min(this.iceBlocks[0].length, cellX + heatRadius + 1);
        int minY = Math.max(0, cellY - heatRadius);
        int maxY = Math.min(this.iceBlocks.length, cellY + heatRadius + 1);

        // Melt the ice in the specified radius
        for (int i = minY; i < maxY; i++) {
            for (int j = minX; j < maxX; j++) {
                if (this.iceBlocks[i][j] != null) {
                    this.interactWithIceBlock(j, i, false);
                }
            }
        }
    }

    /**
     * Initializes the ice blocks based on the level layout.
     */
    public void initializeIceBlocks() {
        this.iceBlocks = new IceBlock[this.level.getLevelLayout().layout().length][this.level.getLevelLayout().layout()[0].length];

        for (int y = 0; y < this.level.getLevelLayout().layout().length; y++) {
            for (int x = 0; x < this.level.getLevelLayout().layout()[y].length; x++) {
                if (this.level.getLevelLayout().layout()[y][x] == Level.ICE_BLOCK) {
                    int iceBlockX = x * this.level.getCellWidth();
                    int iceBlockY = y * this.level.getCellHeight();

                    this.iceBlocks[y][x] = new IceBlock(iceBlockX, iceBlockY, this.level.getCellWidth(), this.level.getCellHeight());
                }
            }
        }
    }

    /**
     * Renders the ice blocks.
     *
     * @param g The graphics object.
     */
    public void render(Graphics g) {
        for (int y = 0; y < this.level.getLevelLayout().layout().length; y++) {
            for (int x = 0; x < this.level.getLevelLayout().layout()[0].length; x++) {
                if (this.level.getLevelLayout().layout()[y][x] == Level.ICE_BLOCK) {
                    this.iceBlocks[y][x].render(g);
                }
            }
        }
    }
}