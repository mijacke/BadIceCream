package main.entities.fruits;

import main.graphics.SpriteSheet;

import java.awt.Image;
import java.awt.Graphics;

public class Orange extends Fruit {
    private final Image sprite;
    public Orange(int x, int y, int width, int height, SpriteSheet spriteSheet) {
        super(x, y, width, height, 20); // Assuming an apple is worth 20 points

        this.sprite = spriteSheet.getSprite(37, 124, 20, 20);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.sprite, getX(), getY(), getWidth(), getHeight(), null);
    }
}
