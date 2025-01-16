import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class StartMenu extends JPanel implements ActionListener {
    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton exitButton;
    private GameEngine gameEngine;

    public StartMenu(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        newGameButton = new JButton("New Game");
        newGameButton.setBounds(350, 200, 100, 50);
        newGameButton.addActionListener(this);
        this.add(newGameButton);

        loadGameButton = new JButton("Load Game");
        loadGameButton.setBounds(350, 300, 100, 50);
        loadGameButton.addActionListener(this);
        this.add(loadGameButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(350, 400, 100, 50);
        exitButton.addActionListener(this);
        this.add(exitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            gameEngine.startGame();
        } else if (e.getSource() == loadGameButton) {
            gameEngine.loadGame();
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("Start Menu", 300, 100);
    }
}
