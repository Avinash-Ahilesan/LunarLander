
public class Camera {
	private double x,y;

	
	public Camera(double x, double y)
	{
		this.x=x;
		this.y=y;
	}
	public void tick(SpaceShip ship)
	{
		if(ship.getY() <0 && ship.getVerticalSpeed() < 0){
			y=(-ship.getY() + Game.HEIGHT/2) ;
			//x=(-ship.getX() + Game.WIDTH)
			//ship.setY(ship.getY() - 100);
	   }
		if(y-ship.getY() >=20 && y !=0)
			y=(-ship.getY() -Game.HEIGHT/2);
		/*y+=1;*/
			//x=-ship.getX()+Game.WIDTH/2;
		/*if(ship.getX() > 1222)
			x=ship.getX() -200;
		*/
		
	}
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
