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
import java.util.ArrayList;
import java.util.List;

public class GameEngine extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private boolean running;
    private Player player;
    private MenuSystem menuSystem;
    private boolean inMenu;

    private OptionsMenu optionsMenu;
    private SettingsMenu settingsMenu;
    private boolean showingOptions;
    private boolean showingSettings;
    private StartMenu startMenu;
    private Universe universe;
    private List<Technology> technologies;
    private List<Resource> resources;
    private MenuGUI menuGUI;
    private boolean isTurnBased;
    private boolean playerTurn;

    public GameEngine() {
        this.setPreferredSize(new Dimension(800, 600)); // Set window size
        this.setBackground(Color.BLACK); // Set background color
        this.setFocusable(true); // Allow keyboard focus
        this.addKeyListener(this); // Add key listener
        running = true;
        timer = new Timer(16, this); // 60 FPS
        timer.start();
        player = new Player();
        menuSystem = new MenuSystem(this);
        optionsMenu = new OptionsMenu(this);
        settingsMenu = new SettingsMenu(this);
        startMenu = new StartMenu(this);
        menuGUI = new MenuGUI(this);
        inMenu = true;
        showingOptions = false;
        showingSettings = false;
        universe = new Universe();
        technologies = new ArrayList<>();
        resources = new ArrayList<>();
        initializeUniverse();
        initializeTechnologies();
        initializeResources();
        isTurnBased = false; // Set to true for turn-based mode
        playerTurn = true;
    }

    private void initializeUniverse() {
        Galaxy milkyWay = new Galaxy("Milky Way");
        System solarSystem = new System("Solar System");
        solarSystem.addPlanet(new Planet("Earth", 12742));
        solarSystem.addPlanet(new Planet("Mars", 6779));
        milkyWay.addSystem(solarSystem);
        universe.addGalaxy(milkyWay);
    }

    private void initializeTechnologies() {
        Technology basicMining = new Technology("Basic Mining", "Allows basic mining operations.");
        Technology advancedMining = new Technology("Advanced Mining", "Allows advanced mining operations.");
        advancedMining.addPrerequisite(basicMining);
        technologies.add(basicMining);
        technologies.add(advancedMining);
    }

    private void initializeResources() {
        resources.add(new Resource("Iron", 1000));
        resources.add(new Resource("Gold", 500));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running && !inMenu && !showingOptions && !showingSettings) {
            if (isTurnBased) {
                if (playerTurn) {
                    // Player's turn logic
                    player.update();
                    playerTurn = false;
                } else {
                    // AI's turn logic
                    // Update AI here
                    playerTurn = true;
                }
            } else {
                // Real-time logic
                player.update();
            }
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inMenu) {
            menuGUI.paintComponent(g);
        } else if (showingOptions) {
            optionsMenu.paintComponent(g);
        } else if (showingSettings) {
            settingsMenu.paintComponent(g);
        } else {
            player.render(g);
            renderUniverse(g);
            renderTechnologies(g);
            renderResources(g);
            renderGameMode(g);
        }
    }

    private void renderUniverse(Graphics g) {
        g.setColor(Color.WHITE);
        int x = 50;
        int y = 50;
        for (Galaxy galaxy : universe.getGalaxies()) {
            g.drawString("Galaxy: " + galaxy.getName(), x, y);
            y += 20;
            for (System system : galaxy.getSystems()) {
                g.drawString("  System: " + system.getName(), x + 20, y);
                y += 20;
                for (Planet planet : system.getPlanets()) {
                    g.drawString("    Planet: " + planet.getName() + " (Size: " + planet.getSize() + " km)", x + 40, y);
                    y += 20;
                }
            }
        }
    }

    private void renderTechnologies(Graphics g) {
        g.setColor(Color.WHITE);
        int x = 600;
        int y = 50;
        g.drawString("Technologies:", x, y);
        y += 20;
        for (Technology tech : technologies) {
            g.drawString(tech.getName() + ": " + tech.getDescription(), x, y);
            y += 20;
        }
    }

    private void renderResources(Graphics g) {
        g.setColor(Color.WHITE);
        int x = 600;
        int y = 200;
        g.drawString("Resources:", x, y);
        y += 20;
        for (Resource resource : resources) {
            g.drawString(resource.getName() + ": " + resource.getQuantity(), x, y);
            y += 20;
        }
    }

    private void renderGameMode(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Game Mode: " + (isTurnBased ? "Turn-Based" : "Real-Time"), 600, 400);
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
                player.setVelocity(5, player.getVelocityY());
                break;
            case KeyEvent.VK_LEFT:
                player.setVelocity(-5, player.getVelocityY());
                break;
            case KeyEvent.VK_UP:
                player.setVelocity(player.getVelocityX(), -5);
                break;
            case KeyEvent.VK_DOWN:
                player.setVelocity(player.getVelocityX(), 5);
                break;
            default:
                break;
        }
    }

    public void startGame() {
        inMenu = false;
        showingOptions = false;
        showingSettings = false;
        this.requestFocus();
    }

    public void showMenu() {
        inMenu = true;
        showingOptions = false;
        showingSettings = false;
        this.requestFocus();
    }

    public void showOptions() {
        inMenu = false;
        showingOptions = true;
        showingSettings = false;
        this.requestFocus();
    }

    public void showSettings() {
        inMenu = false;
        showingOptions = false;
        showingSettings = true;
        this.requestFocus();
    }

    public void saveGame() {
        SaveLoadManager.saveGame(player);
    }

    public void loadGame() {
        Player loadedPlayer = SaveLoadManager.loadGame();
        if (loadedPlayer != null) {
            player = loadedPlayer;
            startGame();
        }
    }

    public void continueGame() {
        inMenu = false;
        showingOptions = false;
        showingSettings = false;
        this.requestFocus();
    }

    public void showStartMenu() {
        inMenu = true;
        showingOptions = false;
        showingSettings = false;
        this.requestFocus();
    }

    public void toggleGameMode() {
        isTurnBased = !isTurnBased;
        playerTurn = true;
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

class Player implements Serializable {
    private static final long serialVersionUID = 1L;
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