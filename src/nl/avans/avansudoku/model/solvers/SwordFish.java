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
 * @version 1.1
 * @since 2013-10-20
 * 
 * 
 *        This class tries to solve the sudoku puzzle using the Swordfish Algorithm.
 */
public class SwordFish implements SolverTechnique
{

	private static SwordFish swordfish;

	/**
	 * Constructor
	 */
	public SwordFish()
	{
	}

	public static SwordFish getInstance()
	{
		if( swordfish == null )
		{
			swordfish = new SwordFish();
		}

		return swordfish;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nl.avans.avansudoku.model.solvers.SolverTechnique#solve(nl.avans.avansudoku .model.GameState)
	 */
	@Override
	public boolean solve( GameState gamestate )
	{
		try
		{
			// Search on candidate value:
			for( int candidateIndex = 1; candidateIndex <= 9; candidateIndex++ )
			{
				// Let's begin to search in the columns on two or more tiles which have the same candidate.
				// The valid maximum for a swordfish is 3 tiles per column, otherwise it will be a jellyfish.
				// Of course more than 3 tiles in the column could have the same candidate, but one of those could be removed.
				for( int neededAmountForSwordfishSelection = 2; neededAmountForSwordfishSelection <= 3; neededAmountForSwordfishSelection++ )
				{
					// Let's check the amount of columns that have the needed amount of tiles with the candidate:
					Vector<Vector<Tile>> columnsWithCandidate = new Vector<Vector<Tile>>();

					for( int y = 0; y < SudokuGameRules.DEFAULT_AMOUNT_OF_ROWS; y++ )
					{
						Vector<Tile> columnWithCandidatedTiles = new Vector<Tile>();

						for( int x = 0; x < SudokuGameRules.DEFAULT_AMOUNT_OF_COLUMNS; x++ )
						{
							// When a tile with the selected candidate is found, save it for later:
							Tile selectedTile = gamestate.getTile( x, y );
							if( selectedTile.isCompCandidate( candidateIndex ) )
							{
								columnWithCandidatedTiles.addElement( selectedTile );
							}
						}

						// It isn't a problem (YET!) when there are more tiles with the candidate are found than in neededAmountForSwordfishSelection.
						if( columnWithCandidatedTiles.size() != neededAmountForSwordfishSelection )
						{
							continue;
						}

						columnsWithCandidate.addElement( columnWithCandidatedTiles );
					}

					// In case when it's a 'perfect Swordfish' with 3 rows and 3 columns.
					// or just a horizontal Swordfish with 2 rows and 3 columns.
					if( columnsWithCandidate.size() == 3 )
					{
						// Check that candidates are in 3 columns
						Vector<Integer> swordfishColumns = new Vector<Integer>();

						for( int i = 0; i < columnsWithCandidate.size(); i++ )
						{
							Vector<Tile> selectedCandiColumn = ( Vector<Tile> ) columnsWithCandidate.elementAt( i );

							// Check if we're missing a column with the candidate:
							for( int j = 0; j < selectedCandiColumn.size(); j++ )
							{
								Integer nextColumn = Integer.valueOf( ( ( ( Tile ) selectedCandiColumn.elementAt( j ) ).getY() ) );

								// When the found column isn't in the known swordfish columns yet, add it:
								if( !swordfishColumns.contains( nextColumn ) )
								{
									swordfishColumns.addElement( nextColumn );
								}
							}
						}

						if( swordfishColumns.size() == 3 )
						{
							// Turns out we have a perfect swordfish (3x3) or a 'horizontal swordfishy' (2x3)
							boolean thingsWentWell = false;

							for( int i = 0; i < swordfishColumns.size(); i++ )
							{
								int swordfishColumnIndex = ( ( Integer ) swordfishColumns.elementAt( i ) ).intValue();

								// We have to protect those tiles for removing the selected candidate within the innocent swordfish tiles.
								Vector<Integer> protectedRows = new Vector<Integer>();

								for( int j = 0; j < columnsWithCandidate.size(); j++ )
								{
									Vector<Tile> selectedCandiColumn = ( Vector<Tile> ) columnsWithCandidate.elementAt( j );

									for( int k = 0; k < selectedCandiColumn.size(); k++ )
									{
										Tile nextCandiTile = ( Tile ) selectedCandiColumn.elementAt( k );

										if( nextCandiTile.getY() == swordfishColumnIndex )
										{
											protectedRows.addElement( Integer.valueOf( nextCandiTile.getX() ) );
										}
									}
								}

								// Scan all the tiles of the sudoku again, and remove the candidate from the non-protected tiles.
								for( int x = 0; x < 9; x++ )
								{
									if( !protectedRows.contains( Integer.valueOf( x ) ) )
									{
										Tile selectedTile = gamestate.getTile( x, swordfishColumnIndex );
										if( selectedTile.isCompCandidate( candidateIndex ) )
										{
											// remove the candidate and backport it back to the gamestate.
											thingsWentWell = true;
											selectedTile.setCompCandidate( candidateIndex, false );
											gamestate.setTile( x, swordfishColumnIndex, selectedTile );
										}
									}
								}
							}

							if( thingsWentWell )
							{
								// So we found a swordfish,
								// removed the candidates which aren't part of the swordfish,
								// thus we could report that the algorithm flow was successful:
								return true;
							}
						}
					}

					// Let's check the amount of rows that have the needed amount of tiles with the candidate:
					Vector<Vector<Tile>> rowsWithCandidate = new Vector<Vector<Tile>>();

					for( int x = 0; x < SudokuGameRules.DEFAULT_AMOUNT_OF_COLUMNS; x++ )
					{
						Vector<Tile> rowWithCandidatedTiles = new Vector<Tile>();

						for( int y = 0; y < SudokuGameRules.DEFAULT_AMOUNT_OF_ROWS; y++ )
						{
							Tile selectedTile = gamestate.getTile( x, y );
							if( selectedTile.isCompCandidate( candidateIndex ) )
							{
								rowWithCandidatedTiles.addElement( selectedTile );
							}
						}

						// It isn't a problem (YET!) when there are more tiles with the candidate are found than in numberOfCandidateTiles.
						if( rowWithCandidatedTiles.size() != neededAmountForSwordfishSelection )
						{
							continue;
						}

						rowsWithCandidate.addElement( rowWithCandidatedTiles );
					}

					// In case when it's a 'perfect Swordfish' with 3 rows and 3 columns.
					// or just a vertical Swordfish with 3 rows and 2 columns.
					if( rowsWithCandidate.size() == 3 )
					{
						// Check that candidates are in 3 rows:
						Vector<Integer> swordfishRow = new Vector<Integer>();

						for( int i = 0; i < rowsWithCandidate.size(); i++ )
						{
							Vector<Tile> selectedCandiRow = ( Vector<Tile> ) rowsWithCandidate.elementAt( i );

							// Check if we're missing a row with the candidate:
							for( int j = 0; j < selectedCandiRow.size(); j++ )
							{
								Integer nextRow = Integer.valueOf( ( ( Tile ) selectedCandiRow.elementAt( j ) ).getX() );

								// When the found row isn't in the known swordfish row yet, add it:
								if( !swordfishRow.contains( nextRow ) )
								{
									swordfishRow.addElement( nextRow );
								}
							}
						}

						if( swordfishRow.size() == 3 )
						{

							// Turns out we have a perfect swordfish (3x3) or a 'vertical swordfishie' (3x2)
							boolean thingsWentWell = false;

							for( int i = 0; i < swordfishRow.size(); i++ )
							{
								int swordfishRowIndex = ( ( Integer ) swordfishRow.elementAt( i ) ).intValue();

								// We have to protect those tiles for removing the selected candidate within the innocent swordfish tiles.
								Vector<Integer> protectedColumns = new Vector<Integer>();

								for( int j = 0; j < rowsWithCandidate.size(); j++ )
								{
									Vector<Tile> selectedCandiRow = ( Vector<Tile> ) rowsWithCandidate.elementAt( j );

									for( int k = 0; k < selectedCandiRow.size(); k++ )
									{
										Tile nextCandiTile = ( Tile ) selectedCandiRow.elementAt( k );

										if( nextCandiTile.getX() == swordfishRowIndex )
										{
											protectedColumns.addElement( Integer.valueOf( nextCandiTile.getY() ) );
										}
									}
								}

								// Scan all the tiles of the sudoku again, and remove the candidate from the non-protected tiles.
								for( int y = 0; y < 9; y++ )
								{
									if( !protectedColumns.contains( Integer.valueOf( y ) ) )
									{
										Tile selectedTile = gamestate.getTile( swordfishRowIndex, y );
										if( selectedTile.isCompCandidate( candidateIndex ) )
										{
											// remove the candidate and backport it back to the gamestate.
											thingsWentWell = true;
											selectedTile.setCompCandidate( candidateIndex, false );
											gamestate.setTile( swordfishRowIndex, y, selectedTile );
										}
									}
								}
							}

							if( thingsWentWell )
							{
								// So we found a swordfish,
								// removed the candidates which aren't part of the swordfish,
								// thus we could report that the algorithm flow was successful:
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
}
