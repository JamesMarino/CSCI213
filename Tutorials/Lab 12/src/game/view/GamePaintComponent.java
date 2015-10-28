package game.view;

import game.sprites.GameSprite;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JComponent;

/* This software is designed and created for the purpose of applying some learnt concepts and
*   techniques in Java programming for lab exercises.
*   
*  It is neither created as a model for Java game programming nor optimised for performance. 
*/

@SuppressWarnings("serial")
/**
 * This is the default GamePaintComponent added to the GameView to draw background and sprites<br>
 * <br>
 *  Spites collections are added to list of sprite collection using the method addSprites().
 * 
 * @author Lei Ye
 *
 */
public class GamePaintComponent extends JComponent {

	private List<Collection<GameSprite>> spritesCollections = new ArrayList<Collection<GameSprite>>();

	/**
	 * The method adds a collection of sprites to the GamePaintComponent for drawing in the GameView
	 * 
	 * @param spritesCollection The Collection of sprites to add to the GamePaintComponent
	 */
	public void addSprites(Collection<GameSprite> spritesCollection) {
		spritesCollections.add(spritesCollection);
	}

	/**
	 * The method paints all sprites in the list of sprites collections. 
	 *   You can override this method to provide a custom drawing for sprites
	 *   
	 * @param g2 The graphics context of the GamePaintComponent
	 */
	public void drawAllSprites(Graphics2D g2){
		for(Collection<GameSprite> sprites: spritesCollections){
			synchronized(sprites) {
				for(GameSprite aSprite: sprites){
					aSprite.draw(g2);
				}
			}
		}
	}

	/**
	 * The method paints a white background. 
	 *   You can override this method to provide a custom background.
	 *   
	 * @param g2 The graphics context of the GameView
	 */
	public void drawGameBackground(Graphics2D g2){
		setBackground(Color.WHITE);
		setOpaque(true);
		g2.setColor(getBackground());
		g2.fillRect(0, 0, getWidth(), getHeight());	// A trick to paint JComponent background, 
													//  which is not automatically painted with the background colour
	}
	
	@Override
	/**
	 * Custom drawing for background and sprites
	 */
	public void paintComponent(Graphics g)
	{

		Graphics2D g2 = (Graphics2D) g;

		drawGameBackground(g2);

		drawAllSprites(g2);
	}
}