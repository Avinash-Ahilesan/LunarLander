import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
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
	private int angleOfSpaceShip = 0;
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
		if(verticalSpeed > -0.6){
			verticalSpeed-=upThrust;
		}
		verticalSpeed+=0.001;
		y+=verticalSpeed;
		if(horizontalSpeed >= -0.46)
			horizontalSpeed-= leftThrust;
		if(horizontalSpeed <=0.6)
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
		if(angleOfSpaceShip > -90)
			angleOfSpaceShip -=1;
	}

	public void increaseRightThrust()
	{

		if(rightThrust <=0.003){
			rightThrust += 0.0003;
			leftThrust = 0;
		}
		if(angleOfSpaceShip < 90)
			angleOfSpaceShip+=1;
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
		double valoresX[] = { x, x+10, x+5};
		double valoresY[] = { y+10, y+10, y+10};
			valoresY[2] += 10000 * upThrust;
		//add mother ship eventually
		Path2D path = new Path2D.Double();
		/*path.lineTo(5, 10);
		path.lineTo(0, 10);
		path.lineTo(2.5, 20);*/
		path.moveTo(valoresX[0], valoresY[0]);
		for(int i = 1; i < valoresX.length; ++i) {
		   path.lineTo(valoresX[i], valoresY[i]);
		}
		
		path.closePath();
		Graphics2D g2 = (Graphics2D)g;
	
		AffineTransform oldTransform = g2.getTransform();
	    g2.setTransform(AffineTransform.getRotateInstance(Math.toRadians(angleOfSpaceShip), x+5, y+5));	    
		g2.draw(rect);
		g2.draw(path);
		g2.setTransform(oldTransform);
	}

}
