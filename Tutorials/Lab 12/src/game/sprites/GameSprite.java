package game.sprites;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/* This software is designed and created for the purpose of applying some learnt concepts and
*   techniques in Java programming for lab exercises.
*   
*  It is neither created as a model for Java game programming nor optimised for performance. 
*/

/**
 *  This is the basic sprite class.  You should create your own sprite classes by extending this class
 * 
 * @author Lei Ye
 *
 */
public class GameSprite implements Runnable{
	
	/* Attributes */
	// update speed
	private int updateRate = 100;								// updates/second (default)
	 															// No point to update faster than the screen refresh
	private long updateInterval = 1000000000L / updateRate; 	// in nanoseconds

	// sprite status
	private boolean aLive = true;
	
	// sprite position
	private Point location =new Point(0,0);
	private Point velocity=new Point(0,0);
	private double rotateAngle = 0.0;								// The angle of rotation in radians; 1 is a good starting point
	private double rotateVelocity = 0.0;							// 0.05 is a good starting point
	private Point2D.Double scale = new Point2D.Double(0.0, 0.0);	
	private Dimension dimension = new Dimension(0,0);				// The actual dimension of the sprite entity
	private Dimension effectiveDimension = new Dimension(0,0);		// The dimension of the sprite drawn on the screen
	
	// Sprite visual entity
	//   They are exclusive in the current design
	private BufferedImage spriteImage = null;
	private Shape spriteShape = null;
	private Color spriteColor = Color.RED;
		
	/**
	 *  Create a sprite with a Shape visual entity
	 *  
	 * @param spriteShape The shape of a sprite
	 */
	GameSprite(Shape spriteShape){

		this.spriteShape = spriteShape;
		dimension = new Dimension(spriteShape.getBounds().width,spriteShape.getBounds().height);
		effectiveDimension = (Dimension)dimension.clone(); 
	}
	
	/**
	 * 	Create a sprite with an Image visual entity
	 * 
	 * @param spriteImage The Image of a sprite
	 */
	GameSprite(BufferedImage spriteImage){

		this.spriteImage = spriteImage;
		dimension = new Dimension(spriteImage.getWidth(), spriteImage.getHeight());
		effectiveDimension = (Dimension)dimension.clone(); 
	}
	
	/* Accessor methods */
	/**
	 *  The method gets the current location of the sprite.
	 *  
	 * @return The current location of the sprite
	 */
	public Point getLocation(){
		return location;
	}
	
	/**
	 *  The method returns the actual dimension of the sprite
	 *  
	 * @return The dimension of the sprite
	 */
	public Dimension getDimension(){
		return dimension;
	}
	
	/**
	 *  The method gets the bounding rectangle of the sprite
	 *    The bounding rectangle as it appears on the screen
	 *  
	 * @return The bounding rectangle of the sprite
	 */
	public Rectangle getBounds(){
		
		// When the sprite is scaled, the visible size on the screen is different from the size in memory
		Point effectiveLocation = (Point)location.clone();
		
		effectiveLocation.translate((dimension.width - effectiveDimension.width)/2, (dimension.height - effectiveDimension.height)/2);
				
		return  new Rectangle(effectiveLocation, effectiveDimension); 
	}
	
	/**
	 *  The method returns the sprite life status
	 *  
	 * @return The life status of the sprite. 
	 *         True - The sprite stops moving and is ready to be removed
	 */
	public boolean isDead() {

		return !aLive;
	}
	
	/* Mutator and helper methods */
	/**
	 *  The method sets the location of the sprite.
	 *    The default location of a sprite is the origin. 
	 *    
	 * @param location The location of the sprite to set to, expressed as a Point
	 */
	public void setLocation(Point location){
		this.location = location;
	}
	
	/**
	 *  The method sets the velocity of the sprite. 
	 *    The default velocity has no speed and direction.
	 * @param velocity The velocity of a 2D sprite, expressed as a Point
	 */
	public void setVelocity(Point velocity){
		this.velocity = velocity;
	}
	
	/**
	 *  The method sets the rotation velocity.
	 *    > 0: rotate clockwise;  
	 *    < 0: rotate anti-clockwise
	 *     
	 * @param rotateVelocity
	 */
	public void setRotateVelocity(double rotateVelocity){
		this.rotateVelocity = rotateVelocity;
	}
	
	/**
	 *  The method sets the rotation angle in radians
	 *  
	 * @param rotateAngle The rotation angle in radians
	 */
	public void setRotateAngle(double rotateAngle){
		this.rotateAngle = rotateAngle;
	}
	
	/**
	 *  The method sets the scaling factor of the sprite
	 *  
	 * @param scale The sprite scaling factor
	 */
	public void setScale(Point2D.Double scale){
		this.scale = scale;
	}
	
	/**
	 *  The method sets the painting colour of the shape sprite
	 *  
	 * @param spriteColor The colour of the shape of the sprite
	 */
	public void setColor(Color spriteColor){
		this.spriteColor = spriteColor;
	}
	
	/**
	 * The method sets the rate to make a move
	 *   The default rate is 10 (100 moves/second)
	 *   
	 * @param updateRate Numbers of moves per second
	 */
	public void setUpdateRate(int updateRate){
		
		this.updateRate = updateRate;
		this.updateInterval = 1000000000L / updateRate; 	// in nanoseconds
	}
	
