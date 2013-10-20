/**
 * 
 */
package nl.avans.avansudoku.control;

import nl.avans.avansudoku.model.SudokuGameState;
import nl.avans.avansudoku.model.Tile;

/**
 * @author Rick van Son
 * @version 1.1
 * @since 2013-10-07
 * 
 * 
 *        This class checks if the user input is valid.
 * 
 *        You have to know the sudoku, and the last input.
 */
public class SudokuGameRules extends GameRules
{

	public static final int DEFAULT_ROW_SIZE = 9;
	public static final int DEFAULT_COLUMN_SIZE = 9;
	public static final int DEFAULT_BLOCK_SIZE = 9;
	public static final int DEFAULT_MIN_VALUE = 0;
	public static final int DEFAULT_MAX_VALUE = 9;
	public static final int DEFAULT_MIN_INDEX_VALUE = 0;
	public static final int DEFAULT_MAX_INDEX_VALUE = 80;
	public static final int DEFAULT_AMOUNT_OF_TILES = 81;

	public static final int DEFAULT_AMOUNT_OF_ROWS = 9;
	public static final int DEFAULT_AMOUNT_OF_COLUMNS = 9;
	public static final int DEFAULT_AMOUNT_OF_BLOCKS = 9;
	
	

	public SudokuGameRules()
	{
	}

	/**
	 * First the input gets into the undostack, then this function comes into
	 * action to check if the input is valid.
	 * 
	 * @return true if input is valid, false if not.
	 * @throws Throwable
	 */
	public boolean checkInput() throws Throwable
	{
		boolean itsOk = false;

		SudokuGameState currentGameState = ( SudokuGameState ) GameManager
				.getInstance().getGameState();

		Tile previousUndoAction = currentGameState.retrieveUndoAction();

		int tileX = previousUndoAction.getX();
		int tileY = previousUndoAction.getY();
		int tileIndex = previousUndoAction.getIndex();
		int tileValue = previousUndoAction.getValue();

		if( 0 > tileX || tileX >= DEFAULT_ROW_SIZE )
		{
			throw new Exception( "Oops, wrong x value: " + tileX + "!" );
		}
		else if( 0 > tileY || tileY >= DEFAULT_COLUMN_SIZE )
		{
			throw new Exception( "Oops, wrong y value: " + tileY + "!" );
		}
		else if( 0 > tileIndex || tileIndex > DEFAULT_MAX_INDEX_VALUE )
		{
			throw new Exception( "Oops, wrong cell index: " + tileIndex + "!" );
		}
		else if( DEFAULT_MIN_VALUE > tileValue || tileValue > DEFAULT_MAX_VALUE )
		{
			throw new Exception( "Oops, wrong cell value: " + tileValue + "!" );
		}
		else
		{
			if( !this.isValueAlreadyInThisRow( tileX, tileValue, tileY ) )
			{
				if( !this.isValueAlreadyInThisColumn( tileY, tileValue, tileX ) )
				{
					if( !this.isValueAlreadyInThisBlock( tileY, tileX,
							tileValue ) )
					{
						itsOk = true;
					}
				}
			}
			if( itsOk )
			{
				this.removeSuggestionsThen( tileX, tileY, tileValue );
			}
		}

		return itsOk;
	}

	/**
	 * 
	 * @param atRowIndex
	 * @param onValue
	 * @param butNotColumnIndex
	 * @return true if value is already in the given row
	 */
	protected boolean isValueAlreadyInThisRow( int atRowIndex, int onValue,
			int butNotColumnIndex )
	{
		SudokuGameState currentGameState = ( SudokuGameState ) GameManager
				.getInstance().getGameState();

		Tile[] rowToCheck = currentGameState.getRow( atRowIndex );

		boolean valueNotInOtherTiles = false;

		for( int i = 0; i < DEFAULT_ROW_SIZE; i++ )
		{
			if( rowToCheck[i].getY() != butNotColumnIndex
					&& rowToCheck[i].getValue() == onValue )
			{
				valueNotInOtherTiles = true;
			}
		}
		return valueNotInOtherTiles;
	}

	/**
	 * 
	 * @param atColumnIndex
	 * @param onValue
	 * @param butNotRowIndex
	 * @return true if value is already in the given column
	 */
	protected boolean isValueAlreadyInThisColumn( int atColumnIndex,
			int onValue, int butNotRowIndex )
	{
		SudokuGameState currentGameState = ( SudokuGameState ) GameManager
				.getInstance().getGameState();

		Tile[] columnToCheck = currentGameState.getColumn( atColumnIndex );

		boolean valueNotInOtherTiles = false;

		for( int i = 0; i < DEFAULT_COLUMN_SIZE; i++ )
		{
			if( columnToCheck[i].getX() != butNotRowIndex
					&& columnToCheck[i].getValue() == onValue )
			{
				valueNotInOtherTiles = true;
			}
		}
		return valueNotInOtherTiles;
	}

	/**
	 * 
	 * @param atRowIndex
	 * @param atColumnIndex
	 * @param onValue
	 * @return true if value is already in the given block
	 */
	protected boolean isValueAlreadyInThisBlock( int atRowIndex,
			int atColumnIndex, int onValue )
	{
		SudokuGameState currentGameState = ( SudokuGameState ) GameManager
				.getInstance().getGameState();

		Tile[] blockToCheck = currentGameState.getBlock( atRowIndex,
				atColumnIndex );

		boolean valueNotInOtherTiles = false;

		for( int i = 0; i < DEFAULT_ROW_SIZE; i++ )
		{
			if( blockToCheck[i].getX() != atRowIndex
					&& blockToCheck[i].getY() != atColumnIndex
					&& blockToCheck[i].getValue() == onValue )
			{
				valueNotInOtherTiles = true;
			}
		}
		return valueNotInOtherTiles;
	}

	/**
	 * Remove the valid value as candidate of the other tiles, because the value
	 * is legit filled it.
	 * 
	 * @param atRowIndex
	 * @param atColumnIndex
	 * @param ofThisValue
	 * @throws Throwable
	 */
	protected void removeSuggestionsThen( int atRowIndex, int atColumnIndex,
			int ofThisValue ) throws Throwable
	{
		SudokuGameState currentGameState = ( SudokuGameState ) GameManager
				.getInstance().getGameState();

		// Of the row:
		Tile[] rowToDo = currentGameState.getRow( atRowIndex );

		for( int i = 0; i < DEFAULT_ROW_SIZE; i++ )
		{
			rowToDo[i].setCompCandidate( ofThisValue, false );
		}

		currentGameState.setRow( atRowIndex, rowToDo );

		// Of the column:
		Tile[] columnToDo = currentGameState.getColumn( atColumnIndex );

		for( int i = 0; i < DEFAULT_COLUMN_SIZE; i++ )
		{
			columnToDo[i].setCompCandidate( ofThisValue, false );
		}

		currentGameState.setColumn( atColumnIndex, columnToDo );

		// Of the block:
		Tile[] blockToDo = currentGameState
				.getBlock( atRowIndex, atColumnIndex );

		for( int i = 0; i < DEFAULT_BLOCK_SIZE; i++ )
		{
			blockToDo[i].setCompCandidate( ofThisValue, false );
		}

		currentGameState.setBlock( atRowIndex, atColumnIndex, blockToDo );
	}
}
