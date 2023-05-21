package main.entities.fruits;

import main.entities.other.GameElement;

import java.awt.Rectangle;
import java.awt.Graphics;

/**
 * The Fruit abstract class represents a generic fruit in the game
 */
public abstract class Fruit extends GameElement {
    private final int pointValue;
    public Fruit(int x, int y, int width, int height, int pointValue) {
        super(x, y, width, height);
        this.pointValue = pointValue;
    }
    public int getPointValue() {
        return this.pointValue;
    }
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    @Override
    public void update() {
    }
    @Override
    public abstract void render(Graphics g);
}
