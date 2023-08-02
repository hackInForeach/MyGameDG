package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;

import javax.swing.JPanel;

import entity.Player;
import object.OBJ_Key;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
	// bu class bir tür oyun ekranı gibi çalışacak

	
	// SCREEN SETTİNG
	public final int originalTileSize = 16; // 16x16 tile
	public final int scale = 3;
	public final int tileSize = originalTileSize * scale;// 48x48 tile

	
	// ekran en boy oranları
	public final int maxScreenCol = 25;
	public final int maxScreenRow = 15;
	public final int screenWidth = tileSize * maxScreenCol;// 768p
	public final int screenHight = tileSize * maxScreenRow;// 576p

	
	// WORLD SETTING
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;

	
	// FPS
	int FPS = 100;
	
	//SYSTEM
	// karoların başlatılması için:
	TileManager tileManager = new TileManager(this);

	// tuş larla ilgi kısmı başlatmak için GamePanele ekleyeceğiz
	KeyHandler keyHandler = new KeyHandler();
	
	Sound music = new Sound();
	
	Sound soundEffect = new Sound();
	
	//Sağlam olan ve geçememeiz gerek yapıt
	public CollisionChecker collisionChecker = new CollisionChecker(this);
	
	//eşylar
	public AssetSetter assetSetter = new AssetSetter(this);
	
	//text
	public UI uI=new UI(this);
	
	// bir işlemin defalarca tekrarlanmasını istendiğinde yardımcı olur
	// bu yüzde Runnable sınıfından implemente ederiz
	Thread gameThread;

	
	//ENTITY AND OBJECT
	// şimdi player sınıfını somutlaştırıyoruz
	// ve parametre olark panel ve key geçiyoruz
	public Player player = new Player(this, keyHandler);
	
	//SuperObject sınıfımızı dizi olarak kullanıyoruz
	//oyun esnasında değişebilir, yani oyuncu şuan içi 10 eşya tutabilir
	public SuperObject superObje[]=  new SuperObject[10];

	// player sınıfı oluşturulduğundan buna artık ihtiyaç kalmadı
	// Set player's default position
