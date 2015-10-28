package game.main;

import game.view.*;

/* This software is designed and created for the purpose of applying some learnt concepts and
*   techniques in Java programming for lab exercises.
*   
*  It is neither created as a model for Java game programming nor optimised for performance. 
*/

/** 
 *  The interface to specify the GameUpdater class interface<br>
 *  <br>
 *  A GameUpdater implementation must provide <br><br>
 *  <ol>
 *      <li> a method to update the game</li>
 *      <li> a method to return the GamePaintComponent </li>
 *  </ol>   
 *  
 * @author Lei Ye
 *
 */
public interface GameUpdater {
	
	/**
	 * The method updates the game (collision detection, game status etc.)
	 */
	void updateGame();
	
	/**
	 * The method returns the GamePaintComponent that the GameEngine uses to repaint the screen
	 * @return
	 */
	GamePaintComponent getGamePaintComponent();
}
