package game.main;

import game.sprites.*;
import game.view.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JComponent;

/* This software is designed and created for the purpose of applying some learnt concepts and
*   techniques in Java programming for lab exercises.
*   
*  It is neither created as a model for Java game programming nor optimised for performance. 
*/

/**
 * This is the GameUpdater and EventListener<br>
 * <br>
 * A GameUpdater manages game characters:<br><br>
 * <ol>
 *      <li> create all sprites and add them to GamePaintCompoment</li>
 *      <li> create a GameEngine to drive the game (regular updating the game) and add this GameUpdater to the GameEngine</li>
 *      <li> update the game</li>
 *      <ol style="list-style-type: lower-roman;">
 *         <li> move all sprites (update to new location)</li>
 *         <li> detect collision</li>
 *         <li> remove dead sprites</li>
 *         <li> monitor game progress  </li>
 *         <li> refresh the screen, driven by the GameEngine  </li> 
 *      </ol>
 * </ol>
 *
 */
public class LabShootingGame implements GameUpdater, KeyListener {

	// Game parameters
	private static final int NUMBER_OF_LABS=12;				 		// 12 labs in total to shoot
	private static final double LABSPRITE_CREATION_RATE = 0.5;      // Every 2 seconds

	private static int numberOfLabSpritesCreated=0;  				// Used to check if all LabSprites are destroyed
	private static boolean isPowerSpriteEnabled = true; 			// A PowerSprite is added when all LabSprites are destroyed
	
	private GamePaintComponent gamePaintComponent;
	private Dimension gameViewDimension;

	// Game status
	private boolean isGameOver = false;
	private boolean isWin = false;
	
	/* Make the collection thread-safe. 
	 * To serialise the access, synchronise on the synchronised collection set 
	 */
	// LabSprite collections
	private List<GameSprite> labSpriteList = Collections.synchronizedList(new ArrayList<GameSprite>());  
	private BufferedImage labSpriteImage = null;
	private BufferedImage powerSpritpeImage = null;
	private Clip powerSpriteAudioClip = null;
	// BulletSprite collection
	private List<GameSprite> bulletSpriteList = Collections.synchronizedList(new ArrayList<GameSprite>());  
	// Weapon sprite
	private WeaponSprite weapon;
	
	private GameView gameView;
	private GameEngine gameEngine;

