package main.core;

import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;

/**
 * The HelpDialog class displays a modal dialog with instructions for playing the game.
 */
public class HelpDialog extends JDialog {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JTextArea textArea = new JTextArea();

    /**
     * Constructor for creating game instructions
     *
     * @param parent the JFrame to which we want to add this Help Dialog
     */
    public HelpDialog(JFrame parent) {
        super(parent, "Help", true);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(parent);
        setResizable(false);

        this.textArea.setEditable(false);
        this.textArea.setForeground(Color.WHITE);
        this.textArea.setBackground(Color.BLACK);
        this.textArea.setLineWrap(true);
        this.textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(this.textArea);

        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(e -> setVisible(false));

        JLabel titleLabel = new JLabel("INSTRUCTIONS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BorderLayout());
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(continueButton, BorderLayout.SOUTH);

        setContentPane(panel);
        this.displayInstructions();
    }

    private void displayInstructions() {
        String instructions = """
                Control the player movement using 'W','A','S','D' keys.
                Press 'SPACE' to create ice blocks.
                Press 'SPACE' again to break ice blocks.
                Collect all types of fruits to proceed to the next level.
                Avoid enemies. Each enemy has its own behavior.
                Once all levels are completed, you can claim your throne!""";

        this.textArea.setText(instructions);
    }
}
