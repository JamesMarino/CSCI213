package game.sprites;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.image.BufferedImage;

/* This software is designed and created for the purpose of applying some learnt concepts and
*   techniques in Java programming for lab exercises.
*   
*  It is neither created as a model for Java game programming nor optimised for performance. 
*/

/**
 * This class is the bullet sprite<br>
 * <br>
 *  A bullet is a sprite as defined bellow
 *
 */
public class BulletSprite extends GameSprite {

	private Point velocity = new Point(0, -5);		// Bullet flying velocity
	private Color bulletColor = Color.BLACK;
	
	/*
	 * Bullet Polygon definition
	 * 
	 * 
	 *        (0,0)    (5,0)
	 * 		       +--+
	 *             |  |
	 *             |  |    
	 *      (0,10) +--+ (5,10)
	 *
	 */
	private static final int[] BULLET_POLYGON_X={0, 5, 5, 0};
	private static final int[] BULLET_POLYGON_Y={0,0,10,10};
	private static final Shape BULLET_SHAPE = new Polygon(BULLET_POLYGON_X, BULLET_POLYGON_Y, 4);

	/**
	 * The constructor to create a bullet with the default shape as the entity
	 * 
	 * @param fireLocation The location where a bullet starts to fly
	 * @param gameViewDimension The game view dimension (the window dimension of the program)
	 */
	
	public BulletSprite(Point fireLocation, Dimension gameViewDimension){
		
		super(BULLET_SHAPE);

		fireLocation.translate(-getBounds().width/2, -getBounds().height);
		setLocation(fireLocation);
		setVelocity(velocity);
		setColor(bulletColor);
		
	}
	

	@Override
	/**
	 * The method determines if the bullet flies off the game view without hitting a target
	 */
	public void checkLifeStatus(){
		
			if(getLocation().y <= -getBounds().height)			// Fly off the window; missed all targets
			setDead();
	}
}
