package nl.avans.avansudoku.model.solvers;

import nl.avans.avansudoku.model.GameState;


public class NakedSingle implements SolverTechnique
{
	private static NakedSingle ns = new NakedSingle();
	
	private NakedSingle()
	{
		
	}
	
	public static NakedSingle getInstance()
	{
		return ns;
	}

	@Override
	public boolean solve(GameState gamestate)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
