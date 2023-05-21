package main.entities.enemies;

import main.levels.Level;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The Yeti class represents a specific type of enemy in the game.
 */
public class Yeti extends Enemy {

    private final int speed;
    private int direction = 0; // Initial is right
    private final Level level;
    private BufferedImage sprite;
    public Yeti(int x, int y, int width, int height, int speed, Level level) {
        super(x, y, width, height, speed);
        this.speed = speed;
        this.level = level;
        this.loadGuardImage();
    }

    @Override
    public void update() {
        int newX = getX();
        int newY = getY();

        // 0 = right, 1 = up, 2 = left, 3 = down
        switch (this.direction) {
            case 0:
                newX += this.speed;
                break;
            case 1:
                newY -= this.speed;
                break;
            case 2:
                newX -= this.speed;
                break;
            case 3:
                newY += this.speed;
                break;
        }

        if (this.level.canMoveTo(newX, newY, getWidth(), getHeight())) {
            // Path is clear, move to new position
            setX(newX);
            setY(newY);
        } else {
            // Path is blocked, turn right (counterclockwise in our direction system)
            this.direction = (this.direction + 3) % 4;
        }
    }

    @Override
    protected void performUniqueActions() {
    }

    private void loadGuardImage() {
        try {
            this.sprite = ImageIO.read(new File("res/yeti.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.sprite, getX(), getY(), getWidth(), getHeight(), null);
    }
}