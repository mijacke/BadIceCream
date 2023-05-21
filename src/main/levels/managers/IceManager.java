package main.levels.managers;

import main.entities.other.Direction;
import main.levels.Level;

import java.awt.Graphics;

/**
 * The IceManager class manages the ice blocks in the game, including their generation, breaking, and melting.
 */
public class IceManager {
    private final IceBlockManager iceBlockManager;
    private final IceGenerator iceGenerator;
    private final IceBreaker iceBreaker;

    /**
     * Constructs an IceManager object with the specified level.
     *
     * @param level The level.
     */
    public IceManager(Level level) {
        this.iceBlockManager = new IceBlockManager(level);
        this.iceGenerator = new IceGenerator(level, this.iceBlockManager);
        this.iceBreaker = new IceBreaker(level, this.iceBlockManager);
    }

    /**
     * Initiates the process of breaking ice in the specified direction.
     *
     * @param x         The x-coordinate of the ice block.
     * @param y         The y-coordinate of the ice block.
     * @param direction The direction in which to break the ice.
     */
    public void breakIceInDirection(int x, int y, Direction direction) {
        this.iceBreaker.startBreakingIce(x, y, direction);
    }

    /**
     * Initiates the process of creating new ice blocks in the specified direction.
     *
     * @param x         The x-coordinate where the ice block should be created.
     * @param y         The y-coordinate where the ice block should be created.
     * @param direction The direction in which to create the ice block.
     */
    public void createIceInDirection(int x, int y, Direction direction) {
        this.iceGenerator.startGeneratingIce(x, y, direction);
    }

    /**
     * Updates the ice blocks in the game.
     */
    public void update() {
        this.iceGenerator.update();
        this.iceBreaker.update();
    }

    /**
     * Initializes the ice blocks at the beginning of the game.
     */
    public void initializeIceBlocks() {
        this.iceBlockManager.initializeIceBlocks();
    }

    /**
     * Renders the ice blocks on the graphics context.
     *
     * @param g The graphics context.
     */
    public void render(Graphics g) {
        this.iceBlockManager.render(g);
    }

    /**
     * Melts the ice blocks around the specified coordinates within the given heat radius.
     *
     * @param x          The x-coordinate.
     * @param y          The y-coordinate.
     * @param heatRadius The radius within which the ice blocks should be melted.
     */
    public void meltIceAround(int x, int y, int heatRadius) {
        this.iceBlockManager.meltIceAround(x, y, heatRadius);
    }

    /**
     * Retrieves the ice block manager.
     *
     * @return The ice block manager.
     */
    public IceBlockManager getIceBlockManager() {
        return this.iceBlockManager;
    }
}
