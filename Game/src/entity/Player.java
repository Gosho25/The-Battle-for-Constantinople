package entity;

import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Player extends Entity{
    //when extended, this provides the problem with the constructor

    //variables for implementing the player into the game environment (logic, movements and so on ...)
    public GamePanel gp;
    public KeyHandler keyH;

    //putting the variables into the Player constructor and setting the position and loading the images
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();//method for the position
        getPlayerImage();//method for the images
    }

    //this method
    public void getPlayerImage() {
        //we try to properly load the images
        try {
            //here, we load .png images from /player resource directory and stores it in the variables
            //we use the class ImageIO for reading and storing images
            // .getResourceAsStream() returns an InputStream(abstract class)
            // .read() takes the stream and reads image data
            //it returns BufferedImage
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/1_U.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/2_U.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/1_D.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/2_D.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/1_L.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/2_L.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/1_R.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/2_R.png"));
        } catch (IOException e) {//if one of the images is not correctly loaded(for some reason) we throw an error
            System.out.println("Not correctly loaded image");
            //.printStackTrace() prints detailed info about the error in the console
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        //Working with Cartesian coordinate system
        //the top left on the screen is (x:0, y:0)
        //we set variables for spawn position on the player and his speed(in pixels)
        x = 100;
        y = 100;
        speed = 4;
        //Player(the class) extends class Entity, from which one we use his direction variable
        //that has different cases, but we CHOOSE down case
        direction = "down";
    }

    public void update() {
        //We created a object from KeyHandler
        //Based on this class logic(which button does what action),
        //if it is pressed, switch direction and coordinates
        if (keyH.upPressed == true) {//Not a good practice:we specify that is pressed
            direction = "up";
            y -= speed;
        } else if (keyH.downPressed) {
            direction = "down";
            y += speed;
        } else if (keyH.leftPressed) {
            direction = "left";
            x -= speed;
        } else if (keyH.rightPressed) {
            direction = "right";
            x += speed;
        }


        //this variable determines the speed between switching the images
        //we increase it
        spriteCounter++;
        if (spriteCounter > 20) {
            spriteNum = (spriteNum == 1) ? 2 : 1; //ternal operator
            //Toggle between sprite 1 and 2 (if it's 1, become 2; if 2, become 1)
            // condition ? if its true : if its false

            spriteCounter = 0;
            // Reset the counter to start the next cycle
        }
    }
    public void draw(Graphics2D g2) {
        super.draw(g2, gp.tileSize);
        //super calls the Entity draw() method(the parent class)
        //Graphics2D g2 object for drawing the image
    }
}