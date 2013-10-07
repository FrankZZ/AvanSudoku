package nl.avans.avansudoku.model;

import java.util.ArrayList;
import java.util.EmptyStackException;

public interface GameState
{

	public abstract SudokuGameState addStartState(Tile[] game);

	public abstract SudokuGameState updateCurrentState(Tile tile)
			throws EmptyStackException;

	public abstract SudokuGameState addUndoAction(Tile tile);

	public abstract SudokuGameState resetToStartState();

	public abstract Tile retrieveUndoAction() throws Throwable;

	public abstract ArrayList<Integer> getPossibilities(int x, int y);

	public abstract Tile getTile( int index );
	
	public abstract Tile getTile( int x, int y );
	
	public abstract Tile[] getRow(int atX);

	public abstract void setRow(int atX, Tile[] modifiedTiles);

	public abstract Tile[] getColumn(int atY);

	public abstract void setColumn(int atY, Tile[] modifiedTiles);

	public abstract Tile[] getBlock(int atX, int atY);

	public abstract void setBlock(int atX, int atY, Tile[] modifiedTiles);

	Tile askHintAction();
		
	public abstract Boolean checkNewState();
}