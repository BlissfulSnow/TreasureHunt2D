package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16 x 16 Tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48 x 48 Tile (New Size)

    //Ratio of Screen will be 4:3
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;

    // Screen Width will be 48 * 16 = 768 px
    public final int screenWidth = tileSize * maxScreenCol;

    //Screen Height will be 48 * 12 = 576 px
    public final int screenHeight = tileSize * maxScreenRow;

   //TODO World Settings
   public final int maxWorldCol = 50;
   public final int maxWorldRow = 50;

    //TODO Fps
    int FPS = 60;

    //TODO Instantiate Area

    // System
    TileManager tileManager = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);


    // Entity And Object
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];




    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        // Improves the game's rendering performance
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        assetSetter.setObject();
        playMusic(0);
    }

    //Starting the Game Thread
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Game Loop
    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                // 1. Update : Updating information such as character positions
                update();
                // 2. Draw : Drawing the screen with the updated information
                repaint();
                // Resetting the loop
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000){
                drawCount = 0;
                timer = 0;
            }
        }
    }

    //TODO Updates the Player's Movement After Key Is Pressed
    public void update() {
        player.update();
    }

    //TODO Draws the updated version of the game
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        // Tile
        tileManager.draw(g2);
        // Object
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }
        // Player
        player.draw(g2);
        ui.draw(g2);
        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSE(int i){
        se.setFile(i);
        se.play();
    }

}