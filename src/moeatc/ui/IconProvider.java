package moeatc.ui;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class IconProvider {
	private IconProvider() {
	}

	private static Image icon;
	static {
		try {
			icon = ImageIO.read(IconProvider.class.getResource("logo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Image getIcon() {
		return icon;
	}
}
