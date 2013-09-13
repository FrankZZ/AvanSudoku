package model;

public interface GameState
{

	public abstract void addStartState();

	public abstract void updateCurrentState();

	public abstract void addUndoAction();

	public abstract void retrieveUndoAction();
	
	public abstract boolean[] getPossibilities(int x, int y);
}
