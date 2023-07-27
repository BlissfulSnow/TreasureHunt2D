package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // TODO Setting the Player's Hitbox
        solidArea = new Rectangle();
        solidArea.x = 16;
        solidArea.y = 15;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 16;
        solidArea.height = 20;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boyup1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boyup2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boydown1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boydown2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boyleft1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boyleft2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boyright1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/boyright2.png")));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
            if (keyH.upPressed){
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            }else if (keyH.rightPressed) {
                direction = "right";
            }

            // Checks Tile collision
            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            // Checks Object Collision
            int objIndex = gp.collisionChecker.checkObject(this, true);
            pickUpObject(objIndex);


            // If Collision is false, player can move
            if (!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }
            // If Collision is true,

            spriteCounter++;
            if (spriteCounter > 14){
                if (spriteNum == 1){
                    spriteNum = 2;
                }else if (spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

        }
    }

    public void pickUpObject(int index) {
        if (index != 999) {

            String objectName = gp.obj[index].name;

            switch (objectName){
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[index] = null;
                    gp.ui.showMessage("You Got A Key!");
                    break;
                case "Door":
                    if (hasKey > 0) {
                        gp.playSE(3);
                        gp.obj[index] = null;
                        gp.ui.showMessage("Door Opened!");
                        hasKey--;
                    }else {
                        System.out.println("Key's Required");
                    }
                    System.out.println("Keys : " + hasKey);
                    break;
                case "Boots":
                    gp.playSE(2);
                    speed += 2;
                    gp.obj[index] = null;
                    break;
                case "Chest":
                    gp.ui.gameEnd = true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {
//        g2.setColor(Color.white);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;
        switch (direction) {
            case "up" -> {
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
            }
            case "down" -> {
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
            }
            case "right" -> {
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
            }
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

    }
}
