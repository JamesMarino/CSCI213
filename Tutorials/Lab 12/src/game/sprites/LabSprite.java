package game.sprites;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;

/* This software is designed and created for the purpose of applying some learnt concepts and
*   techniques in Java programming for lab exercises.
*   
*  It is neither created as a model for Java game programming nor optimised for performance. 
*/

/**
 * This class is the shooting target sprite
 * 
 */
public class LabSprite extends GameSprite {

	private int updateRate = 50;			// updates/second  
											// Don't allow it fly too fast or you won't win
	
	private Point location;					// Sprite location
		
		
	private Point velocity = new Point(0, 1);	// Sprite flying step in each update
	
	private Dimension windowDimension;		// Window dimension
	private Dimension imageDimension;		// Sprite image dimension
	
	private boolean survived=false;			// True if the sprite reaches the bottom of the window without destroyed
											// If true, game is over with the player lost

	/**
	 * Create a new sprite with an image 
	 *    The window dimension is used to determine if the sprite survives (reaches the bottom of the window)
	 * 
	 * @param spriteImage The image of the sprite
	 */
	public LabSprite(BufferedImage spriteImage, Dimension windowDimension){
		super(spriteImage);
		
		this.windowDimension = windowDimension;
		this.imageDimension = new Dimension(spriteImage.getWidth(), spriteImage.getHeight());

		// initial location
		location = new Point((int) (Math.random()*(windowDimension.width-imageDimension.width)), 	// start from a random x location
									-imageDimension.height);				       					// fly downwards into the window

		setLocation(location);
		setUpdateRate(updateRate);
		setVelocity(velocity);
	}
	
	
	/**
	 * Get the sprite survival status: 
	 *   true if the sprite reaches the bottom of the window
	 *   if true, the game should be over with the player lost
	 *   
	 * @return Get the survival status
	 */
	public boolean isSurvived(){
		return survived;
	}

	@Override
	public void checkLifeStatus() {
		// Check if the sprite reaches the bottom of the window		
		if( location.y > windowDimension.height - imageDimension.height) {
			survived=true;  		// Game is over with the player lost
		}
	}	
}
