package nl.avans.avansudoku.control;

import nl.avans.avansudoku.model.GameState;

public interface GameSolver {
	
	public abstract void setDifficultyLevel(int difficultyLevel);
	public abstract boolean solve(GameState gameState);
}
