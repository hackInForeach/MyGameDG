package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {

		JFrame window = new JFrame();
		// pencereyi kaptmak için
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// pencerenin yeniden boyutlandırılmaması için
		window.setResizable(false);
		// oyun adı belirle
		window.setTitle("2D Adventure");

		// paneli pencereye ekleme
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);

		// görebilmek için
		window.pack();

		// pencere ekran ortasında görünsün
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		//eşyaların oyundan önce çağrılması
		gamePanel.setupGame();
		
		// oyunu başlatmak için
		gamePanel.startGmaeThread();

	}

}
