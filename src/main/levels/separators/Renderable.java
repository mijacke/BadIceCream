package main.levels.separators;

import java.awt.Graphics;

/**
 * The Renderable interface represents an object that can be rendered on the screen.
 * Classes that implement this interface should provide an implementation for the render method
 * which specifies how the object should be rendered using the provided Graphics object.
 */
public interface Renderable {
    /**
     * Renders the object on the screen using the provided Graphics object.
     *
     * @param g the Graphics object to use for rendering
     */
    void render(Graphics g);
}