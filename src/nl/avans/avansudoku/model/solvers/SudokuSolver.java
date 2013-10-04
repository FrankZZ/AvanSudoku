package nl.avans.avansudoku.model.solvers;

import nl.avans.avansudoku.model.GameState;

public class SudokuSolver implements GameSolver {

	public static final int VERY_EASY = 1;
	public static final int EASY = 2;
	public static final int MEDIUM = 3;
	public static final int HARD = 4;
	public static final int VERY_HARD = 5;
	
	private int difficultyLevel;
	
	public SudokuSolver()
	{
		difficultyLevel = 0;
	}
	
	@Override
	public void setDifficultyLevel(int difficultyLevel)
	{
		this.difficultyLevel = difficultyLevel;
	}

	@Override
	public boolean solve(GameState gameState)
	{
		boolean canBeSolved = false;
		
		if(difficultyLevel == VERY_EASY)
		{
			NakedSingle.getInstance().solve(gameState);
			HiddenSingle.getInstance().solve(gameState);
		}else if(difficultyLevel == EASY)
		{
			NakedSingle.getInstance().solve(gameState);
			HiddenSingle.getInstance().solve(gameState);
		}else if(difficultyLevel == MEDIUM)
		{
			NakedSingle.getInstance().solve(gameState);
			HiddenSingle.getInstance().solve(gameState);
		}else if(difficultyLevel == HARD)
		{
			NakedSingle.getInstance().solve(gameState);
			HiddenSingle.getInstance().solve(gameState);
		}else if(difficultyLevel == VERY_HARD)
		{
			NakedSingle.getInstance().solve(gameState);
			HiddenSingle.getInstance().solve(gameState);
		}
		return canBeSolved;
	}
}
