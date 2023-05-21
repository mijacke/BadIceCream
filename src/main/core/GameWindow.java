package main.core;

import main.controller.GameState;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;

/**
 * Main game window for the Bad IceCream game
 */
public class GameWindow extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final String TITLE = "Bad IceCream";
    private static final String LICK_BUTTON_LABEL = "CLICK TO LICK";
    private static final String BACKGROUND_IMAGE_PATH = "res/background.png";

    private BackgroundPanel backgroundPanel;
    private JPanel overlayPanel;
    private JButton lickButton;
    private JPanel miniMenu;
    private GameState gameState;
    private GamePanel gamePanel;

    /**
     * Constructor for GameWindow
     */
    public GameWindow() {
        this.setupWindow();
        this.setupBackgroundImage();
        this.setupOverlayPanel();
        this.setupLickButton();
        this.setupMiniMenu();
        this.setupGamePanel();
        pack();
        setLocationRelativeTo(null);
    }

    private void setupWindow() {
        setTitle(TITLE);
        setSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void setupBackgroundImage() {
        BufferedImage backgroundImage = loadImage();
        this.backgroundPanel = new BackgroundPanel(backgroundImage);
        this.backgroundPanel.setLayout(new BorderLayout());
        this.backgroundPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        getContentPane().add(this.backgroundPanel, BorderLayout.CENTER);
    }

    private void setupOverlayPanel() {
        this.overlayPanel = createPanelWithBoxLayout();
        JLabel welcomeLabel = createCenteredLabel();
        this.overlayPanel.add(Box.createVerticalGlue());
        this.overlayPanel.add(welcomeLabel);
        this.overlayPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        this.backgroundPanel.add(this.overlayPanel, BorderLayout.CENTER);
    }

    private void setupLickButton() {
        this.lickButton = new JButton(LICK_BUTTON_LABEL);
        this.lickButton.addActionListener(e -> this.handleLickButtonClick());
        this.lickButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.overlayPanel.add(this.lickButton);
        this.overlayPanel.add(Box.createVerticalGlue());
    }

    private void setupMiniMenu() {
        this.miniMenu = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Create a FlowLayout for side by side buttons
        this.miniMenu.setOpaque(false);
        JButton playButton = new JButton("Play");
        JButton helpButton = new JButton("Help");
        playButton.addActionListener(e -> this.handlePlayButtonClick());
        helpButton.addActionListener(e -> new HelpDialog(GameWindow.this).setVisible(true));
        this.miniMenu.add(playButton);
        this.miniMenu.add(helpButton);
        this.miniMenu.setVisible(false);
        this.overlayPanel.add(this.miniMenu);
    }
    private void setupGamePanel() {
        this.gameState = new GameState(WIDTH, HEIGHT);
        this.gamePanel = new GamePanel(this.gameState);
        this.gamePanel.setOpaque(false);
        this.gamePanel.addKeyListener(this.gameState.getInputHandler().keyInput());
        this.gamePanel.setFocusable(true);
    }

    private void handleLickButtonClick() {
        this.lickButton.setVisible(false);
        this.miniMenu.setVisible(true);
    }

    private void handlePlayButtonClick() {
        this.backgroundPanel.remove(this.overlayPanel);
        this.gamePanel.setVisible(true);
        this.backgroundPanel.add(this.gamePanel, BorderLayout.CENTER);
        new HelpDialog(GameWindow.this).setVisible(true);
        this.gameState.startGameLoop();
        this.backgroundPanel.revalidate();
        this.backgroundPanel.repaint();
    }

    private static BufferedImage loadImage() {
        try {
            return ImageIO.read(new File(GameWindow.BACKGROUND_IMAGE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static JPanel createPanelWithBoxLayout() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        return panel;
    }

    private static JLabel createCenteredLabel() {
        JLabel label = new JLabel(GameWindow.TITLE, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private static class BackgroundPanel extends JPanel {
        private final BufferedImage backgroundImage;

        BackgroundPanel(BufferedImage backgroundImage) {
            this.backgroundImage = backgroundImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(this.backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
    }
}