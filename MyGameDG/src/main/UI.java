package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
	// tüm ekran kullanıcı ara birimi

	GamePanel gamePanel;
	Font arial_40, arial_80B;
	BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	int messageSecond = 0;
	public boolean gameFinished = false;

	int playTimeHourse;

	int playTimeMinute;
	double playTimeSecond;
	// ondalıklı sayı formatı için:
	DecimalFormat decimalFormat = new DecimalFormat("#0.00");

	public UI(GamePanel gamePanel) {
		this.gamePanel = gamePanel;

		// burada başlatmamızın sebebi
		// oyun başlamadan başlatmak
		// ve kaynak ve zamandan tasarruf
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_80B = new Font("Arial", Font.BOLD, 80);
		OBJ_Key obj_Key = new OBJ_Key();
		keyImage = obj_Key.image;
	}

	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}

	public void draw(Graphics2D graphics2D) {
		// oyunu bitirme koşulu
		if (gameFinished == true) {
			// mesajı ortaya yazdırmak için ekran ortasını bulacağız

			graphics2D.setFont(arial_40);
			graphics2D.setColor(Color.WHITE);

			String text;
			int textLength;
			int x;
			int y;

			// mesajımızın uzunluğunu kontrol edelim
			text = "Mission successfully completed...";
			// bu metnin uzunluğunu dödürür
			textLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();

			x = gamePanel.screenWidth / 2 - textLength / 2;

			// metini biraz yukarı kaydıracağız
			y = gamePanel.screenHight / 2 - (gamePanel.tileSize * 3);

			graphics2D.drawString(text, x, y);
			
			//oyun bitirme süresi
			text = "Your time is: \""+playTimeHourse+":"+playTimeMinute+":"+decimalFormat.format(playTimeSecond)+"\" !";
			textLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
			x = gamePanel.screenWidth / 2 - textLength / 2;
			y = gamePanel.screenHight / 2 + (gamePanel.tileSize * 4);
			graphics2D.drawString(text, x, y);
			
			
			
			graphics2D.setFont(arial_80B);
			graphics2D.setColor(Color.YELLOW);
			
			// mesajımızın uzunluğunu kontrol edelim
			text = "Congratulations!...";
			// bu metnin uzunluğunu dödürür
			textLength = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();

			x = gamePanel.screenWidth / 2 - textLength / 2;

			// metini biraz yukarı kaydıracağız
			y = gamePanel.screenHight / 2 + (gamePanel.tileSize * 2);

			graphics2D.drawString(text, x, y);

			// oyunu durduruyoruz
			gamePanel.gameThread = null;

		} else {
			// ekrana oyuncunun mevcut anahtar sayısını basalım

			// ama bu yazım zaman ve kaynak israfıdır
			// graphics2D.setFont(new Font("Arial",Font.ITALIC,40));

			// bu yöntem daha iyidir.
			graphics2D.setFont(arial_40);
			// yaı tipi rengi
			graphics2D.setColor(Color.WHITE);

			graphics2D.drawImage(keyImage, gamePanel.tileSize / 2, gamePanel.tileSize / 2, gamePanel.tileSize,
					gamePanel.tileSize, null);

			// metin çerçevesi çizelim
			graphics2D.drawString("x = " + gamePanel.player.hasKey, 74, 65);

			
			// TIME
			playTimeSecond += (double) 1 / 60;
			if ((int) playTimeSecond == 59) {
				playTimeMinute++;
				playTimeSecond = 0;

				if (playTimeMinute == 59) {
					playTimeHourse++;
					playTimeMinute = 0;
					playTimeSecond = 0;
				}
			}
			graphics2D.drawString(
					"Time: " + playTimeHourse + ":" + playTimeMinute + ":" + decimalFormat.format(playTimeSecond),
					gamePanel.tileSize * 11, 65);

			
			// MESSAGE
			if (messageOn == true) {

				// daha sonradan yazı boyutunu değiştirmek için bir yöntem
				// değer float kabul eder ve F eklemekn gerekir sonuna
				graphics2D.setFont(graphics2D.getFont().deriveFont(30F));

				graphics2D.drawString(message, gamePanel.tileSize / 2, gamePanel.tileSize / 2);

				messageSecond = 2;// metin mesahı kaç saniye gözükecek
				messageCounter++;

				if (messageCounter > gamePanel.FPS * messageSecond) {
					// buradaki 120, saniyedeki FPS yani oluşturulma

					messageCounter = 0;
					messageOn = false;
				}

			}
		}

	}

}
