
import javax.imageio.ImageIO;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceManager {
	
	
	public static Image player, background;
	public static BufferedImage bckgrnd;
	public static AudioClip clip;
	/*
	 * Description: loads assets
	 * Arguments: none
	 * Return Type: void
	 */
	public static void loadAssets()
	{
		player       =  loadImage ("images/image.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		background = loadImage("images/background.fw.png");

		//load assets from files
	}
	
	/*
	 * Description: method to load image
	 * Arguments: none
	 * Return Type: void
	 */
	private static BufferedImage loadImage(String path)
	{
		try {
			return ImageIO.read(ResourceManager.class.getResource(path));
		} catch (IOException e) {
			System.out.println("Error loading image!");
			e.printStackTrace();
			System.exit(1);
		}
		return null;

	}

}
