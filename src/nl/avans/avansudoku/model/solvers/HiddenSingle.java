package nl.avans.avansudoku.model.solvers;

import nl.avans.avansudoku.model.GameState;

public class HiddenSingle implements SolverTechnique {

	private static HiddenSingle hs = new HiddenSingle();
	
	public HiddenSingle() {
		// TODO Auto-generated constructor stub
	}
	
	public static HiddenSingle getInstance()
	{
		return hs;
	}

	@Override
	public boolean solve(GameState gamestate) {
		// TODO Auto-generated method stub
		return false;
	}

}
