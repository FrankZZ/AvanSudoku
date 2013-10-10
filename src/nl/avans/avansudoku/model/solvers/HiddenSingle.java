package nl.avans.avansudoku.model.solvers;

import java.util.ArrayList;

import nl.avans.avansudoku.model.GameState;
import nl.avans.avansudoku.model.Tile;

public class HiddenSingle implements SolverTechnique
{

	private static HiddenSingle	hs	= new HiddenSingle();

	private ArrayList<Integer> pendingTiles = new ArrayList<Integer>();

	public HiddenSingle()
	{
		// Alle tiles pending
		for (int i = 0; i < 81; i++)
			pendingTiles.add(i);

		// Kijk of er een rij is waar een kandidaat maar een keer voor komt
		// Kijk of er een kolom is waar een kandidaat maar een keer voor komt
		// kijk of er een blok is waar een kandidaat maar een keer voor komt
	}

	public static HiddenSingle getInstance()
	{
		return hs;
	}

	// Elke iteratie solved 'ie tiles die 1 candidate hebben (hidden single)
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
