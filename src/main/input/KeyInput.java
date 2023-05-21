package main.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * The KeyInput class handles keyboard input by implementing the KeyListener interface.
 */
public class KeyInput implements KeyListener {
    private final boolean[] keys = new boolean[256];
    private boolean spacePressed = false;
    public boolean isKeyDown(int keyCode) {
        return this.keys[keyCode];
    }
    public boolean isKeyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_SPACE && this.spacePressed) {
            this.spacePressed = false;
            return true;
        }
        return false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.keys[e.getKeyCode()] = true;

        if (e.getKeyCode() == KeyEvent.VK_SPACE && !this.spacePressed) {
            this.spacePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.keys[e.getKeyCode()] = false;

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            this.spacePressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used in this implementation
    }
}
