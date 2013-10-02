package model;

import java.util.Stack;

public class SudokuGameState implements GameState
{
	private Stack<Tile> undoStack;
	private Tile[] tiles = new Tile[81];
	private Tile[] startTiles = new Tile[81];

	@Override
	public void addStartState()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setStartState( double[] tileLijst) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Tile[] getStartState()
	{
		return startTiles;
	}
	@Override
	public Boolean updateCurrentState(double[] tileLijst)
	{
		// TODO Auto-generated method stub
		for (int i = 0; i < tileLijst.length; i++) 
		{
			tiles[i] = new Tile(i, tileLijst[i]);
		}
		// als mogelijk
		return true;
	}

	@Override
	public Boolean checkNewState(double[] tileLijst, Tile tile) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public Tile askHintAction()
	{
		return new Tile(2,5);		
	}
	@Override
	public void addUndoAction(Tile prev)
	{
		// TODO Auto-generated method stub
		undoStack.push(prev);
	}

	@Override
	public Tile retrieveUndoAction()
	{
		if(undoStack.isEmpty())
		return undoStack.pop();
		else
			return null;
	}

	// 9 bij 9 blokken = 81 plaatsen
	private int[]	_tiles	= new int[9 * 9];

	private int getTile(int x, int y)
	{
		return _tiles[(y * 9) + x];
	}

	public boolean[] getPossibilities(int x, int y)
	{
		// Standaard alles false, alle onmogelijke nummers op true zetten
		boolean[] possibilities = new boolean[9];

		for (int i = 0; i < 9; i++)
		{
			int tileX = getTile(i, y);
			int tileY = getTile(x, i);

			// Niet zichzelf tegenkomen en tile niet leeg
			if (i != x && tileX > 0)
				possibilities[tileX - 1] = true;

			if (i != y && tileY > 0)
				possibilities[tileY - 1] = true;
		}

		int blockStartX = (x / 3) * 3;
		int blockStartY = (y / 3) * 3;

		for (int xx = blockStartX; xx < blockStartX + 3; xx++)
		{
			for (int yy = blockStartY; yy < blockStartY + 3; yy++)
			{
				// Niet zichzelf tegenkomen
				if (xx == x && yy == y)
					continue;

				int tile = getTile(xx, yy);

				// Tile niet leeg
				if (tile > 0)
					possibilities[tile - 1] = true;
			}
		}

		return possibilities;
	}
}
