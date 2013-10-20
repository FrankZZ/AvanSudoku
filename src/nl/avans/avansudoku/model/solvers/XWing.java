package nl.avans.avansudoku.model.solvers;

import nl.avans.avansudoku.control.SudokuGameRules;
import nl.avans.avansudoku.model.GameState;
import nl.avans.avansudoku.model.Tile;

/**
 * @author Rick van Son
 * @version 1.0
 * @since 2013-10-10
 * 
 * 
 *        This class tries to solve the sudoku puzzle using the XWing Algorithm.
 */
public class XWing implements SolverTechnique
{

	private static XWing	xwing;

	private Tile			tileA, tileB, tileC, tileD;

	private Tile[]			rowAB, rowCD;

	private Tile[]			columnAC, columnBD;

	private boolean			rowMode, columnMode;

	private int				selectedCandidate;

	private int				tileIndex;

	private int				tileColumnSelector;

	/**
	 * Constructor.
	 */
	public XWing()
	{
		this.resetAlmostEverything();
		this.tileIndex = 0;
		this.tileColumnSelector = 0;
	}

	public static XWing getInstance()
	{
		if (xwing == null)
		{
			xwing = new XWing();
		}
		return xwing;
	}

	@Override
	public boolean solve(GameState gamestate)
	{
		try
		{
			// Search on candidate value:
			for (int candidateIndex = 1; candidateIndex <= 9; candidateIndex++)
			{
				// First reset the selected candidate:
				this.selectedCandidate = 0;

				// Let's begin to search with the first row:
				for (int rowIndex = 0; rowIndex < SudokuGameRules.DEFAULT_AMOUNT_OF_ROWS; rowIndex++)
				{
					Tile[] tempRow = gamestate.getRow(rowIndex);

					// When a candidate has been selected, set the
					// candidateIndex with the selected candidate.
					if (0 < selectedCandidate && selectedCandidate <= 9
							&& tileIndex != 10)
					{
						candidateIndex = selectedCandidate;
					}

					this.tileColumnSelector = 0;
					for (tileIndex = 0; tileIndex < SudokuGameRules.DEFAULT_ROW_SIZE; tileIndex++)
					{
						if (tempRow[tileIndex].isCandidate(candidateIndex))
						{
							this.tileColumnSelector++;

							if (selectedCandidate == 0)
							{
								// It seems we didn't select a candidate
								// yet..
								this.rowAB = tempRow;

								if (tileColumnSelector == 1)
								{
									this.tileA = tempRow[tileIndex];
								}
								else if (tileColumnSelector == 2)
								{
									this.tileB = tempRow[tileIndex];
								}
							}
							else
							{
								this.rowCD = tempRow;

								if (tileColumnSelector == 1)
								{
									this.tileC = tempRow[tileIndex];
								}
								else if (tileColumnSelector == 2)
								{
									this.tileD = tempRow[tileIndex];
								}
							}
						}

						if (tileColumnSelector == 2)
						{
							if (selectedCandidate == 0)
							{
								// Now we've selected a candidate
								this.selectedCandidate = candidateIndex;
							}
							else if (selectedCandidate > 0
									&& tileA.getY() == tileC.getY()
									&& tileB.getY() == tileD.getY())
							{

								boolean removeAllowed = false;

								this.checkOnColumnModus(rowAB, tileA, tileB);
								this.checkOnColumnModus(rowCD, tileC, tileD);

								this.columnAC = gamestate.getColumn(tileA
										.getY());
								this.columnBD = gamestate.getColumn(tileB
										.getY());

								if (this.checkOnRowModus(columnAC, tileA, tileC))
								{
									if (this.checkOnRowModus(columnBD, tileB,
											tileD))
									{
										removeAllowed = true;
									}
									else
									{
										// Break everything
										tileIndex = SudokuGameRules.DEFAULT_ROW_SIZE + 1;
										rowIndex = SudokuGameRules.DEFAULT_AMOUNT_OF_ROWS + 1;

										removeAllowed = false;
									}
								}

								if (removeAllowed)
								{
									if (rowMode && !columnMode)
									{
										this.removeSuggestionsFromRow(
												gamestate, tileA, tileB);
										this.removeSuggestionsFromRow(
												gamestate, tileC, tileD);
									}
									else if (!rowMode && columnMode)
									{
										this.removeSuggestionsFromColumn(
												gamestate, tileA, tileC);
										this.removeSuggestionsFromColumn(
												gamestate, tileB, tileD);
									}
									return true;
								}

								this.resetAlmostEverything();

							}
							else if (selectedCandidate > 0)
							{
								this.resetAlmostEverything();
								break;
							}
						}
					}
				}
			}
		}
		catch (Throwable tr)
		{
			tr.printStackTrace();
		}

		return false;
	}

	private boolean checkOnRowModus(Tile[] column, Tile tileTop, Tile tileBottom)
	{
		for (int i = 0; i < SudokuGameRules.DEFAULT_COLUMN_SIZE; i++)
		{
			Tile temp = column[i];
			if (temp.getX() != tileTop.getX()
					&& temp.getX() != tileBottom.getX()
					&& temp.isCandidate(selectedCandidate))
			{
				if (columnMode)
				{
					return false;
				}
				else
				{
					this.columnMode = false;
					this.rowMode = true;
					return true;
				}
			}
		}

		return false;
	}

	private void checkOnColumnModus(Tile[] row, Tile tileLeft, Tile tileRight)
	{
		for (int i = 0; i < SudokuGameRules.DEFAULT_ROW_SIZE; i++)
		{
			Tile temp = row[i];
			if (temp.getY() != tileLeft.getY()
					&& temp.getY() != tileRight.getY()
					&& temp.isCandidate(selectedCandidate))
			{
				this.columnMode = true;
				this.rowMode = false;
			}
		}

	}

	private void removeSuggestionsFromColumn(GameState gamestate, Tile tileTop,
			Tile tileBottom)
	{
		Tile[] tempColumn = gamestate.getColumn(tileTop.getY());

		for (int i = 0; i < SudokuGameRules.DEFAULT_COLUMN_SIZE; i++)
		{
			if (i != tileTop.getX() && i != tileBottom.getX())
			{
				// Get the column and the tile.
				Tile tempTile = tempColumn[i];
				tempTile.setCandidate(this.selectedCandidate, false);
				tempColumn[i] = tempTile;
			}
		}

		gamestate.setColumn(tileTop.getY(), tempColumn);
	}

	private void removeSuggestionsFromRow(GameState gamestate, Tile tileLeft,
			Tile tileRight)
	{
		Tile[] tempRow = gamestate.getRow(tileLeft.getX());

		for (int i = 0; i < SudokuGameRules.DEFAULT_ROW_SIZE; i++)
		{
			if (i != tileLeft.getY() && i != tileRight.getY())
			{
				// Get the column and the tile.
				Tile tempTile = tempRow[i];
				tempTile.setCandidate(this.selectedCandidate, false);
				tempRow[i] = tempTile;
			}
		}

		gamestate.setRow(tileLeft.getX(), tempRow);

	}

	private void resetAlmostEverything()
	{
		this.selectedCandidate = 0;
		this.tileA = this.tileB = this.tileC = this.tileD = null;
		this.rowAB = this.rowCD = null;
		this.columnAC = this.columnBD = null;
		this.rowMode = this.columnMode = false;
	}
}
