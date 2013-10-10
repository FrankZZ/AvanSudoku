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
	
	//code Maurits; bleek achteraf niet nodig te zijn. :( Frank laat de kandidaten automatisch wegstrepen in de gamestate
	//Nog even ter backup bewaren...

	/*private boolean[][] possibilities;
	private int position;
	private Tile currentTile;
	private GameState gameState;

	public boolean solve2(GameState gameState)
	{
		this.gameState = gameState;
		// TODO Auto-generated method stub
		// Kies een cel. Kijk eerst of deze cel al een getal bevat.
		// Zo wel; verwijder alle kandidaten op de waarde van de cel na.
		// Verwijder ook de waarde van de cel uit de kandidaten van de cellen waar hij invloed op heeft.
		// Zo niet; ga ervan uit dat alle tien de getallen erin kunnen.
		// Kijk horizontaal en streep de getallen weg die al in de rij voorkomen.
		// Kijk verticaal en streep de getallen weg die al in die rij voorkomen.
		// Kijk in het 3x3 cel en streep de getallen weg dia al in die rij voorkomen.
		// Herhaal dit tot je een cel tegen komt, waar nog maar een getal ingevuld kan worden.

		//create an array with 9*9 values (one for each tile); each with an array of 9 kandidates.
		//		possibilities = new boolean[9*9][9];
		//		for (int i = 0; i < 81; i++)
		//		{
		//			for (int j = 0; j < 9; j++)
		//			{
		//				possibilities[i][j] = true;
		//			}
		//		}

		//chose a tile
		for (int i = 0; i < 81; i++)
		{
			int x = i%9;
			int y = i/9;
			currentTile = gameState.getTile(x, y);

			//check if current tile has a value and make it act accordingly
			checkTile(x, y);
		}

		return false;
	}

	private void checkTile(int x, int y)
	{
		//check if current tile has a value
		if (currentTile.getValue() != 0)
		{
			int currentValue = currentTile.getValue();

			//if so; set all other values to false.
			for(int i = 0; i < 9; i++ )
			{
				if (i != currentValue)
					currentTile.setCandidate(i+1, false);
			}

			//and remove this value from the other candidatesin the row,
			Tile row[] = gameState.getRow(x);
			for(int i = 0; i < 9; i++)
			{
				if(i != y)
					row[i].setCandidate(x+1, false);
			}

			//column,
			Tile column[] = gameState.getColumn(y);
			for(int i = 0; i < 9; i++)
			{
				if(i != x)
					column[i].setCandidate(x+1, false);
			}
			
			//and block
			Tile block[] = gameState.getBlock(x, y);
			for(int i = 0; i < 9; i++)
			{
				//getBlock methode is niet echt handig... hoe weet ik nu welke Tile ik moet overslaan zonder loop?
				if(i != x)
					block[i].setCandidate(x+1, false);
			}
		}
	}*/
}