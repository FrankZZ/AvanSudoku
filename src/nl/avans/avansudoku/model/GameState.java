package nl.avans.avansudoku.model;

import java.util.EmptyStackException;


public interface GameState
{

	public abstract SudokuGameState addStartState(Tile[] game);

	public abstract SudokuGameState updateCurrentState(Tile tile)
			throws EmptyStackException;

	public abstract SudokuGameState addUndoAction(Tile tile);

	public abstract SudokuGameState resetToStartState();

	public abstract Tile retrieveUndoAction() throws Throwable;

	public abstract Tile getTile(int index);
	
	public abstract void setTileValue(int index, int value);
	
	public abstract void setTileValue(int x, int y, int value);

	public abstract Tile getTile(int x, int y);
	
	// Because it isn't only the value of the Tile which we are manipulating with the solvers.
	public abstract void setTile( int x, int y, Tile tile );

	public abstract Tile[] getRow(int atX);

	public abstract void setRow(int atX, Tile[] modifiedTiles);

	public abstract Tile[] getColumn(int atY);

	public abstract void setColumn(int atY, Tile[] modifiedTiles);

	public abstract Tile[] getBlock(int atX, int atY);

	public abstract void setBlock(int atX, int atY, Tile[] modifiedTiles);

	public abstract Tile askHintAction(); // Debate if this needs solver access

	public abstract Boolean checkNewState();
}