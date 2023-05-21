package main.entities.enemies;

import main.levels.Level;

import javax.imageio.ImageIO;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The Ghost class represents a specific type of enemy in the game.
 */
public class Ghost extends Enemy {

    private final Level level;
    private BufferedImage sprite;

    public Ghost(int x, int y, int width, int height, int speed, Level level) {
        super(x, y, width, height, speed);
        this.level = level;
        this.loadGhostImage();
    }

    @Override
    public void update() {
        Point newPosition = calculateNewPosition();

        if (this.level.canEnemyMoveTo(newPosition.x, newPosition.y, getWidth(), getHeight())) {
            setX(newPosition.x);
            setY(newPosition.y);
        } else {
            changeDirection();
        }
        this.performUniqueActions();

        // change direction every 5 seconds
        if (System.currentTimeMillis() - getLastChange() > 5000) {
            changeDirection();
        }
    }

    @Override
    protected void performUniqueActions() {
        // The Ghost class does not perform any unique actions,
    }

    private void loadGhostImage() {
        try {
            this.sprite = ImageIO.read(new File("res/ghost.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.sprite, getX(), getY(), getWidth(), getHeight(), null);
    }
}