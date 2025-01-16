import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MenuSystem extends JPanel implements ActionListener {
    private JButton startButton;
    private JButton exitButton;
    private JButton optionsButton;
    private JButton settingsButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton continueButton;
    private GameEngine gameEngine;

    public MenuSystem(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        startButton = new JButton("Start Game");
        startButton.setBounds(350, 150, 100, 50);
        startButton.addActionListener(this);
        this.add(startButton);

        optionsButton = new JButton("Options");
        optionsButton.setBounds(350, 210, 100, 50);
        optionsButton.addActionListener(this);
        this.add(optionsButton);

        settingsButton = new JButton("Settings");
        settingsButton.setBounds(350, 270, 100, 50);
        settingsButton.addActionListener(this);
        this.add(settingsButton);

        saveButton = new JButton("Save Game");
        saveButton.setBounds(350, 330, 100, 50);
        saveButton.addActionListener(this);
        this.add(saveButton);

        loadButton = new JButton("Load Game");
        loadButton.setBounds(350, 390, 100, 50);
        loadButton.addActionListener(this);
        this.add(loadButton);

        continueButton = new JButton("Continue");
        continueButton.setBounds(350, 450, 100, 50);
        continueButton.addActionListener(this);
        this.add(continueButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(350, 510, 100, 50);
        exitButton.addActionListener(this);
        this.add(exitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            gameEngine.startGame();
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        } else if (e.getSource() == optionsButton) {
            gameEngine.showOptions();
        } else if (e.getSource() == settingsButton) {
            gameEngine.showSettings();
        } else if (e.getSource() == saveButton) {
            gameEngine.saveGame();
        } else if (e.getSource() == loadButton) {
            gameEngine.loadGame();
        } else if (e.getSource() == continueButton) {
            gameEngine.continueGame();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("Game Menu", 300, 100);
    }
}
