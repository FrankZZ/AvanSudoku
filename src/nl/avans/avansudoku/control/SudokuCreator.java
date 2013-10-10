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

	public SudokuGameState getGameState()
	{
		return gameState;
	}

	public void generateTiles()
	{
		gameState = new SudokuGameState();

		int i = 0, timesReverted = 0;

		while (i < (9 * 9))
		{

			int x = i % 9;
			int y = i / 9;

			int candidate;
			try
			{
				candidate = getRandomCandidate(x, y);
				Log.d("vv", Integer.toString(candidate)); 
				gameState.setTileValue(x, y, candidate);
			}
			catch (Exception e)
			{
				Log.e("SUDOKU", "aaaa");
				// Geen candidates: failed dus 3 stapjes terug
				for (int j = 0; j < 3; j++)
				{
					i--;
					int xx = i % 9;
					int yy = i / 9;
					gameState.setTileValue(xx, yy, 0);
				}
				timesReverted++;
				continue;
			}
			i++;
		}
	}

	public int getRandomCandidate(int x, int y) throws Exception
	{
		Tile tile = gameState.getTile(x, y);

		// Zijn er nog candidates over?
		if (tile.getCandidateCount() == 0)
			throw new Exception(
					"SudokuCreator::getRandomOption(): No candidates left for ("
							+ x + ", " + y + ")");

		boolean[] candidates = tile.getCandidates();
		Random randomGenerator = new Random();

		// Een random slot dat op true staat brute-forcen, het zijn tenslotte
		// maar 9 slots... ;-)
		while (true)
		{
			int randomInt = randomGenerator.nextInt(candidates.length);

			// Is het een candidate?
			if (candidates[randomInt] == true)
				return randomInt + 1; // while afgebroken
		}
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

}
