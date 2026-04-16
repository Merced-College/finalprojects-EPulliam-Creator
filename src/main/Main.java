package main;

import javax.swing.JFrame;
import game.GamePanel;

public class Main {

    public static void main(String[] args) {

        // Create the main game window
        JFrame frame = new JFrame("Sonic-Style Engine");

        // Create the game panel (where all game logic/rendering happens)
        GamePanel gamePanel = new GamePanel();

        // Ensures the program fully closes when window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Prevent resizing to keep rendering consistent
        frame.setResizable(false);

        // Add game panel and automatically size window to match it
        frame.add(gamePanel);
        frame.pack();

        // Center window on screen
        frame.setLocationRelativeTo(null);

        // Make window visible (must happen before game starts)
        frame.setVisible(true);

        // Start the game loop thread
        gamePanel.startGame();
    }
}