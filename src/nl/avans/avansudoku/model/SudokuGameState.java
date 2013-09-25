package nl.avans.avansudoku.model;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Random;
import java.util.Stack;

public class SudokuGameState implements GameState
{
	private Tile[] tiles = new Tile[9 * 9];

	private Tile[] startTiles;
	private Stack<Tile> undoStack;

	@Override
	public SudokuGameState addStartState( Tile[] tiles )
	{
		// Start state
		if( this.startTiles == null )
		{
			this.startTiles = tiles;
		}

		return this;
	}

	@Override
	public SudokuGameState updateCurrentState( Tile tile )
			throws EmptyStackException
	{
		// De bovenste op de stack updaten.
		if( undoStack.peek() != null )
		{
			undoStack.pop();
			undoStack.add( tile );
		}

		return this;
	}

	@Override
	public SudokuGameState addUndoAction( Tile tile )
	{
		// Toevoegen bovenop de stack.
		undoStack.add( tile );

		return this;
	}

	@Override
	public SudokuGameState resetToStartState()
	{
		undoStack.clear();

		return this;
	}

	@Override
	public Tile retrieveUndoAction() throws Throwable
	{
		return new Tile( 0, 0, 0, 1, false );
	}

	public void setTile( int x, int y, Tile tile )
	{
		tiles[( y * 9 ) + x] = tile;
	}

	public Tile getTile( int x, int y )
	{
		return tiles[( y * 9 ) + x];
	}

	public ArrayList<Integer> getPossibilities( int x, int y )
	{
		// Alle gebruikte tiles op true
		boolean[] possibilities = new boolean[9];
		Integer tileIdx = 0;

		for( int i = 0; i < 9; i++ )
		{
			Tile tileX = getTile( i, y );
			Tile tileY = getTile( x, i );

			// Niet zichzelf tegenkomen en tile niet leeg
			if( i != x && tileX.getX() > 0 )
			{
				possibilities[tileX.getX() - 1] = true;
				tileIdx++;
			}
			if( i != y && tileY.getY() > 0 )
			{
				possibilities[tileY.getY() - 1] = true;
				tileIdx++;
			}

		}

		int blockStartX = ( x / 3 ) * 3;
		int blockStartY = ( y / 3 ) * 3;

		for( int xx = blockStartX; xx < blockStartX + 3; xx++ )
		{
			for( int yy = blockStartY; yy < blockStartY + 3; yy++ )
			{
				// Niet zichzelf tegenkomen
				if( xx == x && yy == y )
					continue;

				Tile tile = getTile( xx, yy );

				// Tile niet leeg
				if( tile.getValue() > 0 )
				{
					possibilities[tile.getIndex() - 1] = true;
					tileIdx++;
				}
			}
		}

		// Vertalen naar array met ONgebruikte tiles
		ArrayList<Integer> arr = new ArrayList<Integer>();

		for( int i = 0; i < possibilities.length; i++ )
		{
			boolean p = possibilities[i];

			if( p == false )
			{
				arr.add( i + 1 );
			}
		}
		// Log.e("arl", arr.size() + "");

		return arr;
	}

	public int getRandomOption( int x, int y ) throws Exception
	{
		ArrayList<Integer> poss = getPossibilities( x, y );

		Random randomGenerator = new Random();

		if( poss.size() == 0 )
			throw new Exception( "ERROR NO POSSIBILITIES, UNDO! " + x + " " + y );

		int randomInt = randomGenerator.nextInt( poss.size() );

		return poss.get( randomInt );
	}

	@Override
	public Tile[] getRow( int atX )
	{
		return null;
	}

	@Override
	public void setRow( int atX, Tile[] modifiedTiles )
	{
		// Stub
	}

	@Override
	public Tile[] getColumn( int atY )
	{
		return null;
	}

	@Override
	public void setColumn( int atY, Tile[] modifiedTiles )
	{
		// Stub
	}

	@Override
	public Tile[] getBlock( int x, int y )
	{
		@SuppressWarnings( "unused" )
		int index = ( y * 9 ) + x;

		/*
		 * if (tiles.length >= index) { getPossibilities moet Tile array[]
		 * returnen. }
		 */

		return null;
	}

	@Override
	public void setBlock( int atX, int atY, Tile[] modifiedTiles )
	{
		// Stub
	}
}
