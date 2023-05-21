package main.levels;

import main.levels.config.LevelConfiguration;
import main.levels.config.LevelLayout;
import main.levels.managers.EnemyManager;
import main.levels.managers.FruitManager;
import main.entities.other.Player;
import main.entities.other.IceBlock;
import main.graphics.SpriteSheet;
import main.levels.managers.IceManager;
import main.levels.separators.Renderable;
import main.levels.separators.LevelRenderer;
import main.levels.separators.LevelUpdater;
import main.levels.separators.LevelValidator;
import main.levels.separators.Updatable;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * The Level class represents a game level.
 * It manages the layout, entities, and gameplay logic of a specific level.
 */
public class Level {
    public static final int EMPTY = 0;
    public static final int WALL = 1;
    public static final int ICE_BLOCK = 2;
    private final LevelLayout levelLayout;
    private IceBlock[][] iceBlocks;
    private final int cellWidth;
    private final int cellHeight;
    private final Player player;
    private final EnemyManager enemyManager;
    private final FruitManager fruitManager;
    private final IceManager iceManager;
    private final SpriteSheet spriteSheet = new SpriteSheet("res/fruits.png");
    private BufferedImage sprite;
    private final LevelConfiguration config;
    private final Updatable updater;
    private final Renderable renderer;
    private final LevelValidator validator;

    /**
     * Constructs a new Level object with the specified configuration, width, height, and player.
     *
     * @param config The configuration of the level.
     * @param width The width of the level.
     * @param height The height of the level.
     * @param player The player object.
     */
    public Level(LevelConfiguration config, int width, int height, Player player) {
        this.levelLayout = new LevelLayout(config.getLayout());
        this.player = player;
        this.config = config;
        this.cellWidth = width / config.getLayout()[0].length;
        this.cellHeight = height / config.getLayout().length;

        // Instantiate the managers and other components
        this.fruitManager = new FruitManager(this, config.getFruits());
        this.enemyManager = new EnemyManager(this, config.getEnemies());
        this.iceManager = new IceManager(this);
        this.updater = new LevelUpdater(this);
        this.renderer = new LevelRenderer(this);
        this.validator = new LevelValidator(this);

        // Initial game setup
        this.initializeGame();
        this.loadWallImage();
    }

    /**
     * Checks if the specified object can move to the given coordinates and dimensions.
     *
     * @param x The x-coordinate of the object.
     * @param y The y-coordinate of the object.
     * @param width The width of the object.
     * @param height The height of the object.
     * @return True if the object can move to the specified coordinates, false otherwise.
     */
    public boolean canMoveTo(int x, int y, int width, int height) {
        return this.validator.canMoveTo(x, y, width, height);
    }

    /**
     * Checks if an enemy can move to the given coordinates and dimensions.
     *
     * @param x The x-coordinate of the enemy.
     * @param y The y-coordinate of the enemy.
     * @param width The width of the enemy.
     * @param height The height of the enemy.
     * @return True if the enemy can move to the specified coordinates, false otherwise.
     */
    public boolean canEnemyMoveTo(int x, int y, int width, int height) {
        return this.validator.canEnemyMoveTo(x, y, width, height);
    }

    /**
     * Initializes the game by spawning enemies, fruits, and ice blocks.
     */
    public void initializeGame() {
        this.enemyManager.spawnEnemies(this.cellWidth, this.cellHeight);
        this.fruitManager.spawnFruitsOfType();
        this.initializeIceBlocks();
        this.iceBlocks = this.iceManager.getIceBlockManager().getIceBlocks();
    }

    /**
     * Initializes the ice blocks in the level.
     */
    public void initializeIceBlocks() {
        this.iceManager.initializeIceBlocks();
    }

    /**
     * Checks if an ice block exists at the specified coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return True if an ice block exists at the specified coordinates, false otherwise.
     */
    public boolean hasIceBlock(int x, int y) {
        return this.iceManager.getIceBlockManager().hasIceBlock(x, y);
    }

    /**
     * Loads the wall image used for rendering the level.
     */
    private void loadWallImage() {
        try {
            this.sprite = ImageIO.read(new File("res/wallBlock.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Renders the level.
     *
     * @param g The graphics object used for rendering.
     */
    public void render(Graphics g) {
        this.renderer.render(g);
    }

    /**
     * Updates the level.
     * This method should be called to update the state and behavior of the level.
     */
    public void update() {
        this.updater.update();
    }

    /**
     * Returns the sprite sheet used for obtaining sprite images.
     *
     * @return The sprite sheet.
     */
    public SpriteSheet getSpriteSheet() {
        return this.spriteSheet;
    }

    /**
     * Returns the ice blocks in the level.
     *
     * @return The 2D array of ice blocks.
     */
    public IceBlock[][] getIceBlocks() {
        return this.iceBlocks;
    }

    /**
     * Returns the width of a single cell in the level.
     *
     * @return The cell width.
     */
    public int getCellWidth() {
        return this.cellWidth;
    }

    /**
     * Returns the height of a single cell in the level.
     *
     * @return The cell height.
     */
    public int getCellHeight() {
        return this.cellHeight;
    }

    /**
     * Returns the ice manager associated with the level.
     *
     * @return An optional containing the ice manager, or empty if it does not exist.
     */
    public Optional<IceManager> getIceManager() {
        return Optional.ofNullable(this.iceManager);
    }

    /**
     * Returns the fruit manager associated with the level.
     *
     * @return An optional containing the fruit manager, or empty if it does not exist.
     */
    public Optional<FruitManager> getFruitManager() {
        return Optional.ofNullable(this.fruitManager);
    }

    /**
     * Returns the enemy manager associated with the level.
     *
     * @return An optional containing the enemy manager, or empty if it does not exist.
     */
    public Optional<EnemyManager> getEnemyManager() {
        return Optional.ofNullable(this.enemyManager);
    }

    /**
     * Returns the layout of the level.
     *
     * @return The level layout.
     */
    public LevelLayout getLevelLayout() {
        return this.levelLayout;
    }

    /**
     * Returns the player object in the level.
     *
     * @return The player object.
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Returns the configuration of the level.
     *
     * @return The level configuration.
     */
    public LevelConfiguration getConfig() {
        return this.config;
    }

    /**
     * Returns the sprite image used for rendering the level's walls.
     *
     * @return The wall sprite image.
     */
    public BufferedImage getSprite() {
        return this.sprite;
    }
}
