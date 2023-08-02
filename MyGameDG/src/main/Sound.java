package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	//javada wav kullanılır
	Clip clip;    //ses doyyalarını açmak için
	URL soundURL[]=new URL[30];//ses dosyalarının dosyalunu depolamak için
	
	public Sound() {
		soundURL[0]=getClass().getResource("/sound/BlueBoyAdventure.wav");
		soundURL[1]=getClass().getResource("/sound/coin.wav");
		soundURL[2]=getClass().getResource("/sound/powerup.wav");
		soundURL[3]=getClass().getResource("/sound/unlock.wav");
		soundURL[4]=getClass().getResource("/sound/fanfare.wav");
		
	}
	
	//javada ses dosyası açmak için bir format
	//setFile bu nedenle kullanılır
	public void setFile(int index) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[index]);
			clip =AudioSystem.getClip();
			clip.open(ais);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void play() {
		clip.start();
	}
	public void loop() {
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	public void stop() {
		clip.stop();
	}
	
}
