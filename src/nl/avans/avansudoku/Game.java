package nl.avans.avansudoku;

import nl.avans.avansudoku.control.SudokuCreator;
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
		// TODO: Dit is overbodig, staat al in GameActivity
		/*
		SudokuCreator sudokuCreator = new SudokuCreator();
		sudokuCreator.CreateGame();
		
		SudokuGameState gameState = sudokuCreator.getGameState();
		*/
		// Toast.makeText(ctx, str, Toast.LENGTH_LONG).show();
	}

}
