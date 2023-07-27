package main;

import object.Obj_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class UI {
    GamePanel gp;
    Font arial_40;
    Font arial_45B;
    BufferedImage keyImage;

    // Messages
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    // End
    public boolean gameEnd = false;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_45B = new Font("Arial", Font.BOLD, 45);
        Obj_Key obj_key = new Obj_Key();
        keyImage = obj_key.image;

    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2){

        if (gameEnd){
            g2.setFont(arial_45B);
            g2.setColor(Color.YELLOW);
            int x;
            int y;

            String text = "Treasure Found!";
            int textLength = (int) g2.getFontMetrics().getStringBounds(message, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize*3);
            g2.drawString(text, x, y );

            gp.stopMusic();
            gp.gameThread = null;
        }else {

            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.hasKey, gp.tileSize * 2, gp.tileSize + 15);

            g2.drawString("FPS : " + gp.FPS, gp.screenWidth - gp.tileSize * 5, gp.tileSize + 15);



            // Message
            if (messageOn) {
                g2.setFont(g2.getFont().deriveFont(30F));
                int textLength = (int) g2.getFontMetrics().getStringBounds(message, g2).getWidth();
                g2.drawString(message, gp.screenWidth / 2 - textLength / 2, gp.tileSize * 5);

                messageCounter++;

                if (messageCounter > 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }

    }
}
