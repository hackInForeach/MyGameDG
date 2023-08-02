package main;

import java.util.Iterator;

import entity.Entity;

public class CollisionChecker {

	GamePanel gamePanel;

	public CollisionChecker(GamePanel gamePanel) {
		this.gamePanel = gamePanel;

	}

	public void checkTile(Entity entity) {
		// player değil de varlık alıyoruz çünkü
		// diğer npc ve canaverlara ile olan çarpışmaları
		// kontrol etmek için alıyoruz

		// oyuncunun sağlam olan birşeye çarpıp çarpmadığını kontrol edeceğiz

		// bu bilgiler ile sütu ve satır numaralarını bulacağız
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

		int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
		int entityRightCol = entityRightWorldX / gamePanel.tileSize;
		int entityTopRow = entityTopWorldY / gamePanel.tileSize;
		int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

		int tileNumber1, tileNumber2;

		switch (entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
			tileNumber1 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityTopRow];
			tileNumber2 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityTopRow];
			if (gamePanel.tileManager.tile[tileNumber1].collision == true
					| gamePanel.tileManager.tile[tileNumber2].collision == true) {

				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
			tileNumber1 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityBottomRow];
			tileNumber2 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityBottomRow];
			if (gamePanel.tileManager.tile[tileNumber1].collision == true
					| gamePanel.tileManager.tile[tileNumber2].collision == true) {

				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
			tileNumber1 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityTopRow];
			tileNumber2 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityBottomRow];
			if (gamePanel.tileManager.tile[tileNumber1].collision == true
					| gamePanel.tileManager.tile[tileNumber2].collision == true) {

				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
			tileNumber1 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityBottomRow];
			tileNumber2 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityTopRow];
			if (gamePanel.tileManager.tile[tileNumber1].collision == true
					| gamePanel.tileManager.tile[tileNumber2].collision == true) {

				entity.collisionOn = true;
			}
			break;

		}

	}

	public int checkObject(Entity entity, boolean player) {

		// bir nesneye vurup vurmadığını kontrol etmek için, vurursa nesnenin indeksini
		// dödürür
		int index = 999;

		// nesne dizisini tarayacak
		for (int i = 0; i < gamePanel.superObje.length; i++) {
			if (gamePanel.superObje[i] != null) {
				// eğer boş değilse o alanda hangi nesnenin olduğunu bilmemiz gerekiyor
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;

				// ve solidArea konumuda bilmemiz gerkiyor
				gamePanel.superObje[i].solidArea.x = gamePanel.superObje[i].worldX + gamePanel.superObje[i].solidArea.x;
				gamePanel.superObje[i].solidArea.y = gamePanel.superObje[i].worldY + gamePanel.superObje[i].solidArea.y;

				// şimdi varllığın yönünü kontrol ediyoruz
				switch (entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					// iki dikdörgenin çarpışıp çarpışmadığını (oto) kontrol etmek için
					// rectangle sınıfının .intersects() metodunu kullanabiliriz
					// bu metod kesişime bakara
					if (entity.solidArea.intersects(gamePanel.superObje[i].solidArea)) {
						//TEST
						//System.out.println("up caprtin!");
						
						//bu cisim katımı deği mi
						if(gamePanel.superObje[i].collision == true) {
							//sağmalsa(else ferek yok varsayılan collisionOn değeri false)
							entity.collisionOn=true;
						}//ve oyunucu olup olmadığınıda kontrol edelim
						if(player == true) {
							//oyuncuysa bu dizini döndürürüz değise birşey yapmayız
							index = i;
						}
					}
					break;
				case "down":
					if (entity.solidArea.intersects(gamePanel.superObje[i].solidArea)) {
						//TEST
						//System.out.println("down caprtin!");
						
						//bu cisim katımı deği mi
						if(gamePanel.superObje[i].collision == true) {
							//sağmalsa(else ferek yok varsayılan collisionOn değeri false)
							entity.collisionOn=true;
						}//ve oyunucu olup olmadığınıda kontrol edelim
						if(player == true) {
							//oyuncuysa bu dizini döndürürüz değise birşey yapmayız
							index = i;
						}
					}
					entity.solidArea.y += entity.speed;
					break;
				case "left":
					if (entity.solidArea.intersects(gamePanel.superObje[i].solidArea)) {
						//TEST
						//System.out.println("left caprtin!");
					
						//bu cisim katımı deği mi
						if(gamePanel.superObje[i].collision == true) {
							//sağmalsa(else ferek yok varsayılan collisionOn değeri false)
							entity.collisionOn=true;
						}//ve oyunucu olup olmadığınıda kontrol edelim
						if(player == true) {
							//oyuncuysa bu dizini döndürürüz değise birşey yapmayız
							index = i;
						}
					}
					entity.solidArea.x -= entity.speed;
					break;
				case "right":
					if (entity.solidArea.intersects(gamePanel.superObje[i].solidArea)) {
						//TEST
						//System.out.println("right caprtin!");
					
						//bu cisim katımı deği mi
						if(gamePanel.superObje[i].collision == true) {
							//sağmalsa(else ferek yok varsayılan collisionOn değeri false)
							entity.collisionOn=true;
						}//ve oyunucu olup olmadığınıda kontrol edelim
						if(player == true) {
							//oyuncuysa bu dizini döndürürüz değise birşey yapmayız
							index = i;
						}
					}
					entity.solidArea.x += entity.speed;
					break;
				}
				// switch ifadesinden sonra bu entity ve objenin solidArea larını resetlememiz
				// gerek
				// aksi halde artmaya devam eder
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gamePanel.superObje[i].solidArea.x = gamePanel.superObje[i].solidAreaDefaultX;
				gamePanel.superObje[i].solidArea.y = gamePanel.superObje[i].solidAreaDefaultY;
			}

		}

		return index;

	}

}
