package main.controller;

import main.core.GameWindow;

import javax.swing.SwingUtilities;

/**
 * Main class for starting the Bad Ice Cream game.
 */
public class BadIceCream {
    /**
     * Main method for starting the game.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameWindow gameWindow = new GameWindow();
            gameWindow.setVisible(true);
        });
    }
}