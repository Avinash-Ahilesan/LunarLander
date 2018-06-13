import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.concurrent.ThreadLocalRandom;

public class SpaceShip {
	private static final double ROCKET_ROTATING = 0.4;
	private static final double ROCKET_AY = 0.006;
	private static final double FRICTION = 0.03;
	private static final double GRAVITY = 0.003;
	private double vy = 0;
	private double vx = 0;
	private double ay = 0;
	private double ax = 0;
	private double angleOfShip = 0;
	private boolean moveUp = false;
	private boolean rotateLeft = false;
	private boolean rotateRight = false;
	private long time;
	private int fuel;
	private double x = 612,y = 50;

	public SpaceShip(int fuel)
	{
		time = System.currentTimeMillis() / 1000L;

		this.fuel = fuel;
	}
	public double getVerticalSpeed() {
		return vy;
	}
	public void setVerticalSpeed(double verticalSpeed) {
		this.vy = verticalSpeed;
	}
	public double getHorizontalSpeed() {
		return vx;
	}
	public void setHorizontalSpeed(double horizontalSpeed) {
		this.vx = horizontalSpeed;
	}

	public void tick(double delta)
	{
		
		 if (rotateLeft) {
	            angleOfShip -= ROCKET_ROTATING * delta;
	        }
		 
	        if (rotateRight) {
	            angleOfShip += ROCKET_ROTATING * delta;
	        }

	        if (angleOfShip >= 360) {
	            angleOfShip -= 360;
	        } else if (angleOfShip < 0) {
	            angleOfShip += 360;
	        }

	        if (moveUp) {
	            ay = (float)(Math.cos(Math.toRadians(angleOfShip)) * ROCKET_AY);
	            ax = (float)(Math.sin(Math.toRadians(angleOfShip)) * ROCKET_AY);
	        } else {
	            ay *= FRICTION;
	            ax *= FRICTION;
	        }

	        vy -= (ay - GRAVITY) * delta;
	        vx += ax * delta;

	        y += vy;
	        x += vx;
		
	}
	
	public void moveUp(boolean moving)
	{
		this.moveUp = moving;
	}
	public void moveLeft(boolean moving)
	{
		this.rotateLeft = moving;
	}
	public void moveRight(boolean moving)
	{
		this.rotateRight = moving;
	}
	public double getX()
	{
		return x;
	}
	public double getY()
	{
		return y;
	}
	public void setX(double x)
	{
		this.x= x;
	}
	public void setY(double y)
	{
		this.y = y;
	}

	public void draw(Graphics g)
	{
		g.setColor(Color.WHITE);
		double valoresX[] = { x+20, x+29, x+24};
		double valoresY[] = { y+39, y+39, y+39};
		if(moveUp){
			int randomNum = ThreadLocalRandom.current().nextInt(0, 7);
			valoresY[2] += 40 + randomNum ;
		}
		//add mother ship eventually
		Path2D path = new Path2D.Double();
		/*path.lineTo(5, 10);
		path.lineTo(0, 10);
		path.lineTo(2.5, 20);*/
		path.moveTo(valoresX[0], valoresY[0]);
		for(int i = 1; i < valoresX.length; ++i) {
		   path.lineTo(valoresX[i], valoresY[i]);
		}
		g.drawString(Double.toString(angleOfShip), 10, 30);
		path.closePath();
		Graphics2D g2 = (Graphics2D)g;
	
		AffineTransform oldTransform = g2.getTransform();
		AffineTransform newTransform = AffineTransform.getRotateInstance(Math.toRadians(angleOfShip), x+25, y+25);
		g2.setTransform(newTransform);
		newTransform.setToTranslation(x, y);
		
	   // g2.setTransform(AffineTransform.getRotateInstance(Math.toRadians(angleOfShip), x+5, y+5));	  
		g2.drawImage(ResourceManager.player, newTransform, null);
		g2.draw(path);
		g2.setTransform(oldTransform);
	}

}
