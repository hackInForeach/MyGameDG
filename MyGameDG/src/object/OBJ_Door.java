package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Door extends SuperObject {
	public OBJ_Door() {

		name = "Door";
		try {

			image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));

		} catch (IOException exception) {
			System.out.println("Erro: " + exception.getMessage());
		}
		
		//katı hale getirmek için eklendi
		collision=true;
		
	}
}
