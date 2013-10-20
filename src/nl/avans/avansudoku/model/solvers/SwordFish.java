/**
 * 
 */
package nl.avans.avansudoku.model.solvers;

import java.util.Vector;

import nl.avans.avansudoku.control.SudokuGameRules;
import nl.avans.avansudoku.model.GameState;
import nl.avans.avansudoku.model.Tile;

/**
 * @author Rick van Son
 * @version 1.0
 * @since 2013-10-16
 * 
 * 
 *        This class tries to solve the sudoku puzzle using the XWing Algorithm.
 */
public class SwordFish implements SolverTechnique
{

	private static SwordFish	swordfish;

	/**
	 * Constructor
	 */
	public SwordFish()
	{
	}

	public static SwordFish getInstance()
	{
		if (swordfish == null)
		{
			swordfish = new SwordFish();
		}

		return swordfish;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nl.avans.avansudoku.model.solvers.SolverTechnique#solve(nl.avans.avansudoku
	 * .model.GameState)
	 */
	@Override
	public boolean solve(GameState gamestate)
	{
		try
		{
			// Search on candidate value:
			for (int candidateIndex = 1; candidateIndex <= 9; candidateIndex++)
			{
				// Let's begin to search in the columns on tiles with more than
				// two candidates
				// the maximum is 3 candidates, otherwise it will be a
				// jellyfish.
				for (int numberOfCandidates = 2; numberOfCandidates <= 3; numberOfCandidates++)
				{
					// Check number of columns that have the numberOfCandidates
					// of the candidateIndex
					Vector<Vector<Tile>> columnCandidates = new Vector<Vector<Tile>>();

					for (int y = 0; y < SudokuGameRules.DEFAULT_AMOUNT_OF_ROWS; y++)
					{
						Vector<Tile> candidatedTiles = new Vector<Tile>();

						for (int x = 0; x < SudokuGameRules.DEFAULT_AMOUNT_OF_COLUMNS; x++)
						{
							// if the candidate is there, save it in a
							// collection.
							Tile selectedTile = gamestate.getTile(x, y);
							if (selectedTile.isCandidate(candidateIndex))
							{
								// We're saving the Vector with Tiles which are
								// likely a candidate into a vector.
								candidatedTiles.addElement(selectedTile);
							}
						}

						// it isn't a problem when there are more candidates are
						// found than in nubmerOfCandidates.
						if (candidatedTiles.size() != numberOfCandidates)
						{
							continue;
						}

						columnCandidates.addElement(candidatedTiles);
					}

					// In case when it's a 'perfect Swordfish' with 3 rows and 3
					// columns
					// or just a vertical Swordfish with 3 rows and 2 columns.
					if (columnCandidates.size() == 3)
					{
						// Check that candidates are in 3 common rows
						Vector<Integer> swordfishColumns = new Vector<Integer>();

						for (int i = 0; i < columnCandidates.size(); i++)
						{
							Vector<Tile> selectedCandiColumn = (Vector<Tile>) columnCandidates
									.elementAt(i);

							for (int j = 0; j < selectedCandiColumn.size(); j++)
							{
								Integer nextColumn = Integer
										.valueOf((((Tile) selectedCandiColumn
												.elementAt(j)).getY()));

								if (!swordfishColumns.contains(nextColumn))
								{
									swordfishColumns.addElement(nextColumn);
								}
							}
						}

						if (swordfishColumns.size() == 3)
						{
							boolean thingsWentWell = false;

							for (int i = 0; i < swordfishColumns.size(); i++)
							{
								int swordfishColumnIndex = ((Integer) swordfishColumns
										.elementAt(i)).intValue();

								// Get the 3 protected columns for the
								// candidates in row <row>
								Vector<Integer> protectedRows = new Vector<Integer>();

								for (int j = 0; j < columnCandidates.size(); j++)
								{
									Vector<Tile> selectedCandiColumn = (Vector<Tile>) columnCandidates
											.elementAt(j);

									for (int k = 0; k < selectedCandiColumn
											.size(); k++)
									{
										Tile nextCandiTile = (Tile) selectedCandiColumn
												.elementAt(k);

										if (nextCandiTile.getY() == swordfishColumnIndex)
										{
											protectedRows.addElement(Integer
													.valueOf(nextCandiTile
															.getX()));
										}
									}
								}

								// Scan all the tiles of the sudoku again, and
								// remove the candidate from the non-protected
								// tiles.
								for (int x = 0; x < 9; x++)
								{
									if (!protectedRows.contains(Integer
											.valueOf(x)))
									{
										Tile selectedTile = gamestate.getTile(
												x, swordfishColumnIndex);
										if (selectedTile
												.isCandidate(candidateIndex))
										{
											// remove the candidate and backport
											// it back to the gamestate.
											thingsWentWell = true;
											selectedTile.setCandidate(
													candidateIndex, false);
											gamestate.setTile(x,
													swordfishColumnIndex,
													selectedTile);
										}
									}
								}
							}

							if (thingsWentWell)
							{
								return (true);
							}
						}
					}

					// Check number of rows that have the numberOfCandidates of
					// the candidateIndex
					Vector<Vector<Tile>> rowCandidates = new Vector<Vector<Tile>>();

					for (int x = 0; x < SudokuGameRules.DEFAULT_AMOUNT_OF_COLUMNS; x++)
					{
						Vector<Tile> candidatedTiles = new Vector<Tile>();

						for (int y = 0; y < SudokuGameRules.DEFAULT_AMOUNT_OF_ROWS; y++)
						{
							Tile selectedTile = gamestate.getTile(x, y);
							if (selectedTile.isCandidate(candidateIndex))
							{
								candidatedTiles.addElement(selectedTile);
							}
						}

						if (candidatedTiles.size() != numberOfCandidates)
						{
							continue;
						}

						rowCandidates.addElement(candidatedTiles);
					}

					if (rowCandidates.size() == 3)
					{
						// Check that candidates are on 3 common columns
						Vector<Integer> swordfishRow = new Vector<Integer>();

						for (int i = 0; i < rowCandidates.size(); i++)
						{
							Vector<Tile> selectedCandiRow = (Vector<Tile>) rowCandidates
									.elementAt(i);

							for (int j = 0; j < selectedCandiRow.size(); j++)
							{
								Integer nextRow = Integer
										.valueOf(((Tile) selectedCandiRow
												.elementAt(j)).getX());

								if (!swordfishRow.contains(nextRow))
								{
									swordfishRow.addElement(nextRow);
								}
							}
						}

						if (swordfishRow.size() == 3)
						{
							boolean thingsWentWell = false;

							for (int i = 0; i < swordfishRow.size(); i++)
							{
								int swordfishRowIndex = ((Integer) swordfishRow
										.elementAt(i)).intValue();

								// Get the 3 protected rows for the
								// candidates in column <column>
								Vector<Integer> protectedColumns = new Vector<Integer>();

								for (int j = 0; j < rowCandidates.size(); j++)
								{
									Vector<Tile> selectedCandiRow = (Vector<Tile>) rowCandidates
											.elementAt(j);

									for (int k = 0; k < selectedCandiRow.size(); k++)
									{
										Tile nextCandiTile = (Tile) selectedCandiRow
												.elementAt(k);

										if (nextCandiTile.getX() == swordfishRowIndex)
										{
											protectedColumns.addElement(Integer
													.valueOf(nextCandiTile
															.getY()));
										}
									}
								}

								// Scan all the tiles of the sudoku again, and
								// remove the candidate from the non-protected
								// tiles.
								for (int y = 0; y < 9; y++)
								{
									if (!protectedColumns.contains(Integer
											.valueOf(y)))
									{
										Tile selectedTile = gamestate.getTile(
												swordfishRowIndex, y);
										if (selectedTile
												.isCandidate(candidateIndex))
										{
											// remove the candidate and backport
											// it back to the gamestate.
											thingsWentWell = true;
											selectedTile.setCandidate(
													candidateIndex, false);
											gamestate.setTile(
													swordfishRowIndex, y,
													selectedTile);
										}
									}
								}
							}

							if (thingsWentWell)
							{
								return (true);
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
}
