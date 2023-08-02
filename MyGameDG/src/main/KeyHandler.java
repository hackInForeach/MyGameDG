package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	// burada oyuncumuza WASD veya yön tuşlarıyla yön vereceğiz

	public boolean upPressed, downPressed, leftPressed, rightPressed;

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// basılan anahtarın numarasını döndürür
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_W | code == KeyEvent.VK_UP) {
			// W veya ileri yöne tuşunu basarsa
			upPressed = true;
		}
		if (code == KeyEvent.VK_A | code == KeyEvent.VK_LEFT) {
			// A veya ileri yöne tuşunu basarsa
			leftPressed = true;
		}
		if (code == KeyEvent.VK_D | code == KeyEvent.VK_RIGHT) {
			// D veya Sağa yöne tuşunu basarsa
			rightPressed = true;
		}
		if (code == KeyEvent.VK_S | code == KeyEvent.VK_DOWN) {
			// S veya geri yöne tuşunu basarsa
			downPressed = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		//bu kısım tuştan el çekildiğinde o yöne olan gidişatı durdurmak

		if (code == KeyEvent.VK_W | code == KeyEvent.VK_UP) {
			
			upPressed = false;
		}
		if (code == KeyEvent.VK_A | code == KeyEvent.VK_LEFT) {
			
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D | code == KeyEvent.VK_RIGHT) {
			
			rightPressed = false;
		}
		if (code == KeyEvent.VK_S | code == KeyEvent.VK_DOWN) {
			
			downPressed = false;
		}

	}

}
