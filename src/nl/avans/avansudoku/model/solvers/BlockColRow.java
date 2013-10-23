package nl.avans.avansudoku.model.solvers;

import nl.avans.avansudoku.control.SudokuGameRules;
import nl.avans.avansudoku.model.GameState;
import nl.avans.avansudoku.model.Tile;

/**
 * @author Jim Franke
 * @version 1.0
 */

public final class BlockColRow implements SolverTechnique
{
	// Single instance
	private static BlockColRow instance;	
	
	// Constructor
	private BlockColRow()
	{
		// Stub
	}
	
	// Singleton
	public static BlockColRow getInstance()
	{
		if (instance == null)
		{
			instance = new BlockColRow();
		}
		
		return instance;
	}
	
	@Override
	// Oplos methode
	public boolean solve(GameState gamestate)
	{
		Tile[][] blocks = getBlocks(gamestate);
		
		for (Tile[] tiles : blocks)
		{
			for (int i = 0; i < blocks.length; i++)
			{
				if (tiles[i].getValue() > 0)
				{
					Tile startTile = tiles[i];
					
					if (tiles[i + 1].getValue() > 0)
					{
						int rowEnd = startTile.getY() + 3;
						
						for (int j = startTile.getY(); j < rowEnd; j++)
						{
							if (tiles[j].getValue() > 0)
							{
								// Onduidelijk
							}
						}
					}
					else
					{
						if (tiles[i + 2].getValue() > 0)
						{
							
						}
					}
				}
			}
		}

		return false;
	}
	
	// Blokken pakken
	private Tile[][] getBlocks(GameState gamestate)
	{
		Tile[][] tileBlocks = new Tile[9][];
		int blockIndex = 0;
		
		for (int i = 0; i < SudokuGameRules.DEFAULT_AMOUNT_OF_TILES; i += 3)
		{
			if (i == 9 || i == 45 || i == 63)
			{
				if (i < 63) i += 18;
				continue;
			}
			
			Tile tile = gamestate.getTile(i);
			
			if (tile != null)
			{
				int tileX = tile.getX();
				int tileY = tile.getY();
				
				Tile[] tileBlock = gamestate.getBlock(tileX, tileY);
				
				tileBlocks[blockIndex] = tileBlock;
						
				blockIndex++;
			}
		}
		
		return tileBlocks;
	}
}