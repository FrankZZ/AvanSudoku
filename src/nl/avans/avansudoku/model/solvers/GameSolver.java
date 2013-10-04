package nl.avans.avansudoku.model.solvers;

import nl.avans.avansudoku.model.GameState;

public interface GameSolver {
	
	public abstract void setDifficultyLevel(int difficultyLevel);
	public abstract boolean solve(GameState gameState);
}
