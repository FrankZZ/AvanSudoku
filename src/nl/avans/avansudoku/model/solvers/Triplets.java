package nl.avans.avansudoku.model.solvers;

import nl.avans.avansudoku.model.GameState;
import nl.avans.avansudoku.model.Tile;

/**
 * @author Jim Franke
 * @version 1.0
 */

public final class Triplets implements SolverTechnique
{
	// Single instance
	private static Triplets instance;
	
	// Constructor
	private Triplets()
	{
		// Stub
	}
	
	// Singleton
	public static Triplets getInstance()
	{
		if (instance == null)
		{
			instance = new Triplets();
		}
		
		return instance;
	}
	
	/*
	 * Solve methode, hier moet nog gebruik gemaakt gaan worden van recursie.
	 */
	
	@Override
	public boolean solve(GameState gamestate)
	{
		for (int i = 0; i < 81; i++)
		{
			Tile tile1 = gamestate.getTile(i);
			
			if (tile1.getAmountOfCandidates() >= 3)
			{
				int colStart = tile1.getX();
				int rowStart = tile1.getY();
				
				Tile tile2 = null;
				
				// Column controleren
				for (int j = colStart; j <= (81 - (9 - colStart)); j += 9)
				{
					tile2 = gamestate.getTile(j);
					
					if (this.has3TheSame(tile2.getCandidates(), tile1.getCandidates()))
					{
						colStart = tile2.getX();
						rowStart = tile2.getY();
						
						Tile tile3 = null;
						
						for (int k = colStart; j <= (81 - (9 - colStart)); k += 9)
						{
							tile3 = gamestate.getTile(j);
							
							if (this.has3TheSame(tile3.getCandidates(), tile1.getCandidates()) 
									&& this.has3TheSame(tile3.getCandidates(), tile2.getCandidates()))
							{
								// Verwijder in de overige cellen van die rij of kolom 
								// de drie kandidaten die met de drie bewuste cellen overeenkomen.
							}
						}
					}
				}
				
				// Rij controleren
				for (int j = rowStart; j <= (rowStart + 9); j++)
				{
					tile2 = gamestate.getTile(j);
					
					if (this.has3TheSame(tile2.getCandidates(), tile1.getCandidates()))
					{
						colStart = tile2.getX();
						rowStart = tile2.getY();
						
						Tile tile3 = null;
						
						for (int k = colStart; j <= (81 - (9 - colStart)); k += 9)
						{
							tile3 = gamestate.getTile(j);
							
							if (this.has3TheSame(tile3.getCandidates(), 
									tile1.getCandidates()) && this.has3TheSame(tile3.getCandidates(), tile2.getCandidates()))
							{
								
							}
						}
					}
				}				
			}
		}
		
		return false;
	}
	
	private boolean has3TheSame(boolean[] one, boolean[] two)
	{
		if (one.length == two.length)
		{
			int matches = 0;
			
			for (int i = 0; i < one.length; i++)
			{
				if (one[i] == two[i])
				{
					matches++;
				}
			}
			
			return (matches >= 3);
		}
		
		return false;
	}
}