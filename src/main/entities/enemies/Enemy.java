package main.entities.enemies;

import main.entities.other.GameElement;
import main.entities.other.Player;
import java.awt.Point;
import java.util.Random;

/**
 * The Enemy abstract class represents a generic enemy in the game.
 */
public abstract class Enemy extends GameElement {
    private final int speed;
    private int direction;
    private final Random random;
    private long lastChange;

    /**
     *
     * @param x the position on x-axis
     * @param y the position on y-axis
     * @param width the width of the enemy
     * @param height the height of the enemy
     * @param speed the speed of the enemy
     */
    public Enemy(int x, int y, int width, int height, int speed) {
        super(x, y, width, height);
        this.speed = speed;
        this.random = new Random();
        this.direction = this.random.nextInt(4); // random initial direction
        this.lastChange = System.currentTimeMillis(); // time of last direction change
    }
    protected Point calculateNewPosition() {
        int newX = getX();
        int newY = getY();

        // 0 = up, 1 = right, 2 = down, 3 = left
        switch (this.direction) {
            case 0:
                newY -= this.speed;
                break;
            case 1:
                newX += this.speed;
                break;
            case 2:
                newY += this.speed;
                break;
            case 3:
                newX -= this.speed;
                break;
        }

        return new Point(newX, newY);
    }

    public boolean hasCollidedWithPlayer(Player player) {
        if (player != null) {
            return this.getX() < player.getX() + player.getWidth() &&
                    this.getX() + this.getWidth() > player.getX() &&
                    this.getY() < player.getY() + player.getHeight() &&
                    this.getY() + this.getHeight() > player.getY();
        }
        return false;
    }

    protected void changeDirection() {
        this.direction = this.random.nextInt(4);
        this.lastChange = System.currentTimeMillis();
    }

    public abstract void update();

    protected abstract void performUniqueActions();

    public int getDirection() {
        return this.direction;
    }

    public long getLastChange() {
        return this.lastChange;
    }
}