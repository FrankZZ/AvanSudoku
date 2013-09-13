package model;

public interface GameState {
	
	abstract void addStartState();
	abstract void updateCurrentState();
	abstract void addUndoAction();
	abstract void retrieveUndoAction();
}
