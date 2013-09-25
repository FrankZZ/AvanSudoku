package nl.avans.avansudoku.model;

import java.util.EmptyStackException;
import java.util.Stack;

public class SudokuGameState implements GameState
{
	private int[] _tiles = new int[9 * 9];
	
	private Tile[] _startTiles;
	private Stack<Tile> _undoStack;

	@Override
	public SudokuGameState addStartState(Tile[] tiles)
	{
		// Start state
		if (this._startTiles == null)
		{
			this._startTiles = tiles;
		}
		
		return this;
	}

	@Override
	public SudokuGameState updateCurrentState(Tile tile) throws EmptyStackException
	{
		// De bovenste op de stack updaten.
		if (_undoStack.peek() != null)
		{
			_undoStack.pop();
			_undoStack.add(tile);
		}
		
		return this;
	}

	@Override
	public SudokuGameState addUndoAction(Tile tile)
	{
		// Toevoegen bovenop de stack.
		_undoStack.add(tile);
		
		return this;
	}

	@Override
	public SudokuGameState resetToStartState()
	{
		_undoStack.clear();
		
		return this;
	}
	
	@Override
	public Tile retrieveUndoAction() throws Throwable
	{
		return new Tile( 0, 0, 0, 1 );
	}

	private int getTile( int x, int y )
	{
		return _tiles[( y * 9 ) + x];
	}

	public boolean[] getPossibilities( int x, int y )
	{
		// Standaard alles false, alle onmogelijke nummers op true zetten
		boolean[] possibilities = new boolean[9];

		for( int i = 0; i < 9; i++ )
		{
			int tileX = getTile( i, y );
			int tileY = getTile( x, i );

			// Niet zichzelf tegenkomen en tile niet leeg
			if( i != x && tileX > 0 )
				possibilities[tileX - 1] = true;

			if( i != y && tileY > 0 )
				possibilities[tileY - 1] = true;
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

				int tile = getTile( xx, yy );

				// Tile niet leeg
				if( tile > 0 )
					possibilities[tile - 1] = true;
			}
		}

		return possibilities;
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
	public Tile[] getBlock(int x, int y)
	{
		@SuppressWarnings("unused")
		int index = (y * 9) + x;
		
		/* if (_tiles.length >= index)
		{
			getPossibilities moet Tile array[] returnen.
		} */
		
		return null;
	}

	@Override
	public void setBlock( int atX, int atY, Tile[] modifiedTiles )
	{
		// Stub
	}
}
