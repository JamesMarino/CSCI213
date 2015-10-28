package game.main;

import game.view.GamePaintComponent;

/* This software is designed and created for the purpose of applying some learnt concepts and
*   techniques in Java programming for lab exercises.
*   
*  It is neither created as a model for Java game programming nor optimised for performance. 
*/

/**
 * 	The Game Engine - a Singleton class<br>
 *  <br>
 *   There must be one single GameEngine to drive the game.<br>
 *   To get the GameEngine, invoke the static method: getGameEngine(gameUpdater)
 * 
 * @author Lei Ye
 *
 */
public class GameEngine implements Runnable{

	/*
	 * 	As monitors refreshes at 50-100Hz, no point to go beyond 100 updates per second
	 *  For games that their moves are defendant on user inputs, update at the same rate 
	 *    as players' response, normally 1-5 updates per second.
	 * 
	 */
	private final static int UPDATE_RATE = 100; 						// 100 updates (frames or refreshes) / second
	private final static long UPDATE_INTERVAL = 1000000000L / UPDATE_RATE; 	// in nanoseconds

	private static boolean isGameOver = false;
	private static boolean isGamePaused = false;

	private static final GameEngine gameEngine_instance = new GameEngine();
	private static Thread gameEngineThread;

	private static GamePaintComponent gamePaintComponent = null;
	private static GameUpdater gameUpdater = null;

	/*
	 *  Make it private to prevent instantiation without a GameUpdater
	 */
	private GameEngine()	
	{
		//Exists only to prevent public instantiation.
	}

	/**
	 * The method returns the GameEngine instance
	 * 
	 * @param gameUpdater The GameUpdater - the class implements game logic
	 * 
	 * @return The GameEngine instance
	 */
	public static GameEngine getGameEngine(GameUpdater gameUpdater){

		gamePaintComponent = gameUpdater.getGamePaintComponent();
		GameEngine.gameUpdater = gameUpdater;
		
		// Create a thread to run the Game loop automatically
		if (gamePaintComponent != null && gameUpdater != null)	// Doesn't make sense without a Game paint component and updater
		{
			gameEngineThread = new Thread(gameEngine_instance);
			gameEngineThread.setName("GameEngine");
			gameEngineThread.start();
		} 

		return gameEngine_instance;
	}
		
	/**
	 * The method stops the game engine
	 */
	public void stop(){
		isGameOver = true;
	}
	
	/**
	 * The method pauses the game engine
	 */
	public void pause(){
		isGamePaused = true;
	}
	
	/**
	 * The method resumes the paused game engine
	 */
	public void resume(){
		isGamePaused = false;
	}

	@Override
	/**
	 * This is the runnable task - the game loop to drive the game - update the game and repaint the screen
	 */
	public void run() {
		
		long startTimeEachUpdate, timeElapsedEachUpdate, timeLeftEachUpdate;

		// Give a chance for the frame and game to set up in other threads
		//  before starting the game loop, or null point error may occur
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}

		while(!isGameOver){
			
			startTimeEachUpdate = System.nanoTime();

			if(!isGamePaused){
				
				// Update the game (collisions and game status etc.)
				gameUpdater.updateGame();

				// Redraw the screen; critical to drive the game animation
				// Each repaint will draw all live sprites at their new locations
				gamePaintComponent.repaint();
			}
			
			/*
			 * 	Control the update rate
			 */
			// Calculate the time left in each loop (update) 
			timeElapsedEachUpdate = System.nanoTime() - startTimeEachUpdate;
			timeLeftEachUpdate = (UPDATE_INTERVAL - timeElapsedEachUpdate) / 1000000L;  // in minisecond 
			if(timeLeftEachUpdate < 1) timeLeftEachUpdate = 1;  // The machine is busy/slow; timeEpalsedEachUpdate is longer than the desired update interval
																//   Still set a small interval to yield the control to allow other threads to run
			try {
				Thread.sleep(timeLeftEachUpdate);				// Wait a specified time period; also give other threads a chance 
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
	}
}