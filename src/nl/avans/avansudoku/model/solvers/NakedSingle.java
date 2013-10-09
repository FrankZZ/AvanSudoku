package nl.avans.avansudoku.model.solvers;

import java.util.ArrayList;

import nl.avans.avansudoku.model.GameState;
import nl.avans.avansudoku.model.Tile;


public class NakedSingle implements SolverTechnique
{
	private static NakedSingle ns = new NakedSingle();
	
	private ArrayList<Integer> pendingTiles = new ArrayList<Integer>();
	
	public NakedSingle()
	{
		// Alle tiles pending
		for (int i = 0; i < 81; i++)
			pendingTiles.add(i);
	}

	public static NakedSingle getInstance()
	{
		return ns;
	}
	
	// Elke iteratie solved hij tiles die 1 candidate hebben
	// Als er geen tiles gesolved worden dan is de hele puzzel unsolvable
	@Override
	public boolean solve(GameState gameState)
	{
		// Om bij te houden of we er een gevonden hebben, anders unsolvable
		boolean solvedTile = false;
		
		for (int i = 0; i < pendingTiles.size(); i++)
		{
			Tile tile = gameState.getTile(pendingTiles.get(i));
			
			// Single candidate?
			if (tile.getCandidateCount() == 1)
			{
				// De enige candidate verkrijgen en setten als value
				for (int j = 0; j < 9; j++)
				{
					if (tile.getCandidate(j))
					{
						gameState.setTileValue(i, j);
						
						// Tile is niet meer pending
						pendingTiles.remove(i);

						// Index compenseren
						i--;
						
						solvedTile = true;
					}
				}
			}
		}
		
		if (solvedTile == false)
			return false; // Unsolvable. Er is deze iteratie geen enkele tile gesolved, in de volgende iteraties dus ook niet
		 
		// Zijn er nog tiles die gesolved moeten worden?		
		if (pendingTiles.size() == 0)
			return true; // Nee, alles solved -> return true
		else
			return solve(gameState); // Ja, volgende iteratie
	}

}