	/**
	 *  The method signals the sprite collided.
	 *  
	 *   The default behaviour is to die if collided. 
	 *   Override this method to provide specific behaviour when the sprite is collided
	 */
	public void setCollided(){
		aLive=false;
	}
	
	/**
	 *  The method sets the the sprite life status to False to stop it moving and get it ready for removal
	 */
	public void setDead(){
		aLive=false;
	}
	
	/**
	 *  The method specifies the dimension of the sprite entity
	 *  
	 *    A dimension is calculated for the sprite when it is created. 
	 *    
	 *    Use this method to specify the dimension of the sprite as you would it like to be used
	 *    
	 *    Caution: Setting arbitrary values may result in the sprite drawn on the screen in a strange way
	 *             when rotation and scaling are involved.
	 *      
	 * @param dimension A dimension specification for the sprite
	 */
	public void setDimension(Dimension dimension){
		this.dimension  = dimension;
	}
	
	/**
	 *  The method sets the effective dimension of the sprite drawn on the screen
	 *  
	 * @param effectiveDimension The effective dimension of the sprite drawn on the screen
	 */
	private void setEffectiveDimension(Dimension effectiveDimension) {
		
		this.effectiveDimension = effectiveDimension;
	}
	
	/**
	 *  The method provides an image visual entity for the sprite
	 *    This method will replace the existing visual entity, either be an image or shape
	 *  
	 * @param spriteImage	The image for the sprite
	 */
	public void setImage(BufferedImage spriteImage){

		this.spriteImage = spriteImage;
		dimension = new Dimension(spriteImage.getWidth(), spriteImage.getHeight());
		spriteShape = null;
	}
	
	/**
	 *  The method provides a shape visual entity for the sprite. 
	 *    This method will replace the existing visual entity, either be an image or shape
	 *    
	 * @param spriteShape The shape of a sprite
	 */
	public void setShape(Shape spriteShape){
		this.spriteShape = spriteShape;
		dimension = new Dimension(spriteShape.getBounds().width,spriteShape.getBounds().height);
		spriteImage = null;
	}
	
	/**
	 *  The method moves the sprite. 
	 *  
	 *    - Default move is provided for all sprites;
	 *    - Override it to provide a specific implementation for moving.
	 */
	public void move(){
		location.translate(velocity.x, velocity.y);
		rotateAngle += rotateVelocity;
		checkLifeStatus();
	}
	
	/**
	 * Normally used for checking if the sprite flies off the screen. 
	 * 
	 *  This method will be automatically called after each move. 
	 *  
	 *  Defending on the nature of sprites, when they fly off the screen (window), 
	 *    some will die and the game is over;
	 *    for others, you just need to remove them (setDead()) from the live sprite collection.
	 *    
	 *  Override this method in the subclass to determine the fate of off-screen sprites
	 */
	public void checkLifeStatus(){
		// Do thing; never die
		// After flying off your screen, it will continue to fly beyond the solar system into the deep space.
		// 
		// The Sprite does not know the window dimension
	}
	
    /* Custom sprite drawing called by GamePaintComponent */
	/**
	 * The custom sprite drawing method. 
	 * 
	 * 	 Default drawing is provided for all sprites. 
	 *   Override it to provide a specific implementation for drawing.
	 * 
	 * @param g2 The graphic context (Graphics2D) to draw
	 */
	public void draw(Graphics2D g2) {
		
		// Create a new transform for each draw
		g2.setTransform(new AffineTransform());
		// Place the sprite to the its location
		g2.translate(location.x, location.y);
		
		
		// Rotate or/and scale the sprite
		if(rotateAngle != 0.0) {
			g2.translate(dimension.width/2, dimension.height/2);
			g2.rotate(rotateAngle);
	        if(scale.x != 0.0 || scale.y != 0.0){
				g2.scale(scale.x, scale.y);
				setEffectiveDimension(new Dimension((int)(dimension.width*scale.x), (int)(dimension.height*scale.y)));
	        }
			g2.translate(-dimension.width/2, -dimension.height/2);
		}
		
		// Draw image
		if (spriteImage != null) {			
			g2.drawImage(spriteImage, 0, 0, null);
		}
		// Or draw shape
		else if(spriteShape != null ) {
			g2.setColor(spriteColor);
			g2.fill(spriteShape);
		}
	}
	
	/**
	 *  Runnable task: move the sprite
	 */
	public void run(){
		
		long startTimeEachUpdate, timeElapsedEachUpdate, timeLeftEachUpdate;

		// Give a chance for the sprite to set up properly
		//  before starting the to run, or null point error may occur
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
		
		while(aLive){	

			startTimeEachUpdate = System.nanoTime();

			move();
			
			/*
			 * 	Control the update rate
			 */
			timeElapsedEachUpdate = System.nanoTime() - startTimeEachUpdate;

			timeLeftEachUpdate = (updateInterval - timeElapsedEachUpdate) / 1000000L;  // in minisecond 
			if(timeLeftEachUpdate < 1) timeLeftEachUpdate = 1;  // The machine is busy/slow; timeEpalsedEachUpdate is longer than the desired update interval
																//   Still set a small interval to yield the control to allow other threads to run
			try{
				Thread.sleep(timeLeftEachUpdate);				// wait for the specified interval; also give other threads a chance 
			}catch(InterruptedException e){
				System.err.println(e.getMessage());
			}
		}
	}
}
