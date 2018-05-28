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
		horizontalSpeed-= leftThrust;
		horizontalSpeed+= rightThrust;
		x+=horizontalSpeed;
		System.out.println("vertical speed:" +verticalSpeed);
		System.out.println("horizontal Speed: " + horizontalSpeed);
		/*if(rightThrust >0)
			x+=rightThrust;
		else if(leftThrust >0)
			x-= leftThrust;*/
	}
	public void increaseLeftThrust()
	{
		if(leftThrust <=0.003){
			leftThrust += 0.0003;
			rightThrust = 0;
		}
	}
	
	public void increaseRightThrust()
	{
		
		if(rightThrust <=0.003){
			rightThrust += 0.0003;
			leftThrust = 0;
		}
	}
	public void destroyHorizontalThrust()
	{
		leftThrust = 0;
		rightThrust = 0;
	}
	public void destroyVerticalThrust()
	{
		upThrust = 0;
	}
	public void increaseUpThrust()
	{
		if(upThrust <=0.0027)
			upThrust += 0.00005;
		
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
		g.setColor(Color.RED);
		Rectangle2D rect = new Rectangle2D.Double(x, y, 10, 10);
		Graphics2D g2 = (Graphics2D)g;
		g2.draw(rect);
	}

}
