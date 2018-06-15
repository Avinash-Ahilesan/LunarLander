import org.omg.PortableServer.THREAD_POLICY_ID;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;


import java.util.Formatter;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Game extends JPanel implements Runnable, KeyListener{

	private JFrame frame;
	private int gameState=0;
	private Canvas canvas;
	private int width, height;
	private Thread thread;
	private SpaceShip ship;
	private BufferStrategy bufferStrat;
	private Graphics g;
	private static boolean collided = false;
	private static boolean bothLegsCollided = false;
	private boolean win = false;
	private boolean keys[] = new boolean[256];
	boolean left,right,up,down;
	Camera cam;


	public Game(int width, int height)
	{
		this.width = width;
		this.height = height;


		frame = new JFrame ("Avinash and Siavash's Space Game");
		frame.setSize(width, height);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//game stays open in background unless this is set	
		frame.setLocationRelativeTo(null);	//center window
		frame.setVisible (true);


		//canvas to paint on3
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);

		frame.addKeyListener(this);
		frame.add(canvas);
		frame.pack();

		setFocusable(true);
		ship = new SpaceShip(1000);
		cam = new Camera(0,0);

	}


	private void init()
	{
		ResourceManager.loadAssets();
		//load assets and stuff
	}


	public synchronized void start()
	{
		thread = new Thread(this);
		thread.start();	//starts new thread, runs the run method
	}


	private void render()
	{

		bufferStrat = canvas.getBufferStrategy();
		if(bufferStrat == null)
		{

			canvas.createBufferStrategy(3);
			return;
		}
		g = bufferStrat.getDrawGraphics();


		//Clear Screen
		g.clearRect(0, 0, width, height);

		//Start Draw
		if(gameState==0)
		{
			Graphics2D g2d = (Graphics2D)g;
			g.setColor(Color.black);
			g.fillRect(0, 0, width, height);
			g.setColor(Color.white);
			g.drawImage(ResourceManager.background, 0,0,null);

			g2d.translate(cam.getX(), cam.getY());
			ship.draw(g2d, cam);
			World.draw(g2d, ship, cam);

			g2d.translate(-cam.getX(), -cam.getY());
			g.drawString(" Horizontal Speed: "  + String.format("%.1f",ship.getHorizontalSpeed() * 10), 10, 10);
			g.drawString(" Vertical Speed: " + String.format("%.1f", ship.getVerticalSpeed() * 10), 10, 20);
			g.drawString("Ship Angle:" + ship.getShipAngle(), 200, 10);
			g.drawString("Fuel:" + ship.getFuel(), 200, 20);
			if(collided)
			{
				if(win) 
					g.drawString("YOU HAVE LANDED!!!", 600, 340);
					
				else {
					g.drawString("YOU HAVE CRASHED!!!", 600, 340);
					
					if(ship.getFuel() == 0)
					{
					}
					else
					{
						try {
							bufferStrat.show();
							bufferStrat.show();
							g.dispose();
							Thread.sleep(1000);
							ship.setBack();
						}
						catch(Exception e)
						{						}
					}

				}
			}

			//End Draw
		}
		bufferStrat.show();
		g.dispose();

	}

	private void tick(double nextTick)
	{
		//in my JRPG all my game logic (like updating player vars and stuff) went in here, not sure how useful this will be here
		getGameInput();
		if(collided)
		{
			if(bothLegsCollided){
				if(ship.getVerticalSpeed() < 0.3 && Math.abs(ship.getHorizontalSpeed()) < 0.2)
				{
					if(ship.getShipAngle() > 350 || ship.getShipAngle() < 10)
						win=true;
					else
						win=false;
				}
			}
		}
		else{
			if(left)
				ship.moveLeft(true);
			else
				ship.moveLeft(false);
			if(right)
				ship.moveRight(true);
			else
				ship.moveRight(false);

			if(up)
				ship.moveUp(true);
			else 
				ship.moveUp(false);

			ship.tick(nextTick);
			cam.tick(ship);
			if(ship.getX()>1222)
				ship.setX(2);
			if(ship.getX() < 2)
				ship.setX(1222);
		}


	}
	public void run() {
		init();	//initialize (load all the resources)

		/* ************************************************
		 * Ok so lots of confusing code but basically what this does is limit how many times the game process info
		 * If you put the render statement outside the if statement it unlocks the fps (after if OC'd my graphics card it makes a wierd noise
		 *  when on certain screens lol)
		 */

		int ticksPerSecond = 120;	//limiting times that tick and render can run, effectively an fps limit
		double timePerTick = 1000000000 / ticksPerSecond;	//dividing 1 second (1B nanoseconds) by 60  ticks per second to get time per tick
		double nextTick = 0;
		long now;
		long lastTime = System.nanoTime();	//current time of computer in nano seconds

		//GAME LOOP (tick is game logic (we dont need to have this), render is the draw method)
		while(true)
		{
			now = System.nanoTime();
			nextTick += (now-lastTime) / timePerTick;	//divide time past by max time to get when to call tick
			lastTime = now;

			if(nextTick >= 1){	//tick when needed 
				nextTick -= 1;
				tick(nextTick);
			}

			render();	
		}
	}
	private void getGameInput()
	{

		if(keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP])
			up = true;
		else 
			up = false;
		if(keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN])
			down = true;
		else
			down = false;
		if(keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT])
			left = true;
		else
			left = false;
		if(keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT])
			right = true;
		else
			right = false;

		/*up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];*/
	}

	public void keyPressed(KeyEvent key) {
		keys[key.getKeyCode()] = true;

	}

	public void keyReleased(KeyEvent key) {
		keys[key.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent key) {
	}


	public static boolean isCollided() {
		return collided;
	}


	public static void setCollided(boolean collided) {
		Game.collided = collided;
	}


	public static boolean isBothLegsCollided() {
		return bothLegsCollided;
	}


	public static void setBothLegsCollided(boolean bothLegsCollided) {
		Game.bothLegsCollided = bothLegsCollided;
	}

}