	/**
	 * The constructor to create the game
	 * 
	 * @param gameView The GameView for the game
	 */
	public LabShootingGame(GameView gameView){

		this.gamePaintComponent = gameView.getGamePaintComponent();
		
		this.gameView = gameView;
		
		gameEngine = GameEngine.getGameEngine(this);
				
		// Operating the game with keyboard
		this.gameView.addKeyListener(this);

		gameViewDimension = gameView.getGamePaintComponent().getSize();

		// Loading resources
		initGame();
		
		// Set up the game
		gamePaintComponent.addSprites(labSpriteList);		// Let the GameView know what sprites to paint
		addAllLabSprite();									// Use a thread to create sprites at a specific rate
		
		addWeapon();										// There is only one weapon in this game; it is added to the GameView as well
		
		gamePaintComponent.addSprites(bulletSpriteList);	// Let the GameView know what sprites to paint
		
	}

	
	/* Game logic */
	/**
	 * The method initiates the game by loading image/audio resources etc.
	 */
	private void initGame(){
		
		// load the image for LabSprite
		try {
			labSpriteImage = ImageIO.read(new File("Media/java.png"));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		// load the image for PowerSprite
		try {
			powerSpritpeImage = ImageIO.read(new File("Media/Power.png"));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	/**
	 * The method detects collisions
	 */
	private void detectCollision() {
		// Collision detection
		/* 
		 * A bullet hits the target: 
		 *   if the bounds of the target intersects the bounds of the bullet as shown
		 * 
		 * 			Bounds of a target	
		 *              +------------+
		 * 	            |   target   |
		 * 				|	   		 |
		 * 				|     +-+    |
		 *              +-----|-|----+  
		 *                    | |  Bounds of a bullet
		 *                    +-+
		 *                   bullet  
		 *                   
		 *   Bounds are the bounding rectangle of the sprite entity
		 */

		synchronized(labSpriteList){
			for(GameSprite aLabSprite: labSpriteList){
				synchronized (bulletSpriteList){
					for(GameSprite aBullet: bulletSpriteList){
						if(aBullet.getBounds().intersects(aLabSprite.getBounds())){
							aLabSprite.setCollided(); // If hit, set the LabSprite as collided
							aBullet.setCollided(); // The bullet sacrifices as well
						}
					}
				}
			}
		}

	}
	
	
	/**
	 * The method checks the game status
	 * 
	 *   GameOver if 1) a LabSprite survives
	 *               2) all LabSprites are destroyed, including the PowerSprite
	 */
	private void checkGameStatus(){
		
		// Check if any LabSprite survived; if yes, the game is over with the player lost
		synchronized(labSpriteList){
			for(GameSprite aLabSprite: labSpriteList){
				if (((LabSprite)aLabSprite).isSurvived()) {
					isWin=false;
					endGame();
				}
			}
		}
		
		// Check if all LabSprite are destroyed; if yes, the game is over with the player won
		if(labSpriteList.size()==0 && numberOfLabSpritesCreated==NUMBER_OF_LABS) {
			isWin=true;

			if(!isPowerSpriteEnabled) endGame();
			else {						// when a power sprite is enabled
				createPowerSprite();
				isPowerSpriteEnabled=false;
			}
		}
	}
	
	
	/**
	 * This method cleans up the game (removing all sprites), stop the game engine then display the game over screen
	 */
	private void endGame(){
		
		isGameOver = true;
		removeAllSprites(); 

		gameEngine.stop();
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				gameView.remove(gameView.getGamePaintComponent());
				JComponent gameOverScreen = new GameOverScreen(isWin);
				gameOverScreen.setPreferredSize(gameView.getGamePaintComponent().getSize());
				
				// There are 4 different game over screens shown at random. 
				gameView.add(gameOverScreen, BorderLayout.CENTER);
				gameView.validate();
			}
		});

	}
	
	
	/* Implement KeyListener */
	@Override
	/**
	 * KeyEvent handler for KeyPressed
	 * 
	 *   SpaceBar: fire a bullet
	 *   Ctl-C or Esc: terminate the game
	 *   Left/Right arrow keys: pass it to weapon
	 */
	public void keyPressed(KeyEvent e){
		int keyCode = e.getKeyCode();
	
		switch (keyCode) {

		// Fire a bullet when the space bar is pressed
		case KeyEvent.VK_SPACE:
			fire();
			break;

		// Terminate the program when Ctl-C or Esc key is pressed
		case KeyEvent.VK_C:
		case KeyEvent.VK_ESCAPE:
			endGame();
			System.exit(0);

		// Pass the arrow key events to the weapon
		// to move the weapon when the left or right arrow key is pressed
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_RIGHT:  
			weapon.keyPressed(e);
		}    
	}

	@Override
	/**
	 * KeyEvent handler for KeyReleased
	 * 
	 *   When the key is released, pass it to weapon to stop moving
	 */
	public void keyReleased(KeyEvent e){
		int keyCode = e.getKeyCode();

		switch (keyCode) {
		// Pass the arrow key events to the weapon
		// to stop the weapon moving when the left or right arrow key is released
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_RIGHT:   
			weapon.keyReleased(e);  
		}
	}

	@Override
	/**
	 * KeyEvent handler
	 * 
	 *  Not used in this program
	 */
	public void keyTyped(KeyEvent arg0) {
		// Don't care, do nothing
	}

	/* Implement GameUpdater */
	@Override
	/**
	 *  This method returns the GameView's GamePaintComponent
	 */
	public GamePaintComponent getGamePaintComponent(){
		return gamePaintComponent;
	}
	
	@Override
	/**
	 * The method to implement the GameUpdater interface
	 *   To be called by the GameEngine to update the game at the specified rate
	 */
	public void updateGame() {
		
		if(!isGameOver) {
			detectCollision();
			removeAllDeadSprites();
			checkGameStatus();
		}
		
	}
	
	
	/* Helper methods */
	/**
	 *  The method creates the weapon sprite
	 */
	private void addWeapon(){

		// Create the weapon (a weapon sprite thread is created)
		weapon = new WeaponSprite(gameViewDimension);
		new Thread(weapon).start();
		
		// Create a collection for it and add it to the gamePaintComponent for paiting
		List<GameSprite> weaponSet = Collections.synchronizedList(new ArrayList<GameSprite>()); 
		weaponSet.add(weapon);
		gamePaintComponent.addSprites(weaponSet);

	}

