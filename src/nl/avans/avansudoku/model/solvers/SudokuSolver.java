package nl.avans.avansudoku.model.solvers;

import nl.avans.avansudoku.model.GameState;

public class SudokuSolver implements GameSolver
{
	private static final SudokuSolver SudokuSolver = new SudokuSolver();
	public static final int	VERY_EASY	= 1;
	public static final int	EASY		= 2;
	public static final int	MEDIUM		= 3;
	public static final int	HARD		= 4;
	public static final int	VERY_HARD	= 5;

	private int				difficultyLevel;

	public SudokuSolver()
	{
		difficultyLevel = 0;
	}

	public static SudokuSolver getInstance()
	{
		return SudokuSolver;
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

		if (difficultyLevel == VERY_EASY)
		{
			for (int i = 0; i < 40; i++)
			{
				canBeSolved = NakedSingle.getInstance().solve(gameState);
				if(canBeSolved == true)
					break;
//				canBeSolved = HiddenSingle.getInstance().solve(gameState);
//				if(canBeSolved == true)
//					break;
			}

			return canBeSolved;
		}
		else
			if (difficultyLevel == EASY)
			{
				for (int i = 0; i < 40; i++)
				{
					canBeSolved = NakedSingle.getInstance().solve(gameState);
					if(canBeSolved == true)
						break;
					canBeSolved = HiddenSingle.getInstance().solve(gameState);
					if(canBeSolved == true)
						break;
				}

				return canBeSolved;
			}
			else
				if (difficultyLevel == MEDIUM)
				{
					for (int i = 0; i < 40; i++)
					{
						canBeSolved = NakedSingle.getInstance().solve(gameState);
						if(canBeSolved == true)
							break;
						canBeSolved = HiddenSingle.getInstance().solve(gameState);
						if(canBeSolved == true)
							break;
					}

					return canBeSolved;
				}
				else
					if (difficultyLevel == HARD)
					{
						for (int i = 0; i < 40; i++)
						{
							canBeSolved = NakedSingle.getInstance().solve(gameState);
							if(canBeSolved == true)
								break;
							canBeSolved = HiddenSingle.getInstance().solve(gameState);
							if(canBeSolved == true)
								break;
						}

						return canBeSolved;
					}
					else
						if (difficultyLevel == VERY_HARD)
						{
							for (int i = 0; i < 40; i++)
							{
								canBeSolved = NakedSingle.getInstance().solve(gameState);
								if(canBeSolved == true)
									break;
								canBeSolved = HiddenSingle.getInstance().solve(gameState);
								if(canBeSolved == true)
									break;
							}

							return canBeSolved;
						}
		return canBeSolved;
	}
}