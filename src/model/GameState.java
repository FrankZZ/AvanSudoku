package model;

public interface GameState
{

	public abstract void addStartState();
	
	public abstract Tile[] getStartState();
	
	public abstract void setStartState(double[] tileLijst);

	public abstract Boolean updateCurrentState(double[] tileLijst);

	public abstract Boolean checkNewState(double[] tileLijst, Tile tile);

	public abstract void addUndoAction(Tile prev);
	
	public abstract Tile askHintAction();

	public abstract Tile retrieveUndoAction();
	
	public abstract boolean[] getPossibilities(int x, int y);
}
