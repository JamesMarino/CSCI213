package game.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

/* This software is designed and created for the purpose of applying some learnt concepts and
*   techniques in Java programming for lab exercises.
*   
*  It is neither created as a model for Java game programming nor optimised for performance. 
*/

@SuppressWarnings("serial")
/**
 * This is the top window frame of the GameView that contains the GamePaintComponent
 * 
 * @author Lei Ye
 *
 */
public class GameView extends JFrame{

	private GamePaintComponent gamePaintComponent;
	
	/**
	 * The constructor to create GameView (JFrame) with a GamePaintComponent of the given dimension
	 * 
	 * @param gameViewDimension The dimension of the GameView
	 */
	public GameView(Dimension gameViewDimension){
		
		gamePaintComponent = new GamePaintComponent();
		gamePaintComponent.setPreferredSize(gameViewDimension);
		add(gamePaintComponent, BorderLayout.CENTER);
	}
	
	/**
	 * The method returns the GamePaintComponent of the GameView
	 * 
	 * @return The GamePaintComponent of the GameView
	 */
	public GamePaintComponent getGamePaintComponent(){
		return gamePaintComponent;
	}
	
}
