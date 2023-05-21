package main.core;

import main.controller.GameState;

import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Graphics;
import java.awt.Color;

/**
 * The GamePanel class is responsible for drawing the game state and updating the score and time labels.
 */
public class GamePanel extends JPanel {
    private final GameState gameState;
    private final JLabel scoreLabel;
    private final JLabel timeLabel;

    /**
     * GamePanel constructor for initiating score and time label
     *
     * @param gameState current gameState
     */
    public GamePanel(GameState gameState) {
        this.gameState = gameState;
        Timer repaintTimer = new Timer(16, e -> repaint());
        repaintTimer.start();
        this.scoreLabel = new JLabel();
        this.timeLabel = new JLabel();

        add(this.scoreLabel);
        add(this.timeLabel);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.gameState.render(g);

        // Update score and time labels
        this.scoreLabel.setForeground(Color.WHITE);
        this.scoreLabel.setText("Score: " + this.gameState.getPlayer().getPoints());
        long remainingTime = GameState.getLevelDuration() - (System.currentTimeMillis() - this.gameState.getLevelStartTime());

        long minutes = remainingTime / 60000; // 1 minute = 60000 milliseconds
        long seconds = (remainingTime % 60000) / 1000; // 1 second = 1000 milliseconds

        // Ensure that the time is always shown as two digits
        String timeString = String.format("%02d:%02d", minutes, seconds);

        this.timeLabel.setForeground(Color.WHITE);
        this.timeLabel.setText("Time: " + timeString);
    }
}