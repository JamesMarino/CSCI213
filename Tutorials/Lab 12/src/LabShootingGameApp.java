import game.main.*;

import game.view.GameView;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/* This software is designed and created for the purpose of applying some learnt concepts and
*   techniques in Java programming for lab exercises.
*   
*  It is neither created as a model for Java game programming nor optimised for performance. 
*/

/**
 * 
 * The shooting game for Java lab exercises
 * 
 * @author Lei Ye
 *
 */
public class LabShootingGameApp {

	private final static Dimension GAMEVIEW_DIMENSION = new Dimension(1280,720);	// Define window dimension in windowed mode
																					// The GameView dimension will be the screen size in full screen mode
	/**
	 * Create the Game GUI then start the game
	 */
	/*   1. Create a GameView (the top window)
	 *    - A GamePaintComponent will be created and added to the GameView
	 *      A GamePaintComponent is a JComponent to draw game world - background and sprites 
	 *   3. Create a GameUpdater and add it the GameFrame and GameUpdater to a GameEngine
	 *      As soon as a GameEngine is create, the game starts
	 */
	private static void createAndShowGUI_Then_startGame() {

		// Create a GameView
		GameView gameView = new GameView(GAMEVIEW_DIMENSION);

		// Prepare for full-screen mode
		GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] devices = gEnv.getScreenDevices();

		// Check if the full-screen mode is supported
		// Multi-monitor full-screen mode not yet supported; use the first physical monitor
		boolean isFullScreen = devices[0].isFullScreenSupported();

		//isFullScreen=false;   // Force the windowed mode if desired
								// There are keyboard focus issues in full-screen mode with Java 7u6 and later on OS X 10.7 and later;
								// if the .setVisible(false) workaround bellow does not work, use windowed mode on Macs.
								
								/* ****
								 * 
								 * You have heard the slogan "Write once, run anywhere" that the Java inventors created to 
								 * illustrate the cross-platform benefits of the Java language. You may have also heard 
								 * "Write once, debug everywhere." 
								 * 
								 * I created this code on Windows and tested it on OS X and Linux to have discovered issues on 
								 * each of the platforms relating to Full-screen exclusive mode and sound - a good testimony of
								 * "debug everywhere".
								 * 
								 * Java programs taking advantage of VM-specific features may cause porting problems 
								 * although most of Java applications migrate well between platforms.
								 * 
								 * ****/
				
		if (isFullScreen) {  	// Full-screen exclusive mode
			
			gameView.setUndecorated(true); 		
			devices[0].setFullScreenWindow(gameView);
			
			// set the GamePaintComponent to the full screen dimension
			gameView.getGamePaintComponent().setPreferredSize(new Dimension(devices[0].getFullScreenWindow().getWidth(),devices[0].getFullScreenWindow().getHeight()));

		} else {				// Windowed mode

			gameView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		} 

		// Resize the window to fit the preferred size and layouts of its subcomponents
		gameView.pack();
		
		/* This is a workaround for keyboard focus issues in full-screen mode on Macs */			
		if(System.getProperty("os.name").toLowerCase().startsWith("mac") && isFullScreen){
			gameView.setVisible(false);  
		}
		
		// Display the window on the screen
		gameView.setVisible(true);
	
		/*  After the window (GameView) and painting surface (GamePaintComponent)
		 *     are created, time to start the game
		 *     
		 *     The LabShootingGame class (GameUpdater) that implements game logic needs to know the GameView
		 *     
		 *     GameView consists of GameFrame and GamePaintComponent
		 *     - The GameView is also the user interface where users interact with the game using a Keyboard or Mouse or others
		 *     - Add KeyEvent listener etc. to GameView
		 */
		new LabShootingGame(gameView);
		// Now the game started and ready to play
	}	


	public static void main(String[] args) 
	{
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI_Then_startGame();
			}
		});

	}
}

