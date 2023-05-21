package main.controller;

import main.entities.other.Player;
import main.entities.other.PlayerInputHandler;
import main.input.KeyInput;
import main.levels.Level;
import main.levels.config.LevelConfigFactory;
import main.levels.config.LevelConfiguration;
import main.levels.managers.FruitManager;
import main.levels.managers.LevelManager;

import javax.swing.Timer;
import javax.swing.JOptionPane;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Supplier;

/**
 * Represents the state of the game, including current player, level, and game loop.
 */
public class GameState implements ActionListener {
    private Timer gameLoopTimer;
    private final PlayerInputHandler inputHandler;
    private Player player;
    private LevelManager levelManager;
    private boolean isNotGameOver;
    private long levelStartTime;
    private static final int LEVEL_DURATION = 120000; // 2 minutes
    private boolean isTimeUp;
    private final int width;
    private final int height;

    private static final Supplier<LevelConfiguration>[] LEVEL_CONFIGURATIONS = new Supplier[]{
        LevelConfigFactory::createLevel1Config,
        LevelConfigFactory::createLevel2Config,
        LevelConfigFactory::createLevel3Config,
        LevelConfigFactory::createLevel4Config,
        LevelConfigFactory::createLevel5Config,
    };

    /**
     * Constructor for GameState.
     *
     * @param width  the width of the game area
     * @param height the height of the game area
     */
    public GameState(int width, int height) {
        this.isNotGameOver = true;
        this.inputHandler = new PlayerInputHandler(new KeyInput());
        this.width = width;
        this.height = height;

        this.initiateNewGame();
    }

    private void initiateNewGame() {
        this.levelManager = new LevelManager();
        for (Supplier<LevelConfiguration> levelConfigSupplier : LEVEL_CONFIGURATIONS) {
            LevelConfiguration levelConfig = levelConfigSupplier.get();
            Level level = new Level(levelConfig, this.width, this.height, this.player);
            this.levelManager.addLevel(level);
        }

        // first time player initialization
        LevelConfiguration firstLevelConfig = LEVEL_CONFIGURATIONS[0].get();
        Point startingPosition = firstLevelConfig.getPlayerStartingPosition();
        int cellWidth = this.width / firstLevelConfig.getLayout()[0].length;
        int cellHeight = this.height / firstLevelConfig.getLayout().length;
        this.player = new Player(startingPosition.x * cellWidth, startingPosition.y * cellHeight, cellWidth,
                cellHeight, this.inputHandler, this.levelManager.getCurrentLevel());
    }

    private String constructGameOverMessage() {
        if (this.isTimeUp) {
            return "Game Over! You ran out of time! Your score was: " + this.player.getPoints();
        } else if (this.isNotGameOver) {
            return "Game Over! You won! Your score was: " + this.player.getPoints();
        } else {
            return "Game Over! You were hit by an enemy! Your score was: " + this.player.getPoints();
        }
    }

    private void resetGame() {
        this.gameLoopTimer.stop();
        String message = this.constructGameOverMessage();
        int result = JOptionPane.showConfirmDialog(null, message + " Would you like to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            this.resetPlayerScore();
            this.initiateNewGame();
            this.levelStartTime = System.currentTimeMillis();
            this.isNotGameOver = true;
            this.isTimeUp = false;
            this.startGameLoop();
        } else {
            System.exit(0);
        }
    }
    private void checkGameState() {
        Level currentLevel = this.levelManager.getCurrentLevel();

        if (currentLevel.getFruitManager().isPresent()) {
            FruitManager currentFruitManager = currentLevel.getFruitManager().get();
            if (currentFruitManager.allFruitsCollected()) {
                if (this.levelManager.isLastLevel()) {
                    this.isNotGameOver = false;
                    this.resetGame();
                } else {
                    this.levelManager.nextLevel();
                    currentLevel = this.levelManager.getCurrentLevel();
                    this.player.setLevel(currentLevel);
                    this.player.updatePosition(currentLevel.getConfig().getPlayerStartingPosition());
                    this.levelStartTime = System.currentTimeMillis();
                }
            }
        }
    }

    private void resetPlayerScore() {
        this.player.setPoints(0);
    }

    /**
     * Starts the main game loop.
     */
    public void startGameLoop() {
        this.gameLoopTimer = new Timer(1000 / 60, this);
        this.gameLoopTimer.start();
        this.isNotGameOver = true;
        this.isTimeUp = false;
        this.levelStartTime = System.currentTimeMillis();
    }

    private void checkLevelTime() {
        if (System.currentTimeMillis() - this.levelStartTime > LEVEL_DURATION) {
            this.isTimeUp = true;
        }
    }

    private void playing() {
        if (!this.isNotGameOver || this.isTimeUp) {
            this.resetGame();
            return;
        }

        this.checkLevelTime();

        this.player.update();
        this.levelManager.getCurrentLevel().update();

        // Check if the player collided with an enemy
        if (this.levelManager.getCurrentLevel().getEnemyManager().isPresent() && this.levelManager.getCurrentLevel().getFruitManager().isPresent()) {
            if (this.levelManager.getCurrentLevel().getEnemyManager().get().checkPlayerCollision(this.player)) {
                this.isNotGameOver = false;
                this.resetGame();
                return;
            }

            this.levelManager.getCurrentLevel().getFruitManager().get().checkFruitCollection(this.player);
            this.checkGameState();
        }
    }

    /**
     * Handles the action event, namely updating the game state.
     *
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.playing();
    }

    /**
     * Renders the game state.
     *
     * @param g the Graphics object to protect
     */
    public void render(Graphics g) {
        this.levelManager.getCurrentLevel().render(g);
        this.player.render(g);
    }

    /**
     * Returns the input handler.
     *
     * @return the input handler
     */
    public PlayerInputHandler getInputHandler() {
        return this.inputHandler;
    }

    /**
     * Returns the player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Returns the start time of the level.
     *
     * @return the start time of the level
     */
    public long getLevelStartTime() {
        return this.levelStartTime;
    }

    /**
     * Returns the duration of the level.
     *
     * @return the duration of the level
     */
    public static int getLevelDuration() {
        return LEVEL_DURATION;
    }
}