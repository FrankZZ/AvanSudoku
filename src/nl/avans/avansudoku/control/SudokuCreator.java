package nl.avans.avansudoku.control;

import nl.avans.avansudoku.model.SudokuGameState;
import nl.avans.avansudoku.model.Tile;

public class SudokuCreator implements GameCreator
{
	private int[] sudokuContent = { 1, 4, 5, 3, 2, 7, 6, 9, 8, 8, 3, 9, 6, 5,
			4, 1, 2, 7, 6, 7, 2, 9, 1, 8, 5, 4, 3, 4, 9, 6, 1, 8, 5, 3, 7, 2,
			2, 1, 8, 4, 7, 3, 9, 5, 6, 7, 5, 3, 2, 9, 6, 4, 8, 1, 3, 6, 7, 5,
			4, 2, 8, 1, 9, 9, 8, 4, 7, 6, 1, 2, 3, 5, 5, 2, 1, 8, 3, 9, 7, 6, 4 };

	@Override
	public void CreateGame()
	{
		SudokuGameState gameState = new SudokuGameState();

		// Toast.makeText(ctx, str, Toast.LENGTH_LONG).show();
	}

	private SudokuGameState generateTiles( SudokuGameState gameState )
			throws Throwable
	{
		int i = 0, timesReverted = 0;

		while( i < ( 9 * 9 ) && timesReverted < 3 )
		{
			int x = i % 9;
			int y = i / 9;

			Integer poss;
			try
			{
				poss = gameState.getRandomOption( x, y );
				gameState.setTile( x, y, new Tile( x, y, poss, true, 1 ) );
			}
			catch( Exception e )
			{

				// failed dus 3 stapjes terug

				for( int j = 0; j < 3; j++ )
				{

					i--;
					int xx = i % 9;
					int yy = i / 9;
					gameState.setTile( xx, yy, new Tile( xx, yy, 0, true, 1 ) );

				}

				timesReverted++;
				continue;

			}
			i++;
		}

		return gameState;
	}

}