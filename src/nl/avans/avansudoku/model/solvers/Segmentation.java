package nl.avans.avansudoku.model.solvers;

import nl.avans.avansudoku.control.SudokuGameRules;
import nl.avans.avansudoku.model.GameState;
import nl.avans.avansudoku.model.Tile;
import nl.avans.avansudoku.model.solvers.SolverTechnique;

/**
 * @author Rick van Son
 * @version 1.0
 * @since 2013-09-30
 * 
 * 
 *        This class tries to solve the sudoku puzzle using the Segmentation
 *        Algorithm.
 */
public class Segmentation implements SolverTechnique
{
	private static Segmentation seg;

	/**
	 * 
	 */
	public Segmentation()
	{
		// TODO Auto-generated constructor stub
	}

	public static Segmentation getInstance()
	{
		if( seg == null )
		{
			seg = new Segmentation();
		}
		return seg;
	}

	@Override
	public boolean solve( GameState gamestate )
	{
		try
		{
			for( int i = SudokuGameRules.DEFAULT_MAX_INDEX_VALUE; i < SudokuGameRules.DEFAULT_MAX_INDEX_VALUE; i++ )
			{
				Tile firstSelectedTile = gamestate.getTile( i );

				int xOfSelectedTile = firstSelectedTile.getX();
				int yOfSelectedTile = firstSelectedTile.getY();

				// Step 1: search for a tile with candidates:
				if( firstSelectedTile.getAmountOfCandidates() > 0 )
				{
					// Step 2: Pick a candidate:
					boolean[] candidatesOfSelectedTile = firstSelectedTile
							.getCandidates();

					for( int j = 0; j < candidatesOfSelectedTile.length; j++ )
					{
						if( candidatesOfSelectedTile[j] )
						{
							// / Oh yes this candidate is in the tile.
							int selectedCandidate = j;

							// / Pick the other tiles of the row of the selected
							// tile:
							Tile[] rowOfSelectedTile = gamestate
									.getRow( xOfSelectedTile );

							boolean[] whichTilesInRowHaveSameCandidate = new boolean[9];

							for( int k = 0; k < rowOfSelectedTile.length; k++ )
							{
								Tile secondSelectedTile = rowOfSelectedTile[k];

								// Check if that tile has the same candidate:
								if( secondSelectedTile
										.getCandidate( selectedCandidate )
										&& secondSelectedTile.getY() != firstSelectedTile
												.getY() )
								{
									// it is.

									int yOfCandidateMatchedTile = secondSelectedTile
											.getY();

									whichTilesInRowHaveSameCandidate[k] = this
											.checkIfThisCandidateIsntInOtherTilesOfTheBlock(
													gamestate,
													selectedCandidate,
													xOfSelectedTile,
													yOfCandidateMatchedTile,
													true, false );
								}
							}

							// / Pick the other tiles of the column of the
							// selected
							// tile:
							Tile[] columnOfSelectedTile = gamestate
									.getColumn( yOfSelectedTile );

							boolean[] whichTilesInColumnHaveSameCandidate = new boolean[9];

							for( int k = 0; k < columnOfSelectedTile.length; k++ )
							{
								Tile secondSelectedTile = columnOfSelectedTile[k];

								// Check if that tile has the same candidate:
								if( secondSelectedTile
										.getCandidate( selectedCandidate )
										&& secondSelectedTile.getX() != firstSelectedTile
												.getX() )
								{
									// it is.

									int xOfCandidateMatchedTile = secondSelectedTile
											.getX();

									whichTilesInColumnHaveSameCandidate[k] = this
											.checkIfThisCandidateIsntInOtherTilesOfTheBlock(
													gamestate,
													selectedCandidate,
													xOfCandidateMatchedTile,
													yOfSelectedTile, false,
													true );
								}
							}

							boolean couldCandidateOfFirstSelectedTileBeRemoved = false;
							for( int k = 0; k < whichTilesInRowHaveSameCandidate.length; k++ )
							{
								// / Maybe some more checks later....not sure if
								// everything goes that well...

								if( whichTilesInRowHaveSameCandidate[k] )
								{
									couldCandidateOfFirstSelectedTileBeRemoved = true;
								}
								else if( whichTilesInColumnHaveSameCandidate[k] )
								{
									couldCandidateOfFirstSelectedTileBeRemoved = true;
								}
							}

							if( couldCandidateOfFirstSelectedTileBeRemoved )
							{
								// it seems like it's only on the same
								// row, so we could remove the candidate
								// at the first selected tile.
								firstSelectedTile.setCandidate( j, false );
								
								return true;
							}
						}
					}

				}
			}
		}
		catch( Throwable tr )
		{
			tr.printStackTrace();
		}

		return false;
	}

	private boolean checkIfThisCandidateIsntInOtherTilesOfTheBlock(
			GameState gamestate, int candidate, int xIsInThisBlock,
			int yIsInThisBlock, boolean ignoreGivenXAxis,
			boolean ignoreGivenYAxis ) throws Throwable
	{
		Tile[] blockToBeChecked = gamestate.getBlock( xIsInThisBlock,
				yIsInThisBlock );

		for( int i = 0; i < blockToBeChecked.length; i++ )
		{
			Tile selectedTile = blockToBeChecked[i];

			// Check if there is a Tile in the Block with the given candidate,
			// of course not the Tile with the given X and Y!
			if( selectedTile.getCandidate( candidate )
					&& selectedTile.getX() != xIsInThisBlock
					&& selectedTile.getY() != yIsInThisBlock )
			{
				// Are we checking the row, or column?
				if( ignoreGivenXAxis && selectedTile.getX() != xIsInThisBlock )
				{
					return true;
				}
				else if( ignoreGivenYAxis
						&& selectedTile.getY() != yIsInThisBlock )
				{
					return true;
				}
			}
		}
		return false;
	}
}
