import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.ThreadLocalRandom;

public class SpaceShip {
	private static final double ROCKET_ROTATING = 0.4;
	private static final double ROCKET_AY = 0.008;
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
	public double getVerticalAcceleration()
	{
		return ay;
	}
	public double setVerticalAcceleration()
	{
		return ax;
	}
	public int getFuel(){return fuel;}
	public void setBack()
	{
		this.x=612;
		this.y=50;
		vy=0;
		vx=0;
		ay=0;
		ax=0;
		angleOfShip = 0;
		Game.setCollided(false);
	}

	public void tick(double delta)
	{
		if(!Game.isCollided()){
			if (rotateLeft) 
				angleOfShip -= ROCKET_ROTATING; //* delta;

			if (rotateRight) 
				angleOfShip += ROCKET_ROTATING; //* delta;

			if (angleOfShip >= 360) 
				angleOfShip -= 360;
			else if (angleOfShip < 0) 
				angleOfShip += 360;


			if (moveUp && fuel > 0) {
				ay = (float)(Math.cos(Math.toRadians(angleOfShip)) * ROCKET_AY);
				ax = (float)(Math.sin(Math.toRadians(angleOfShip)) * ROCKET_AY);
				fuel--;
			} else {
				ay *= FRICTION;
				ax *= FRICTION;
			}

			vy -= (ay - GRAVITY); //* delta;
			vx += ax; //* delta;

			y += vy;
			x += vx;
		}
		else
		{
			
		}

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
	public double getShipAngle()
	{
		return angleOfShip;
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

	public void draw(Graphics2D g, Camera cam)
	{
		g.setColor(Color.WHITE);
		double xvalues[] = { x+20, x+29, x+24};
		double yvalues[] = { y+39, y+39, y+39};
		if(moveUp && fuel > 0){
			int randomNum = ThreadLocalRandom.current().nextInt(0, 7);
			yvalues[2] += 40 + randomNum ;
		}
		Path2D path = new Path2D.Double();
		path.moveTo(xvalues[0], yvalues[0]);
		for(int i = 1; i < xvalues.length; ++i) {
			path.lineTo(xvalues[i], yvalues[i]);
		}
		path.closePath();

		AffineTransform oldTransform = g.getTransform();
		AffineTransform newTransform = AffineTransform.getRotateInstance(Math.toRadians(angleOfShip), cam.getX() + x+25, cam.getY() + y+25);
		g.setTransform(newTransform);
		newTransform.setToTranslation(x, y);

		// g2.setTransform(AffineTransform.getRotateInstance(Math.toRadians(angleOfShip), x+5, y+5));	
		g.translate(cam.getX(), cam.getY());
		g.drawImage(ResourceManager.player, newTransform, null);
		g.draw(path);

		g.translate(-cam.getX(), -cam.getY());
		g.setTransform(oldTransform);
	}

}
