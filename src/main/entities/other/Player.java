package main.entities.other;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import main.input.KeyInput;
import main.levels.Level;
import javax.imageio.ImageIO;

/**
 * Class representing the Player entity in the game.
 * It includes logic for player movement, handling ice creation and breaking, and managing player points.
 */
public class Player extends GameElement {
    private final int speed;
    private final PlayerInputHandler inputHandler;
    private final KeyInput keyInput;
    private Level level;
    private BufferedImage sprite;
    private int points;
    private int targetX;
    private int targetY;

    /**
     * Constructs a Player object.
     *
     * @param x             The initial X-coordinate of the player.
     * @param y             The initial Y-coordinate of the player.
     * @param width         The width of the player.
     * @param height        The height of the player.
     * @param inputHandler  The input handler for player movement.
     * @param level         The level in which the player exists.
     */
    public Player(int x, int y, int width, int height, PlayerInputHandler inputHandler, Level level) {
        super(x, y, width, height);
        this.speed = 5;
        this.inputHandler = inputHandler;
        this.keyInput = this.inputHandler.keyInput();
        this.level = level;
        this.points = 0;
        this.loadPlayerImage();

        this.targetX = x;
        this.targetY = y;
    }

    private void playerMovement() {
        // If the player is at the target cell, update target cell
        if (getX() == this.targetX && getY() == this.targetY) {
            int potentialTargetX = this.targetX;
            int potentialTargetY = this.targetY;

            if (this.inputHandler.isMoveUpPressed()) {
                potentialTargetY -= this.level.getCellHeight();
            } else if (this.inputHandler.isMoveDownPressed()) {
                potentialTargetY += this.level.getCellHeight();
            } else if (this.inputHandler.isMoveLeftPressed()) {
                potentialTargetX -= this.level.getCellWidth();
            } else if (this.inputHandler.isMoveRightPressed()) {
                potentialTargetX += this.level.getCellWidth();
            }

            if (this.level.canMoveTo(potentialTargetX, potentialTargetY, getWidth(), getHeight())) {
                this.targetX = potentialTargetX;
                this.targetY = potentialTargetY;
            }
        }
        // Move the player towards the target cell
        if (getX() < this.targetX) {
            setX(Math.min(getX() + this.speed, this.targetX));
        } else if (getX() > this.targetX) {
            setX(Math.max(getX() - this.speed, this.targetX));
        }

        if (getY() < this.targetY) {
            setY(Math.min(getY() + this.speed, this.targetY));
        } else if (getY() > this.targetY) {
            setY(Math.max(getY() - this.speed, this.targetY));
        }
    }

    private Point computeIceBlockInitialPosition() {
        int iceBlockX = getX() / this.level.getCellWidth();
        int iceBlockY = getY() / this.level.getCellHeight();
        return new Point(iceBlockX, iceBlockY);
    }

    private void adjustIceBlockPositionForBreaking(Direction direction, Point iceBlockPosition) {
        switch (direction) {
            case UP -> iceBlockPosition.y--;
            case DOWN -> iceBlockPosition.y++;
            case LEFT -> iceBlockPosition.x--;
            case RIGHT -> iceBlockPosition.x++;
        }
    }

    private int computeLookAhead() {
        if (this.speed < 11) {
            return 2;
        } else {
            return this.speed < 21 ? 3 : 4; // 4 is extreme - other speeds will not fit our game
        }
    }
    private void handleIceCreationAndBreaking() {
        if (!this.keyInput.isKeyPressed(KeyEvent.VK_SPACE)) {
            return;
        }
        Direction direction = this.inputHandler.getFacingDirection();

        // Compute the ice block's initial position. ALso adjust the ice block position for breaking the ice.
        Point iceBlockPosition = this.computeIceBlockInitialPosition();
        this.adjustIceBlockPositionForBreaking(direction, iceBlockPosition);

        if (this.level.getIceManager().isPresent()) {
            if (this.level.hasIceBlock(iceBlockPosition.x, iceBlockPosition.y)) {
                this.level.getIceManager().get().breakIceInDirection(iceBlockPosition.x, iceBlockPosition.y, direction);
                return;  // return after breaking ice
            }
        }

        // Compute lookahead only for creating the ice.
        int lookahead = this.computeLookAhead();
        iceBlockPosition = this.computeIceBlockInitialPosition();

        switch (direction) {
            case UP:
                iceBlockPosition.y -= lookahead;
                break;
            case DOWN:
                iceBlockPosition.y += lookahead;
                break;
            case LEFT:
                iceBlockPosition.x -= lookahead;
                break;
            case RIGHT:
                iceBlockPosition.x += lookahead;
                break;
        }

        if (this.level.getIceManager().isPresent() && !this.level.hasIceBlock(iceBlockPosition.x, iceBlockPosition.y)) {
            this.level.getIceManager().get().createIceInDirection(iceBlockPosition.x, iceBlockPosition.y, direction);
        }
    }

    private void resetTargetLocation() {
        this.targetX = getX();
        this.targetY = getY();
    }

    /**
     * Updates the player's position to the given new position.
     *
     * @param newPosition The new position of the player.
     */
    public void updatePosition(Point newPosition) {
        setX(newPosition.x * this.level.getCellWidth());
        setY(newPosition.y * this.level.getCellHeight());
        this.resetTargetLocation();
    }
    private void loadPlayerImage() {
        try {
            this.sprite = ImageIO.read(new File("res/pink_iceCream.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPoints(int points) {
        this.points += points;
    }

    @Override
    public void update() {
        this.playerMovement();
        this.handleIceCreationAndBreaking();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.sprite, getX(), getY(), getWidth(), getHeight(), null);
    }

    /**
     * Retrieves the bounding rectangle of the player.
     *
     * @return The bounding rectangle.
     */
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Retrieves the current points of the player.
     *
     * @return The points.
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * Sets the points of the player.
     *
     * @param points The points to be set.
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Sets the level for the player.
     *
     * @param level The level to be set.
     */
    public void setLevel(Level level) {
        this.level = level;
    }
}