//	int playerX = 100;
//	int playerY = 100;
//	int playerSpeed = 5;

	public GamePanel() {

		// panel boyutu
		this.setPreferredSize(new Dimension(screenWidth, screenHight));
		// panel rengi
		this.setBackground(Color.black);
		// çift arabelleğe alma özelliğiyle gösterilecek çizimler önce bellekte çizilir
		// sonra gösterilirler dolayısıyla titrem azalır ve performans artar
		this.setDoubleBuffered(true);
		// bu bizim tanımlı tuşlarımızı panel tarafından tanınması için
		this.addKeyListener(keyHandler);
		// buda tuşlarımızın girdisini almak için kullandık
		this.setFocusable(true);
	}
	
	//eşyaralarımız için setObject metodunu çağıralım
	public void setupGame() {
	//oyun başlamadan çağrılması için mainden çağıracağız
		assetSetter.setObject();
		
		//müzik ve ses efektleri
		playMusic(0);
		
	}
	

	public void startGmaeThread() {
		// oyun başlatma
		// yani GamePanel sınıfını bu iş parçacığının kurucusuna aktarıyoruz
		gameThread = new Thread(this);
		gameThread.start();
	}

	// BU UYKU YÖNTEMİ İLE HAZITLANMIŞ OYUN DÖNGÜSÜ
	/*
	 * 
	 * 
	 * @Override public void run() { // otomatik yürütme yeteneği oluştu (Runnable)
	 * // ve bir oyun döngüsü oluşturacağız
	 * 
	 * 
	 * 
	 * //YÖNTEM 1) ilk olarak uyku metoduyla zamanı yöneterek sınırlarımızı
	 * belileyeceğiz double drawInterval = 1000000000/FPS; //0.01666 second double
	 * nextDrawTime = System.nanoTime()+drawInterval;
	 * 
	 * 
	 * 
	 * // anlamı GameThread var olduğu sürece bu parantez, // içinde yazan işlemi
	 * tekrarlar while (gameThread != null) { // şimdi zamanı kontrol ederek pencere
	 * dışına çıkmasın diye kısıt koyacağız // nanoTime() çok küçük bi birimdir ve
	 * 1milyar nano sn. 1 saniyeye eşit. long currentTime = System.nanoTime(); //
	 * bunuda kullanabilir, geçerlizamanı milisaniyeye dönüştürür // yani bin
	 * milisaniye 1 saniyeye eşit // ama nano daha hassa olduğundan şimdili onu
	 * kullanacağız :D // long currentTime2=System.currentTimeMillis();
	 * 
	 * // bu kısım karmaşık olan kısım DİKKAT!!!............. // soru 60FPS için
	 * kısıt nasıl oluşturulur
	 * 
	 * // bu kısımda yapacağımız 2 şey var: // 1 UPDATED: karakter konumları gibi
	 * güncellenmiş bilgiler, update();
	 * 
	 * // 2 DRAW : güncellenmiş bilgilerle ekranı çizin repaint(); // oyun devam
	 * ettiği sürece update() ve repaint() tekrar tekrar çağrılır
	 * 
	 * 
	 * //YÖNTEM 1) için : try { double remainingTime = nextDrawTime -
	 * System.nanoTime(); remainingTime /=1000000;// Thread.sleep() mili saniye
	 * cinsinden istediği için 1 miyona bölüyoruz if(remainingTime <0)
	 * remainingTime=0;
	 * 
	 * Thread.sleep((long) remainingTime);
	 * 
	 * nextDrawTime += drawInterval;
	 * 
	 * }catch(InterruptedException exception) {
	 * System.out.println("Error: "+exception.getMessage()); }
	 * 
	 * 
	 * }
	 * 
	 * }
	 */

	// BU DA DELTA/AKUMULATOR YÖNTEMİ İLE OYUN DÖNGÜSÜ
	@Override
	public void run() {
		// BU YÖNTEMDE ZAMANI ÖNCEDEN KONTROL EDİP SÜREKLİ OLARAK GÜNCELLENMESİ
		// SAĞLANIYOR

		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		// FPS-DRAW TEST
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			// TEST											
			timer += (currentTime - lastTime);

			lastTime = currentTime;
			if (delta >= 1) {
				update();
				repaint();
				delta--;

				// test
				drawCount++;
			}

			// test
			if (timer >= 1000000000) {
				//System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}

		}

	}

	public void update() {
		// buara sınıfın updati kullanılarak günclleme yapılacak
		player.update();

	}

	public void paintComponent(Graphics g) {
		// bu sınıf javada yerleşiktir birşeyler çizmenin standart yolu
		super.paintComponent(g);
		// çizimlerimizi burada yapacağız ve fıçamızda Graphics olacak ancak
		// önce bu Graphics i 2D ye dönüştüreceğiz

		Graphics2D graphics2d = (Graphics2D) g;// dönüştürdük
		
		//TILE
		// ortam için karo çizimi
		tileManager.draw(graphics2d);// çizilen ilk taşlar sonra diğerleri yoksa :D

		//OBJECT
		//nesnenin bu dizide olup olmadığını kontrolüdür aksi halde NullPointer hatası alırız
		for(int i=0;i<superObje.length;i++) {
			if(superObje[i] != null) {
				superObje[i].draw(graphics2d, this);	
			}
		}
		
		//PLaYER
		// oyuncu için karakter çizimi
		player.draw(graphics2d);
		
		//UI
		uI.draw(graphics2d);

		// çizim bitti artık buna sahip olmak için:
		graphics2d.dispose();

	}
	//oyun müzikleri uzun ve süreklidir
	public void playMusic(int index) {
		music.setFile(index);
		music.play();
		music.loop();
	}
	public void stopMusic() {
		music.stop();
	}
	//efectlerde döngü olamz çok kısadadır
	public void playSoundEffect(int index) {
		soundEffect.setFile(index);
		soundEffect.play();
		
	}

}





























