package main.entities.other;

import java.awt.Graphics;

/**
 * This is a base class for all game elements such as player, enemies and ice blocks. It provides
 * methods that are common to these elements.
 */
public abstract class GameElement {
    private int x;
    private int y;

    private int width;
    private int height;

    public GameElement(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public abstract void update();
    public abstract void render(Graphics g);

    /**
     * Getters and setters below
     *
     */
    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}