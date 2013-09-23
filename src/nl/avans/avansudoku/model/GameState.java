package nl.avans.avansudoku.model;

public interface GameState
{

	public abstract void addStartState();

	public abstract void updateCurrentState();

	public abstract void addUndoAction();

	public abstract Tile retrieveUndoAction() throws Throwable;

	public abstract boolean[] getPossibilities( int x, int y );

	public abstract Tile[] getRow( int atX );

	public abstract void setRow( int atX, Tile[] modifiedTiles );

	public abstract Tile[] getColumn( int atY );
	
	public abstract void setColumn( int atY, Tile[] modifiedTiles );

	public abstract Tile[] getBlock( int atX, int atY );
	
	public abstract void setBlock( int atX, int atY, Tile[] modifiedTiles );
}
