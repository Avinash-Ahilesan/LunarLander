import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class SpaceShip {
	private double verticalSpeed = 0;
	private double horizontalSpeed = 0;
	private double upThrust = 0;
	private double rightThrust = 0;
	private double leftThrust = 0;
	private long time;
	private int fuel;
	private double x = 612,y = 50;

	public SpaceShip(int fuel)
	{
		time = System.currentTimeMillis() / 1000L;

		this.fuel = fuel;
	}
	public double getVerticalSpeed() {
		return verticalSpeed;
	}
	public void setVerticalSpeed(double verticalSpeed) {
		this.verticalSpeed = verticalSpeed;
	}
	public double getHorizontalSpeed() {
		return horizontalSpeed;
	}
	public void setHorizontalSpeed(double horizontalSpeed) {
		this.horizontalSpeed = horizontalSpeed;
	}

	public void tick()
	{
		verticalSpeed+=0.001;
		verticalSpeed-=upThrust;
		y+=verticalSpeed;
		if(rightThrust >0)
			x+=rightThrust;
		else if(leftThrust >0)
			x-= leftThrust;
	}
	public void moveLeft()
	{
		rightThrust = 0;
		if(leftThrust <=0.2)
			leftThrust += 0.5;
	}
	public void moveRight()
	{
		leftThrust = 0;
		if(rightThrust <=0.2)
			rightThrust += 0.5;
	}
	public void setThrust(int thrustValue)
	{
		upThrust = thrustValue;
	}
	public void increaseThrust()
	{
		if(upThrust <=0.0025)
			upThrust += 0.00005;
		
	}
	public void draw(Graphics g)
	{
		g.setColor(Color.RED);
		Rectangle2D rect = new Rectangle2D.Double(x, y, 10, 10);
		Graphics2D g2 = (Graphics2D)g;
		g2.draw(rect);
	}

}
