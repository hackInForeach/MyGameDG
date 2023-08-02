package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
	// bu sınıf diğer tüm varlıkların üst sınıfıdır

	public int worldX, worldY;
	public int speed;

	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction;

	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	//bu sınıf ile görünmez veya sayut bir dikdörtgen oluşturabiliriz.
	public Rectangle solidArea;
	
	//bu sınıf değişkenlerini oyuncu için belli şartlara göre sağlam karunun sağlam olmaması
	public int solidAreaDefaultX,solidAreaDefaultY;
	
	public boolean collisionOn=false;
}
