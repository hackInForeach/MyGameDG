package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

	GamePanel gamePanel;
	KeyHandler keyHandler;

	// bunlar son değişkendir. yani oyuncu ekrandaki yeri değişmez harita kayar
	public final int screenX;
	public final int screenY;
	
	//oyuncu sahip olduğu anahtar sayısı
	public int hasKey = 0;
	
	
	public Player(GamePanel gamePanel, KeyHandler keyHandler) {
		this.gamePanel = gamePanel;
		this.keyHandler = keyHandler;

		// ekranın orta noktasının döndürür
		screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
		screenY = gamePanel.screenHight / 2 - (gamePanel.tileSize / 2);
		
		//Rectangle sınıfını başlat
		//böylelille constructor e 4 parametre verebiliriz:
		//örn: x,y,width, height 
		//karo lar ile çarpışayı önlemk için kullanıcının bir kısmını sağlam yaptık
		solidArea = new Rectangle();
		solidArea.x=8;
		solidArea.y=16;
		
		solidAreaDefaultX=solidArea.x;
		solidAreaDefaultY=solidArea.y;
		
		solidArea.width=32;
		solidArea.height=32;
	
		
		setDefaultValues();// varsayılan değerler ayarlandı
		getPlayerImage();// imageler çağrıldı
	}

	// burada oyuncu varsayılanlarını ayarlayacağız
	public void setDefaultValues() {

		worldX = gamePanel.tileSize * 23;
		worldY = gamePanel.tileSize * 21;
		speed = 3;
		direction = "down";

	}

	public void getPlayerImage() {
		// görüntüler yüklenece res klasöründen
		// bu oyuncumuzun hal ve hareketleri :D
		try {
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));

		} catch (IOException exception) {
			System.err.println("Error: " + exception.getMessage());
		}

	}

	// player güncelle & çizim:
	public void update() {
		// fixed: tuşa basmadan hareket etmemesini sağlamak
		if (keyHandler.upPressed == true | keyHandler.downPressed == true | keyHandler.leftPressed == true
				| keyHandler.rightPressed == true) {
			// oyuncu pozisyon güncellememizi burada yapcağız:
			if (keyHandler.upPressed == true) {
				// eğer ileri basıyorsa oyuncunun y ekseninden speed değerde çıkar
				// bu bize oyuncunun ileri gitmesini sağlar yani
				// pc grafik kordinatlarında Y ekseni ekranın üstünden başlar yani orayı 0
				// kabul edersek aşağı yönlü artış la beraber
				// y ekseni tam tersi olarak karşımıza çıkıyor

				// image için ekleme yapıyoruz
				direction = "up";
				
			} else if (keyHandler.downPressed == true) {
				direction = "down";
				
			} else if (keyHandler.leftPressed == true) {
				// x ekseninde sıkıntı yok :D
				direction = "left";
				
			} else if (keyHandler.rightPressed == true) {
				direction = "right";

			}
			
			//CHECK TILE COLLISION
			collisionOn= false;
			gamePanel.collisionChecker.checkTile(this);
			
			//CHECK OBJECT COLLISION
			int objectIndex = gamePanel.collisionChecker.checkObject(this, true);
			pickUpObject(objectIndex);

			//IF COLLISION IS FALSE, PLAYER CAN MOVE
			//oyuncu hareketlerini buraya taşıyoruz
			//çarpışma kontrolü yaptığımızdan oyuncu sadece sağlam olmayan yerlerden geçebilir
			if (collisionOn==false) {
				switch (direction) {
				case "up":   worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right":worldX += speed; break;
			}
			
			
			// hareket etmesi sağlandı
			spriteCounter++;
			if (spriteCounter > 10) {
				if (spriteNum == 1)
					spriteNum = 2;
				else if (spriteNum == 2)
					spriteNum = 1;
				spriteCounter = 0;
			}
		}
	}
}
	//onjelerin tepkileri tanımlanır
	public void pickUpObject(int index) {
		//index 999 ise herhangi bir nesneye dokunmadık
		// 999 ise ozaman bir nesneye çarptık
		if(index != 999) {
			//bu konuda birşey yapabşlmek içise:(basit olarak)
			//gamePanel.superObje[index]=null;//dokunduğumuz nesneyi siler
			
			//nesnenın adını alınıyor
			String objectName = gamePanel.superObje[index].name;
			switch(objectName) {
			case "Key":
				gamePanel.playSoundEffect(1);
				hasKey++;
				gamePanel.superObje[index]=null;
				//System.out.println("Key: "+hasKey);
				gamePanel.uI.showMessage("You got a key!");
				break;
			case "Door":
				if(hasKey > 0) {
					gamePanel.playSoundEffect(3);
					gamePanel.superObje[index]=null;
					hasKey--;
					gamePanel.uI.showMessage("You opened the door! \" BURADA OPED THE DOOR SESİ EKLE :D \" ");
				}else {
					gamePanel.playSoundEffect(3);
					gamePanel.uI.showMessage("You need a key!");
				}
					
				//System.out.println("Key: "+hasKey);
			break;
			case "Boots":
				gamePanel.playSoundEffect(2);
				speed +=1;//item alınınca hızı arttır
				gamePanel.superObje[index]=null;//sonrada sil
				gamePanel.uI.showMessage("Speed up!");
				break;
			case "Chest":
				//sandığı aldığında oyun biter
				gamePanel.uI.gameFinished = true;
				gamePanel.stopMusic();
				gamePanel.playSoundEffect(4);
				
				break;
			}
		}
	}

	public void draw(Graphics2D graphics2d) {

		// ARTIK PNG LERİMİZ VAR BUNU KULLANMAYACAPIZ :D
		/*
		 * // çizim
		 * 
		 * // şimdi rengi ayarlayalım graphics2d.setColor(Color.white); // ekranda bir
		 * dikdörtgen çizeceğiz graphics2d.fillRect(x, y,
		 * gamePanel.tileSize,gamePanel.tileSize);
		 */

		BufferedImage image = null;

		switch (direction) {
		case "up":
			if (spriteNum == 1)
				image = up1;
			else
				image = up2;
			break;
		case "down":
			if (spriteNum == 1)
				image = down1;
			else
				image = down2;
			break;
		case "left":
			if (spriteNum == 1)
				image = left1;
			else
				image = left2;
			break;
		case "right":
			if (spriteNum == 1)
				image = right1;
			else
				image = right2;
			break;
		}
		graphics2d.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);

	}
}
