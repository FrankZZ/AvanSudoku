package nl.avans.avansudoku.control;

import java.util.Random;

import android.util.Log;
import nl.avans.avansudoku.model.*;
import nl.avans.avansudoku.model.solvers.NakedSingle;
import nl.avans.avansudoku.model.solvers.SudokuSolver;

public class SudokuCreator implements GameCreator
{
	private SudokuGameState	gameState;

	public SudokuCreator()
	{
		CreateGame();
	}

	@Override
	public void CreateGame()
	{
		//generateTiles();
		createDemoField();
		digHoles(1); //1 for very easy
	}

	public Tile[] createDemoField()
	{
		Tile[] demoField = new Tile[81];
		int[]	sudokuContent	= { 1, 4, 5, 3, 2, 7, 6, 9, 8, 8,
				3, 9, 6, 5, 4, 1, 2, 7, 6, 7, 2, 9, 1, 8, 5, 4, 3, 4, 9, 6, 1, 8,
				5, 3, 7, 2, 2, 1, 8, 4, 7, 3, 9, 5, 6, 7, 5, 3, 2, 9, 6, 4, 8, 1,
				3, 6, 7, 5, 4, 2, 8, 1, 9, 9, 8, 4, 7, 6, 1, 2, 3, 5, 5, 2, 1, 8,
				3, 9, 7, 6, 4};
		
		for(int i = 0; i < 81; i++)
		{
			demoField[i] = new Tile(i, sudokuContent[i], false, 0);
		}
		return demoField;
	}

	public void generateTiles()
	{
		gameState = new SudokuGameState();

		int i = 0;
		int lastRevert = -1;
		int timesReverted = 0;
		
		while (i < (9 * 9))
		{

			int x = i % 9;
			int y = i / 9;

			int candidate;
			try
			{
				candidate = getRandomCandidate(x, y);

				
				Log.d("vv", "[" + x + "][" + y + "] " + Integer.toString(candidate));
				gameState.setTileValue(x, y, candidate);
			}
			catch (Exception e)
			{
				if (lastRevert == i && timesReverted > 2)
				{
					Log.e("Sudoku", "I'm stuck!");
					
					gameState = new SudokuGameState();
					
					i = 0;
					lastRevert = -1;
					
					continue;
				}
				
				lastRevert = i;
				timesReverted++;
				
				Log.e("SUDOKU", "REVERTING AT " + i);
				// Geen candidates: failed dus 3 stapjes terug
				for (int j = 0; j < 9; j++)
				{
					i--;
					int xx = i % 9;
					int yy = i / 9;
					gameState.setTileValue(xx, yy, 0);
				}
				continue;
			}
			i++;
		}
	}

	public int getRandomCandidate(int x, int y) throws Exception
	{
		Tile tile = gameState.getTile(x, y);

		// Geef een exceptie als er geen candidaten meer zijn voor deze cel/tegel
		if (tile.getCompCandidateCount() == 0)
		{
			Log.e("CREATOR", "SudokuCreator::getRandomOption(): No candidates left for ("
							+ x + ", " + y + ")");
			
			throw new Exception(
					"SudokuCreator::getRandomOption(): No candidates left for ("
							+ x + ", " + y + ")");
		}
		Random randomGenerator = new Random();

		// Een random slot dat op true staat brute-forcen, het zijn tenslotte
		// maar 9 slots... ;-)
		while (true)
		{
			int randomInt = randomGenerator.nextInt(9) + 1; // van 1 t/m 9

			// Is het een candidate?
			if (tile.isCompCandidate(randomInt) == true)
				return randomInt; // while afgebroken
			}
		}

	public void digHoles(int difficultyLevel)
	{
		SudokuSolver suSolver = SudokuSolver.getInstance();
		Random rand = new Random();

		suSolver.setDifficultyLevel(difficultyLevel);
		suSolver.solve(gameState);

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

				if (suSolver.solve(gameState))
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
