package nl.avans.avansudoku.control;

import nl.avans.avansudoku.model.GameState;

public interface SolverTechnique {
	
	public abstract boolean solve(GameState gamestate);
}
