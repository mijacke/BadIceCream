package main.entities.fruits;

import main.graphics.SpriteSheet;

import java.awt.Image;
import java.awt.Graphics;

/**
 * The Lemon class represents a specific type of fruit in the game.
 */
public class Lemon extends Fruit {
    private final Image sprite;
    public Lemon(int x, int y, int width, int height, SpriteSheet spriteSheet) {
        super(x, y, width, height, 15); // Assuming an apple is worth 15 points

        this.sprite = spriteSheet.getSprite(64, 124, 20, 20);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.sprite, getX(), getY(), getWidth(), getHeight(), null);
    }
}
