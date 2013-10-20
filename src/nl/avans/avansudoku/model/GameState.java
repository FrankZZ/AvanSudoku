package nl.avans.avansudoku.model;

public interface GameState
{

	public abstract void addStartState(Tile[] game);

	public abstract void updateCurrentState(Tile tile);

	public abstract void addUndoAction(Tile tile);

	public abstract void resetToStartState();

	public abstract Tile retrieveUndoAction();
	
	public abstract void undoLastAction();

	public abstract Tile getTile(int index);
	
	public abstract void setTileValue(int index, int value);
	
	public abstract void setTileValue(int x, int y, int value);

	public abstract Tile getTile(int x, int y);

	public abstract Tile[] getRow(int y);

	public abstract void setRow(int y, Tile[] modifiedTiles);

	public abstract Tile[] getColumn(int x);

	public abstract void setColumn(int y, Tile[] modifiedTiles);

	public abstract Tile[] getBlock(int x, int y);

	public abstract void setBlock(int x, int y, Tile[] modifiedTiles);

	public abstract Tile askHintAction(); //TODO Debate if this needs solver access

	public abstract Boolean checkNewState();
}