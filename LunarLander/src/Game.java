import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;


import javax.swing.JFrame;
import javax.swing.JPanel;


public class Game extends JPanel implements Runnable, KeyListener{

	private JFrame frame;
	private Canvas canvas;
	private int width, height;
	private Thread thread;
	private SpaceShip ship;
	private BufferStrategy bufferStrat;
	private Graphics g;
	long time= 0;
	private boolean keys[] = new boolean[256];
	boolean left,right,up,down;
	
	//TEST CODE
	int x = 612, y = 50;
	
	public Game(int width, int height)
	{
		this.width = width;
		this.height = height;


		frame = new JFrame ("Escape from Tarkov");
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//game stays open in background unless this is set	
		frame.setLocationRelativeTo(null);	//center window
		frame.setVisible (true);


		//canvas to paint on
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);

		frame.addKeyListener(this);
		frame.add(canvas);
		frame.pack();
		
		setFocusable(true);
		time = System.currentTimeMillis()/1000L;
		ship = new SpaceShip(5000);
		
	}
	
	
	private void init()
	{
		//load assets and stuff
	}
	
	
	public synchronized void start()
	{
		thread = new Thread(this);
		thread.start();	//runs thread
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
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		
		//g.drawImage(Res.cells, 95, 0, null);
		//g.drawImage(Res.player, x, y, null);
		//End Draw
		ship.draw(g);
		
		bufferStrat.show();
		g.dispose();
	}
	
	private void tick()
	{
		//in my JRPG all my game logic (like updating player vars and stuff) went in here, not sure how useful this will be here
		getGameInput();
		if(left){ 
			x-= 1;
			ship.moveLeft();
		}
		if(right){
			x+=1;
			ship.moveRight();
		}
		if(up){
			ship.increaseThrust();
			y-= 1;
		}
		/*else if(down )
			y+= 1;*/
		if(left==false && right==false && up==false)
			ship.setThrust(0);
		ship.tick();
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
				tick();
				nextTick -= 1;
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

}