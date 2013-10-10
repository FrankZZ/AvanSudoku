package nl.avans.avansudoku.model.solvers;

import nl.avans.avansudoku.model.GameState;

/**
 * @author Jim Franke
 * @version 1.0
 */

public final class BlockBlock implements SolverTechnique
{
	// Single instance
	private static BlockBlock instance;	
	
	// Constructor
	private BlockBlock()
	{
		// Stub
	}
	
	// Singleton
	public static BlockBlock getInstance()
	{
		if (instance == null)
		{
			instance = new BlockBlock();
		}
		
		return instance;
	}	
	
	@Override
	public boolean solve(GameState gamestate)
	{
		return false;
	}
}