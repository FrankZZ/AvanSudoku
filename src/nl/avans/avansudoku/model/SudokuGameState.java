package nl.avans.avansudoku.model;

import java.util.Stack;

public class SudokuGameState implements GameState
{
	private Stack<Tile> undoStack;

	@Override
	public void addStartState()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCurrentState()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void addUndoAction()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Tile retrieveUndoAction() throws Throwable
	{
		// TODO Auto-generated method stub

		return new Tile( 0, 0, 0, 1 );

	}

	// 9 bij 9 blokken = 81 plaatsen
	private int[] _tiles = new int[9 * 9];

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRow( int atX, Tile[] modifiedTiles )
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Tile[] getColumn( int atY )
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setColumn( int atY, Tile[] modifiedTiles )
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Tile[] getBlock( int atX, int atY )
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBlock( int atX, int atY, Tile[] modifiedTiles )
	{
		// TODO Auto-generated method stub

	}

}
