package nl.avans.avansudoku.model.solvers;

import java.util.ArrayList;
import nl.avans.avansudoku.model.GameState;
import nl.avans.avansudoku.model.Tile;

public class NakedSingle implements SolverTechnique
{
	private static NakedSingle	ns				= new NakedSingle();

	private ArrayList<Integer>	pendingTiles	= new ArrayList<Integer>();

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
			return false; // Unsolvable. Er is deze iteratie geen enkele tile
		// gesolved, in de volgende iteraties dus ook niet

		// Zijn er nog tiles die gesolved moeten worden?
		if (pendingTiles.size() == 0)
			return true; // Nee, alles solved -> return true
		else
			return solve(gameState); // Ja, volgende iteratie
	}

	public boolean solve2(GameState gamestate)
	{
		// TODO Auto-generated method stub
		// Kies een cel. Ga ervan uit dat alle tien de getallen erin kunnen.
		// Kijk horizontaal en streep de getallen weg die al in de rij voorkomen.
		// Kijk verticaal en streep de getallen weg die al in die rij voorkomen.
		// Kijk in het 3x3 cel en streep de getallen weg dia al in die rij voorkomen.
		// Herhaal dit tot je een cel tegen komt, waar nog maar een getal ingevuld kan worden.

		//create an array with 9*9 values (one for each tile); each with an array of 9 kandidates.
		boolean[][] possibilities = new boolean[9*9][9];
		for (int i = 0; i < 81; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				possibilities[i][j] = true;
			}
		}

		for (int x = 0; x < 9; x++)
		{
			int xPosition = x * 9; //for writing to the single dimension array
			Tile[] currentRow = gamestate.getRow(x);
			for(int y = 0; y < 9; y++)
			{
				int position = xPosition + y;
				Tile currentTile;

				//check if current tile has a value
				currentTile = gamestate.getTile(x, y);
				if (currentTile.getValue() != 0)
				{
					//if so; set all other values to false.
					for(int i = 0; i < 9; i++ )
					{
						if (i != currentTile.getValue())
							possibilities[position][i] = false;
					}
				}
				else
				{
					//if not; check the row and remove candidates
					currentTile = currentRow[x];
					if(currentTile.getValue() != 0);
					{
						for(int i = 0; i < 9; i++)
							possibilities[position][i] = false;
					}

					if(currentTile.getValue() != 0)
					{
						for(int i = 0; i < 9; i++)
							possibilities[position][i] = false;
					}
					Tile[] currentColumn = gamestate.getColumn(y);

					Tile[] currentBlock = gamestate.getBlock(x, y);

				}
			}
		}

		return false;
	}
}