package main.levels.managers;

import main.entities.other.Direction;
import main.levels.Level;

/**
 * The IceBreaker class represents a player's ability to break ice blocks in the game.
 */
public class IceBreaker {
    private final Level level;
    private final IceBlockManager iceBlockManager;
    private boolean breakingIce = false;
    private Direction breakDirection;
    private int breakX;
    private int breakY;
    private static final long ICE_BREAK_INTERVAL = 75; // Time in milliseconds
    private long lastBreakTime = 0;

    /**
     * Constructs an IceBreaker object with the specified level and ice block manager.
     *
     * @param level            The level.
     * @param iceBlockManager  The ice block manager.
     */
    public IceBreaker(Level level, IceBlockManager iceBlockManager) {
        this.level = level;
        this.iceBlockManager = iceBlockManager;
    }

    /**
     * Starts breaking the ice in the specified coordinates and direction.
     *
     * @param x         The x-coordinate.
     * @param y         The y-coordinate.
     * @param direction The direction.
     */
    public void startBreakingIce(int x, int y, Direction direction) {
        this.breakingIce = true;
        this.breakDirection = direction;
        this.breakX = x;
        this.breakY = y;
        this.lastBreakTime = System.currentTimeMillis();
    }

    private void interactWithIceBlock(int x, int y) {
        this.iceBlockManager.interactWithIceBlock(x, y, false);
    }

    /**
     * Stops breaking the ice.
     */
    public void stopBreakingIce() {
        this.breakingIce = false;
    }

    /**
     * Updates the icebreaker.
     */
    public void update() {
        long currentTime = System.currentTimeMillis();

        if (this.breakingIce) {
            if (currentTime - this.lastBreakTime >= ICE_BREAK_INTERVAL) {
                // break an ice block before updating coordinates
                this.interactWithIceBlock(this.breakX, this.breakY);

                this.updateCoordinates(this.breakDirection);

                if (this.isOutOfBounds(this.breakX, this.breakY) || !this.iceBlockManager.isIceBlock(this.breakX, this.breakY)) {
                    this.stopBreakingIce();
                } else {
                    this.lastBreakTime = currentTime;
                }
            }
        }
    }

    private boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= this.level.getLevelLayout().layout()[0].length || y < 0 || y >= this.level.getLevelLayout().layout().length;
    }

    private void updateCoordinates(Direction direction) {
        switch (direction) {
            case UP -> this.breakY--;
            case DOWN -> this.breakY++;
            case LEFT -> this.breakX--;
            case RIGHT -> this.breakX++;
        }
    }
}