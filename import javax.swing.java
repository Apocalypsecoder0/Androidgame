import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GameEngine extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private boolean running;
    private Player player;

    public GameEngine() {
        this.setPreferredSize(new Dimension(800, 600)); // Set window size
        this.setBackground(Color.BLACK); // Set background color
        this.setFocusable(true); // Allow keyboard focus
        this.addKeyListener(this); // Add key listener
        running = true;
        timer = new Timer(16, this); // 60 FPS
        timer.start();
        player = new Player();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            player.update();
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.render(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        handleKeyPress(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Handle key release if necessary
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Handle key typed if necessary
    }

    public void handleKeyPress(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_RIGHT:
                player.setVelocity(0, player.getVelocityY());
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                player.setVelocity(player.getVelocityX(), 0);
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game Engine");
        GameEngine gameEngine = new GameEngine();
        frame.add(gameEngine);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class Player {
    private int x, y;
    private int velocityX, velocityY;

    public Player() {
        x = 100;
        y = 100;
        velocityX = 0;
        velocityY = 0;
    }

    public void update() {
        x += velocityX;
        y += velocityY;
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, 50, 50);
    }

    public void setVelocity(int velocityX, int velocityY) {
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }
}

interface Scene {
    void update();
    void render(Graphics g);
}

public class ResourceManager {
    private HashMap<String, BufferedImage> images;

    public ResourceManager() {
        images = new HashMap<>();
    }

    public void loadImage(String name, String path) {
        try {
            BufferedImage image = ImageIO.read(getClass().getResource(path));
            images.put(name, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage(String name) {
        return images.get(name);
    }
}

public class SceneManager {
    private HashMap<String, Scene> scenes;
    private Scene currentScene;

    public SceneManager() {
        scenes = new HashMap<>();
    }

    public void addScene(String name, Scene scene) {
        scenes.put(name, scene);
    }

    public void setCurrentScene(String name) {
        currentScene = scenes.get(name);
    }

    public void update() {
        if (currentScene != null) {
            currentScene.update();
        }
    }

    public void render(Graphics g) {
        if (currentScene != null) {
            currentScene.render(g);
        }
    }
}

public class SoundManager {
    public static void playSound(String soundFile) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(SoundManager.class.getResource(soundFile));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}