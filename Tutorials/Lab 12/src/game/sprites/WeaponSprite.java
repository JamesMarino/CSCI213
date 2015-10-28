package game.sprites;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.KeyEvent;

/* This software is designed and created for the purpose of applying some learnt concepts and
*   techniques in Java programming for lab exercises.
*   
*  It is neither created as a model for Java game programming nor optimised for performance. 
*/

/**
 * This is the weapon sprite
 *
 */
public class WeaponSprite extends GameSprite{

	private final static int X_VELOCITY =3;
	
	private Point velocity = new Point(0, 0);
	private Color weaponColor = Color.BLUE;

	/*
	 * Weapon Polygon definition
	 * 
	 * 
	 *       (18,0)    (23,0)
	 * 		       +--+
	 *  (0,10)     |  |     (41,10)
	 *      +------+  +------+
	 *      | (18,10) (23,10)|
	 *      +----------------+
	 *    (0,20)           (41.20)
	 *
	 */
	private final static int[] WEAPON_POLYGON_X={0,  0, 18, 18, 23, 23, 41, 41};
	private final static int[] WEAPON_POLYGON_Y={20, 10, 10, 0, 0,  10, 10, 20};
	private final static Shape WEAPON_SHAPE = new Polygon(WEAPON_POLYGON_X, WEAPON_POLYGON_Y, 8);
	
	private Dimension gameViewDimension;
	
	/**
	 *  The constructor of the weapon
	 *    The window dimension is used to ensure the weapon remain in the window
	 *    
	 * @param gameViewDimension The game view dimension (the window dimension of the program)
	 */
	public WeaponSprite(Dimension gameViewDimension){
		
		super(WEAPON_SHAPE);
		
		this.gameViewDimension = gameViewDimension;
			
		setLocation(new Point(gameViewDimension.width/2,  						// start from the middle of the window
				    gameViewDimension.height-WEAPON_SHAPE.getBounds().height));	// located at the bottom of the windows

		setColor(weaponColor);
		
	}

	/**
	 *  The method to tell from where the bullet starts flying
	 *  
	 * @return The location from where the bullet starts flying
	 */
	public Point getFireLocation(){
		
		// shoot from the top at the center of the weapon		
		return new Point(getLocation().x + WEAPON_SHAPE.getBounds().width/2, gameViewDimension.height-WEAPON_SHAPE.getBounds().height); 
	}

	
	/**
	 *  KeyPressed event handler of the weapon dedicated by the game KeyListener
	 *   
	 *    Right arrow key: move right
	 *    Left arrow key: move left
	 *    
	 * @param keyEvent The key event to determine which arrow key is pressed
	 */
	public void keyPressed(KeyEvent keyEvent){

		int keyCode = keyEvent.getKeyCode();

		if(keyCode == KeyEvent.VK_LEFT){
			setXDirection(-1);
		}
		if(keyCode == KeyEvent.VK_RIGHT){
			setXDirection(1);
		}

	}

	/**
	 *  KeyReleased event handler of the weapon dedicated by the game KeyListener
	 *   
	 *   Stop moving when the arrow is released
	 *   
	 * @param keyEvent The KeyReleased event
	 */
	public void keyReleased(KeyEvent keyEvent){

		setXDirection(0);

	}

	/**
	 *  The method to change the direction by changing direction of the velocity
	 *  
	 * @param xdir Weapon moving direction; 1: right; -1: left
	 */
	private void setXDirection(int xdir){
		velocity.x = xdir*X_VELOCITY;
		setVelocity(velocity);
	}

	@Override
	public void move()
	{
		super.move();

		// Make sure the weapon is in the window
		if(getLocation().x < 0)
			setLocation(new Point(0, getLocation().y));

		if(getLocation().x > gameViewDimension.width-getBounds().width)
			setLocation(new Point(gameViewDimension.width-getBounds().width, getLocation().y));
	}
}
