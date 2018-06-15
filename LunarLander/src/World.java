import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public class World {
	public static int[] map;
	public static double[] xvalues = {0, 200.0, 400, 450 , 550, 580, 640, 680,720,820,900,1000,1050,1100, 1224};
	public static double[] yvalues = {850, 850.0, 750, 750, 550, 750, 750, 650,790,870,870,790,870,870, 700};
	private static int gravity = 9;
	public static Path2D path;
	public static int getGravity() {
		return gravity;
	}

	public static void setGravity(int gravity) {
		World.gravity = gravity;
	}
	public static void draw(Graphics2D g, SpaceShip ship)
	{
		g.setColor(Color.white);
		Path2D path = new Path2D.Double();
		path.moveTo(xvalues[0], yvalues[0]);
		for(int i = 1; i < xvalues.length; ++i) {
			path.lineTo(xvalues[i], yvalues[i]);
		}
		Rectangle2D rect = new Rectangle2D.Double(ship.getX(), ship.getY(), ResourceManager.player.getWidth(null)-10, ResourceManager.player.getHeight(null)-10);
		Point2D point  = new Point2D.Double(ship.getX(), ship.getY()+ ResourceManager.player.getHeight(null) - 10);
		Point2D point1  = new Point2D.Double(ship.getX()+ResourceManager.player.getHeight(null) - 10, ship.getY()+ ResourceManager.player.getHeight(null) - 10);
		if(path.intersects(rect))
		{
			Game.setCollided(true);
			if(path.contains(point) &&path.contains(point1))
				Game.setBothLegsCollided(true);
		}
		g.draw(path);
		/*g.setColor(Color.white);
		for(int i = 0; i < xvalues.length; i++)
			if(i+1 < xvalues.length)
				g.drawLine(xvalues[i], yvalues[i], xvalues[i+1], yvalues[i+1]);*/
		/*g.drawLine(0, 800, 200, 800);*/
		/*g.drawLine(200, 800, 0, 800);
		g.drawLine(200, 800, 400, 700);
		g.drawLine(400, 700, 500, 700);*/
	}

}
