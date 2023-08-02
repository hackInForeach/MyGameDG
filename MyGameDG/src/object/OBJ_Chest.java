package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Chest extends SuperObject {
	public OBJ_Chest() {

		name = "Chest";
		try {

			image = ImageIO.read(getClass().getResourceAsStream("/objects/chest (OLD).png"));

		} catch (IOException exception) {
			System.out.println("Erro: " + exception.getMessage());
		}

	}
}
