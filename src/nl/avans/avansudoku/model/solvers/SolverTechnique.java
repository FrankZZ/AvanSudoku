package nl.avans.avansudoku.model.solvers;

import nl.avans.avansudoku.model.GameState;

public interface SolverTechnique {
	
	public abstract boolean solve(GameState gamestate);
}
