package main.entities.enemies;

import main.levels.Level;

import javax.imageio.ImageIO;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The ThermalTitan class represents a specific type of enemy in the game.
 */
public class ThermalTitan extends Enemy {
    private final Level level;
    private BufferedImage sprite;
    private final int heatRadius;

    public ThermalTitan(int x, int y, int width, int height, int speed, Level level, int heatRadius) {
        super(x, y, width, height, speed);
        this.level = level;
        this.heatRadius = heatRadius;
        this.loadTitanImage();
    }

    @Override
    protected void performUniqueActions() {
        this.emitHeatAura();
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

        // Perform any unique actions for this enemy
        this.performUniqueActions();

        // change direction every 4 seconds
        if (System.currentTimeMillis() - getLastChange() > 4000) {
            changeDirection();
        }
    }

    private void emitHeatAura() {
        if (this.level.getIceManager().isPresent()) {
            this.level.getIceManager().get().meltIceAround(getX(), getY(), this.heatRadius);
        }
    }

    private void loadTitanImage() {
        try {
            this.sprite = ImageIO.read(new File("res/thermalTitan.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.sprite, getX(), getY(), getWidth(), getHeight(), null);
    }
}