package main;

import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {

	GamePanel gamePanel;
	
	public AssetSetter(GamePanel gamePanel) {
		this.gamePanel=gamePanel;
	}
	
	//burada bazı versayılan nesleri başltıp map e ekleyeceğiz
	public void setObject() {
		//BURASI NESNELERİN ÖNYÜKLEMESİ
		//bu anahtar nesne sınıfı kullanılacak
		gamePanel.superObje[0]=new OBJ_Key();
		gamePanel.superObje[0].worldX=23*gamePanel.tileSize;
		gamePanel.superObje[0].worldY=7*gamePanel.tileSize;
		
		
		gamePanel.superObje[1]=new OBJ_Key();
		gamePanel.superObje[1].worldX=23*gamePanel.tileSize;
		gamePanel.superObje[1].worldY=40*gamePanel.tileSize;
		
		gamePanel.superObje[2]=new OBJ_Key();
		gamePanel.superObje[2].worldX=38*gamePanel.tileSize;
		gamePanel.superObje[2].worldY=8*gamePanel.tileSize;
		
		gamePanel.superObje[3]=new OBJ_Door();
		gamePanel.superObje[3].worldX=10*gamePanel.tileSize;
		gamePanel.superObje[3].worldY=11*gamePanel.tileSize;
		
		gamePanel.superObje[4]=new OBJ_Door();
		gamePanel.superObje[4].worldX=8*gamePanel.tileSize;
		gamePanel.superObje[4].worldY=28*gamePanel.tileSize;
		
		gamePanel.superObje[5]=new OBJ_Door();
		gamePanel.superObje[5].worldX=12*gamePanel.tileSize;
		gamePanel.superObje[5].worldY=22*gamePanel.tileSize;
		
		gamePanel.superObje[6]=new OBJ_Chest();
		gamePanel.superObje[6].worldX=10*gamePanel.tileSize;
		gamePanel.superObje[6].worldY=8*gamePanel.tileSize;
		
		gamePanel.superObje[7]=new OBJ_Boots();
		gamePanel.superObje[7].worldX=37*gamePanel.tileSize;
		gamePanel.superObje[7].worldY=42*gamePanel.tileSize;
		
		
	}
	
}
