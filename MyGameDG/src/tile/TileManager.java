package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;

public class TileManager {

	GamePanel gamePanel;
	// döşeme sınıfı
	public Tile[] tile;
	// maps paketinteki mapı okumak için
	public int mapTileNumber[][];

	public TileManager(GamePanel gamePanel) {
		this.gamePanel = gamePanel;

		// döşeme dizisinin boyutu
		// 10 çeşit karo oluşturacağız cam,su,duvar gibi
		// ihtiyaca göre sayı değişkenlik gösterebilir
		tile = new Tile[10];
		// mapı yapıcı içinde başlatmak için:
		// ve buradaki oluşan sayıları map dosyasına koyarak harita oluşturacağız
		mapTileNumber = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

		// yöntem yapıcıya aktarıldı
		getTileImage();
		// hazıldığımız haritayı çağırıyoruz:
		// ve nuraya hazırlana map ın uzantısı eklenerek harita yüklemesi yapılabilir
		loadMap("/maps/world01.txt");
	}

	public void getTileImage() {
		// try catch bloğunda bir döşeme dizisi başlatılacak
		try {
			// döşeme dizisi başlatılıyor:
			// ver örneklemeler oluşturuldu

			// çim
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			
			
			// duvar
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			//tam bit kara yapmakiçin: sağlam karolar için tru, varsayılan false
			tile[1].collision=true;
			
			
			
			// su
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[2].collision=true;
			
			
			// toprak
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
			
			
			
			// ağaç
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			tile[4].collision=true;
			
			
			// kum
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));

		} catch (IOException exception) {
			System.out.println("Error: " + exception.getMessage());
		}
	}

	// map e ımageleri koymak için:
	public void loadMap(String filePath) {
		/*
		 * Kısaca: string olarak okunan map dosyasının ilgili satı ve sütunu bizim
		 * değerlerimizle eşleşmesi için int e çevirerek denk gelen sayılara ilgili png
		 * dosyasını vererek map oluşmuş olacak
		 */

		try {

			// metiin dosyasını içe aktarmak için InputStream kullandık
			InputStream is = getClass().getResourceAsStream(filePath);
			// aktarılan metin dosyasını okumak için BufferedReader kullandık
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

			int col = 0, row = 0;
			// bu döngü içinde metin dosyasını okuyacağız
			while (col < gamePanel.maxWorldCol & row < gamePanel.maxWorldRow) {
				// readLine() : tek bir satır okuyacağı anlamına gelir
				String line = bufferedReader.readLine();
				// ve diğer while döngüsü:
				// az önce okunan satırdan bu sayılar birer birer alınır
				while (col < gamePanel.maxWorldCol) {
					String numbers[] = line.split(" ");

					// şimdi Stringten int e gidiyoruz
					// yani readLine String olarak aldığı veriyi int e
					// çeviriyoruz ki onları sayı olark kullanabileliç
					int num = Integer.parseInt(numbers[col]);

//şimdi mapTileNumber dizisini kullanarak num değişkenine gelen sayılar 
//col ve row kordinatlarıylar birlikte okunan int değerlerin karşılığı 
					// olan png dosyaları ile haritamız oluşacak
					mapTileNumber[col][row] = num;
					col++;
				}
				// sütun(col) sayı sınırına ulainca sütun sıfırlanıp satır
				// 1 artar bu veri sonuna kadar devm eder
				if (col == gamePanel.maxWorldCol) {
					col = 0;
					row++;
				}
			} // son olarak BufferedRead ile işimiz bitince kullanmayacağımız için kapatacağız
			bufferedReader.close();

		} catch (Exception exception) {
			System.out.println("Error: " + exception.getMessage());
		}
	}

	// şimdi bir çizim yötemi oluşturulacak
	public void draw(Graphics2D graphics2d) {

		int worldCol = 0, worldRow = 0;
		// şuan dünya haritası biziö sınırımız yani çok daha büyük bir döngü yapıyoruz
		while (worldCol < gamePanel.maxWorldCol & worldRow < gamePanel.maxWorldRow) {
			// burada karaloar çizeceğiz
			int tileNumber = mapTileNumber[worldCol][worldRow];

			// x ve y yi bulmak için:
			int worldX = worldCol * gamePanel.tileSize;
			int worldY = worldRow * gamePanel.tileSize;
			int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
			int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
			
			//bu if in anlamı oyuncu ekranı içerisine dahil olan karoların çiziminin yapılmasıdır ki,
			//buda performansı çok daha büyük daha fazla nesnenin olduğu haritalarda artırır.
			if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX & 
				worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX & 
				worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY & 
				worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {
				
				graphics2d.drawImage(tile[tileNumber].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize,null);

			}

			worldCol++;

			if (worldCol == gamePanel.maxWorldCol) {
				worldCol = 0;
				worldRow++;

			}
		}
	}

}
