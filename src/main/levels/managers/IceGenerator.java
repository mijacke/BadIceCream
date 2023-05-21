package main.levels.managers;

import main.entities.other.Direction;
import main.levels.Level;

/**
 * The IceGenerator class represents a player's ability to generate new ice blocks in the game.
 */
public class IceGenerator {
    private final Level level;
    private final IceBlockManager iceBlockManager;
    private Direction generationDirection;
    private int generationX;
    private int generationY;
    private static final long ICE_GENERATION_INTERVAL = 75; // Time in milliseconds
    private long lastGenerationTime = 0;
    private boolean generatingIce = false;

    /**
     * Constructs an IceGenerator object with the specified level and ice block manager.
     *
     * @param level            The level.
     * @param iceBlockManager  The ice block manager.
     */
    public IceGenerator(Level level, IceBlockManager iceBlockManager) {
        this.level = level;
        this.iceBlockManager = iceBlockManager;
    }

    private void interactWithIceBlock(int x, int y) {
        this.iceBlockManager.interactWithIceBlock(x, y, true);
    }

    /**
     * Starts generating ice in the specified coordinates and direction.
     *
     * @param x         The x-coordinate.
     * @param y         The y-coordinate.
     * @param direction The direction.
     */
    public void startGeneratingIce(int x, int y, Direction direction) {
        this.generatingIce = true;
        this.generationDirection = direction;
        this.generationX = x;
        this.generationY = y;
        this.lastGenerationTime = System.currentTimeMillis();

        if (this.iceBlockManager.isWallOrIceBlockOrEnemyAhead(this.generationX, this.generationY, this.generationDirection)) {
            this.stopGeneratingIce();
        }
    }

    /**
     * Updates the ice generator.
     */
    public void update() {
        long currentTime = System.currentTimeMillis();

        if (this.generatingIce) {
            if (currentTime - this.lastGenerationTime >= ICE_GENERATION_INTERVAL) {
                if (this.iceBlockManager.isWallOrIceBlockOrEnemyAhead(this.generationX, this.generationY, this.generationDirection)) {
                    // create an ice block before stopping the ice generation
                    this.interactWithIceBlock(this.generationX, this.generationY);
                    this.stopGeneratingIce();
                    return;
                }

                this.stopIfCollidedWithEnemy();
                // create an ice block before updating coordinates
                this.interactWithIceBlock(this.generationX, this.generationY);

                this.updateCoordinates(this.generationDirection);

                this.lastGenerationTime = currentTime;
            }
        }
    }

    /**
     * Stops generating ice.
     */
    public void stopGeneratingIce() {
        this.generatingIce = false;
    }

    private void stopIfCollidedWithEnemy() {
        if (this.level.getEnemyManager().isPresent()) {
            for (int i = 0; i <= 2; i++) { // check current and next two cells
                int futureX = this.generationX;
                int futureY = this.generationY;

                switch (this.generationDirection) {
                    case UP:
                        futureY -= i;
                        break;
                    case DOWN:
                        futureY += i;
                        break;
                    case LEFT:
                        futureX -= i;
                        break;
                    case RIGHT:
                        futureX += i;
                        break;
                }

                if (this.level.getEnemyManager().get().isEnemyAt(futureX, futureY)) {
                    this.stopGeneratingIce();
                    return;
                }
            }
        }
    }

    private void updateCoordinates(Direction direction) {
        switch (direction) {
            case UP -> this.generationY--;
            case DOWN -> this.generationY++;
            case LEFT -> this.generationX--;
            case RIGHT -> this.generationX++;
        }
    }
}