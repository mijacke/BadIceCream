package main.entities.fruits;

import main.graphics.SpriteSheet;

import java.awt.Image;
import java.awt.Graphics;

/**
 * The Banana class represents a specific type of fruit in the game.
 */
public class Banana extends Fruit {
    private final Image sprite;
    public Banana(int x, int y, int width, int height, SpriteSheet spriteSheet) {
        super(x, y, width, height, 5); // Assuming a banana is worth 5 points

        this.sprite = spriteSheet.getSprite(175, 94, 20, 20);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.sprite, getX(), getY(), getWidth(), getHeight(), null);
    }
}
