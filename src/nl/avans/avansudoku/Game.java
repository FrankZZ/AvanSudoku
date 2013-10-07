package nl.avans.avansudoku;

import nl.avans.avansudoku.model.SudokuGameState;
import nl.avans.avansudoku.model.Tile;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
 
public class Game
{

	@SuppressWarnings( "unused" )
	private Context ctx;
	private int count = 0;
	private boolean done = false;

	public Game( Context ctx )
	{
		this.ctx = ctx;
		
		
		
		for (int i = 0; i < 100; i++)
		{
			long start = System.currentTimeMillis();

			while( done == false )
			{
				try
				{
					startGame();
					count++;
				}
				catch( Throwable e )
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			}
			Log.i( "SUDOKU SOLVED ", "Took me "
					+ ( System.currentTimeMillis() - start ) + "ms in " + count
					+ " tries." );
			count = 0;
			done = false;
		}

	}

	public void startGame() throws Throwable
	{
		SudokuGameState gameState = new SudokuGameState();
		String str = "";

		int i = 0;
		int timesReverted = 0;

		while( i < ( 9 * 9 ) && timesReverted < 3 )
		{
			int x = i % 9;
			int y = i / 9;

			Integer poss;
			try
			{
				poss = gameState.getRandomOption( x, y );
				gameState.setTile( x, y, new Tile( x, y, poss.intValue(), true, 1 ) );
				str += poss;
			}
			catch( Exception e )
			{
				// Log.e("Failure", "Collission detected. Tried " +
				// timesReverted + " already");

				// failed dus 3 stapjes terug

				for( int j = 0; j < 3; j++ )
				{
					i--;
					int xx = i % 9;
					int yy = i / 9;
//					gameState.setTile( new Tile( xx, yy, 0, true ) );

				}
				
				timesReverted++;
				continue;

			}
			str += "\n";

			i++;
		}

		// System.out.println(str);
		// Log.d("STATE", str);

		if( timesReverted < 3 )
			done = true;

		// Toast.makeText(ctx, str, Toast.LENGTH_LONG).show();
	}

}
