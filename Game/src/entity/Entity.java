package entity;

import java.awt.*;
import java.awt.image.BufferedImage;


//ГЛАВЕН КЛАС ОТ ПАКЕТА, ОТ КОЙТО СЕ ИЗПОЛЗВАТ ОСНОВНИ ХАРАКТЕРИСТИКИ НА ВСЕКИ ЖИВ ОБЕКТ В ИГРАТА
public class Entity {
    //Всеки клас който го наследи ще използва дадените променливи:
    public int x,y;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;

    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public void draw(Graphics2D g2, int titleSize) {
        BufferedImage image = null;

        switch(direction) {
            case "up":
                image = (spriteNum == 1) ? up1 : up2;
                break;
            case "down":
                image = (spriteNum == 1) ? down1 : down2;
                break;
            case "left":
                image = (spriteNum == 1) ? left1 : left2;
                break;
            case "right":
                image = (spriteNum == 1) ? right1 : right2;
                break;
        }

        g2.drawImage(image, x, y, titleSize, titleSize, null);
    }

}