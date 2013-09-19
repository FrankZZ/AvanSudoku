/**
 * 
 */
package control;

import model.GameState;
import model.SudokuGameState;


/**
 * @author Rick van Son
 * @version 1.0
 * @since 2013-09-18
 *
 */
public class GameManager
{

	private static GameManager gameManagerSingleton;
	
	private GameState currentGameState;
	
	/**
	 * Constructor which is private because this is a singleton.
	 */
	private GameManager()
	{
		/// I guess an apart factory returning SudokuGameState boxed in interface GameState
		/// is a better idea..
		this.currentGameState = new SudokuGameState();
	}
	
	/**
	 * @return A instance of this class. 
	 */
	public static GameManager getInstance()
	{
		if( gameManagerSingleton == null )
		{
			gameManagerSingleton = new GameManager();
		}
		return gameManagerSingleton;
	}
	
	/**
	 * @return The Current Game State of this Game.
	 */
	public GameState getGameState()
	{
		return this.currentGameState;
	}
	
	/**
	 * @param newGameState Replace the game state with another one. 
	 */
	public void setGameState( GameState otherGameState )
	{
		this.currentGameState = otherGameState;
	}
	
}
