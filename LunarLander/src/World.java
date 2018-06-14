import java.awt.Color;
import java.awt.Graphics;


public class World {
	public int[] map;
	
	private static int gravity = 9;

	public static int getGravity() {
		return gravity;
	}

	public static void setGravity(int gravity) {
		World.gravity = gravity;
	}
	public static void draw(Graphics g)
	{
		g.setColor(Color.white);
		int[] xvalues = {0, 200, 200, 400, 400, 450, 450, 550};
		int[] yvalues = {850,850,850,750,750,750, 750, 650};
		for(int i = 0; i < xvalues.length; i++)
			if(i+1 < xvalues.length)
				g.drawLine(xvalues[i], yvalues[i], xvalues[i+1], yvalues[i+1]);
		/*g.drawLine(0, 800, 200, 800);*/
		/*g.drawLine(200, 800, 0, 800);
		g.drawLine(200, 800, 400, 700);
		g.drawLine(400, 700, 500, 700);*/
	}

}
