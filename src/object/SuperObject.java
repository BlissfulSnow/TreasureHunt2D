package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;


    public void draw(Graphics2D g2, GamePanel gp){
        int ScreenX = worldX - gp.player.worldX + gp.player.screenX;
        int ScreenY = worldY - gp.player.worldY + gp.player.screenY;

        // Rndering System To Improve Game Fluency
        // Remove gp.tileSize to check if render distance is working
        if (worldX + gp.tileSize> gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(image, ScreenX, ScreenY, gp.tileSize, gp.tileSize, null);
        }
    }
}