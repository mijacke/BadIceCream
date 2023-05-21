package main.entities.enemies;

import main.levels.Level;

import javax.imageio.ImageIO;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The Guard class represents a specific type of enemy in the game.
 */
public class Guard extends Enemy {

    private final Level level;
    private BufferedImage sprite;

    public Guard(int x, int y, int width, int height, int speed, Level level) {
        super(x, y, width, height, speed);
        this.level = level;
        this.loadGuardImage();
    }

    @Override
    public void update() {
        Point newPosition = calculateNewPosition();

        if (this.level.canMoveTo(newPosition.x, newPosition.y, getWidth(), getHeight())) {
            setX(newPosition.x);
            setY(newPosition.y);
        } else {
            changeDirection();
        }
        this.performUniqueActions();

        // change direction every 3 seconds
        if (System.currentTimeMillis() - getLastChange() > 3000) {
            changeDirection();
        }
    }

    @Override
    protected void performUniqueActions() {
        // The Guard class does not perform any unique actions,
    }

    private void loadGuardImage() {
        try {
            this.sprite = ImageIO.read(new File("res/guard.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.sprite, getX(), getY(), getWidth(), getHeight(), null);
    }
}