	/**
	 * This method creates a thread to create LabSprite at a specified rate
	 */
	private void addAllLabSprite(){
		
		gamePaintComponent.addSprites(labSpriteList);
		
		// Create a thread to run an anonymous inner runnable task class to create Lab Sprite threads
		new Thread() {
			
			// The string to draw to the image
			final String aString = "Lab";
					
			BufferedImage labelLabImage(int i){
				
				/* Draw a string to an image */
				// Prepare a blank buffered image with the same size of the original
				BufferedImage labelledImage= new BufferedImage(labSpriteImage.getWidth(), labSpriteImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
				// Create a graphic object for drawing
				Graphics2D g2d = labelledImage.createGraphics();
				// Copy to the original image
				g2d.drawImage(labSpriteImage, 0, 0, null);
				// Set drawing properties
				g2d.setColor(Color.BLUE);
				g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
				// Draw the string to the image with a serial number
				g2d.drawString(aString+(i+1), 55, 57);
				g2d.dispose();
				
				return labelledImage;
			}
			
			@Override
			/** 
			 * Runnable task
			 */
			public void run() {
			
				int labSpriteCreation_Interval = (int)(1000./LABSPRITE_CREATION_RATE);
				
				// Create a specified number of Lab Sprite threads
				for(int i=0;i<NUMBER_OF_LABS;i++){

					// Create the Lab Sprite with the image written with the lab number 
					LabSprite aLabSprite = new LabSprite(labelLabImage(i), gameViewDimension);
						labSpriteList.add(aLabSprite);
					new Thread(aLabSprite).start();
					/* The game (loop) must be aware whether all lab sprites are created
					 * 
					 * The game loop checks if all lab sprites are destroyed, that is, the lab sprite set is empty. 
					 * if all lab sprites are destroyed but more sprites to be created, the game is not over
					*/
					// Count the number of Lab sprites created so far
					numberOfLabSpritesCreated++;

					// Create thread to run the Lab Sprite
					try {
						// Wait the specified time interval to create the next Lab Sprite
						Thread.sleep(labSpriteCreation_Interval);  // in milliseconds
					} catch (InterruptedException e) {
						System.err.println(e.getMessage());
					}
				}				
			}
		}.start();
	}
	
	/**
	 * The method creates a bullet sprite
	 *   called by the key event handler when the space bar is pressed
	 */
	private void fire(){

		// Create a bullet (a bullet sprite thread is created)
		BulletSprite aBullet = new BulletSprite(weapon.getFireLocation(), gameViewDimension);
		new Thread(aBullet).start();

		// Add it to the collection for painting
		synchronized(bulletSpriteList) {
			bulletSpriteList.add(aBullet);
		}

	}
	
	/**
	 * This method sets all sprites as dead to let the threads finish
	 */
	private void removeAllSprites(){

		// Set all Lab Sprite as dead
		synchronized(labSpriteList){
			for(GameSprite aLabSprite:labSpriteList){
				aLabSprite.setDead();
			}
		}

		// Set all Bullet as dead
		synchronized(bulletSpriteList){
			for(GameSprite aBulletSprite:bulletSpriteList){
				aBulletSprite.setDead();
			}
		}

		// Set the weapon as dead
		if(weapon !=null) weapon.setDead();
	}

	/**
	 * The method removes all dead sprites from sprite collections
	 */
	private void removeAllDeadSprites() {
		
		// Elements cannot be removed from the collection while being traversed through.
		// So, create a buffer collection to hold all dead sprites
		List<Runnable> deadSet = new ArrayList<Runnable>();  // This collection is not shared; no need for synchronization
	
		// Find all dead LabSprites and remove
		synchronized(labSpriteList) {
			for(GameSprite aLabSprite:labSpriteList){
				if(aLabSprite.isDead()) deadSet.add(aLabSprite);
			}
			labSpriteList.removeAll(deadSet); 		// Remove all dead
		}
	
		// Clear the buffer collection for reuse
		deadSet.clear();
	
		// Find all dead BulletSprites and remove
		synchronized(bulletSpriteList) {
			for(GameSprite aBulletSprite:bulletSpriteList){
				if(aBulletSprite.isDead()) deadSet.add(aBulletSprite);
			}
			bulletSpriteList.removeAll(deadSet);		// Remove all dead
		}
	}
		
	/** 
	 *  The method to create a PowerSprite, if enabled
	 */
	private void createPowerSprite(){

		/* Ex.4.4 put your code here */

	}

}
