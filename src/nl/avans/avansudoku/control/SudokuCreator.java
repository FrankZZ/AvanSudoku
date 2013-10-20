package nl.avans.avansudoku.control;

import java.util.Random;

import android.util.Log;

import nl.avans.avansudoku.model.*;
import nl.avans.avansudoku.model.solvers.NakedSingle;

public class SudokuCreator implements GameCreator
{

	private int[]			sudokuContent	= { 1, 4, 5, 3, 2, 7, 6, 9, 8, 8,
			3, 9, 6, 5, 4, 1, 2, 7, 6, 7, 2, 9, 1, 8, 5, 4, 3, 4, 9, 6, 1, 8,
			5, 3, 7, 2, 2, 1, 8, 4, 7, 3, 9, 5, 6, 7, 5, 3, 2, 9, 6, 4, 8, 1,
			3, 6, 7, 5, 4, 2, 8, 1, 9, 9, 8, 4, 7, 6, 1, 2, 3, 5, 5, 2, 1, 8,
			3, 9, 7, 6, 4					};

	private SudokuGameState	gameState;

	public SudokuCreator()
	{
		CreateGame();
	}

	@Override
	public void CreateGame()
	{
		generateTiles();
		//digHoles();
	}

	public void generateTiles()
	{
		gameState = new SudokuGameState();

		int safetyLoopBreaker = 0;

		for (int i = 0; i < (9 * 9) && safetyLoopBreaker < 200; i++)
		{
			//to be sure this never is an endless loop
			safetyLoopBreaker++;

			int x = i % 9;
			int y = i / 9;

			boolean hasCandidates = checkTileForCandidates(x, y);
			if(hasCandidates == true)
			{
				//de huidige tegel heeft candidaten
				int randomCandidate = getRandomCandidate(x,y);
				gameState.setTileValue(x, y, randomCandidate);
			}
			else
			{
				//de huidige tegel heeft geen candidaten
				for(int j = 0; j<3; j++)
				{
					gameState.undoLastAction();
					i--;
				}
			}
		}
		
		if(safetyLoopBreaker == 0)
		{
			Log.w("AvanSudoku","safety loop break activated for the for loop in SudokuCreator -> generateTiles");
		}
	}

	private boolean checkTileForCandidates(int x, int y)
	{
		boolean result = false;
		Tile tile = gameState.getTile(x, y);
		if (tile.getCompCandidateCount() == 0)
			result = false;
		else
			result = true;
		return result;
	}

	public int getRandomCandidate(int x, int y)
	{
		//method variables
		int result = 0;
		boolean[] candidates = gameState.getTile(x, y).getCompCandidates();
		Random randomGenerator = new Random();

		//loop variables + loop
		boolean validCandidateFound = false;
		int safetyLoopBreaker = 0;
		while(validCandidateFound == false && safetyLoopBreaker < 40)
		{
			//to make sure this never becomes an endless loop
			safetyLoopBreaker++;
			
			int randomIndex = randomGenerator.nextInt(candidates.length);

			// Als de random index het een kandidaat is dan het resultaat setten en de loop afbreken
			if (candidates[randomIndex] == true)
			{
				result = randomIndex;
				validCandidateFound = true;
			}
		}

		if (safetyLoopBreaker >= 40)
		{
			Log.w("AvanSudoku","safety loop break activated for while loop in SudokuCreator -> getRandomCandidate");
		}

		return result;
	}

	public void digHoles()
	{
		Random rand = new Random();

		NakedSingle sol = NakedSingle.getInstance();

		int i, j, dug = 0;

		// 20 gaten maken
		while (dug < 20)
		{
			i = rand.nextInt(9);
			j = rand.nextInt(9);
			int k;

			if (gameState.getTile(i, j).getValue() > 0)
			{
				k = gameState.getTile(i, j).getValue();

				// Een gat maken
				gameState.setTileValue(i, j, 0);

				if (sol.solve(gameState))
				{
					dug++;
				}
				else
				{
					// Unsolvable geworden, terugzetten
					gameState.setTileValue(i, j, k);
					gameState.getTile(i, j).setLocked(true);
				}
			}
		}
	}

	public SudokuGameState getGameState()
	{
		return gameState;
	}

}
