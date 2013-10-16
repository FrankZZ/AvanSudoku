package nl.avans.avansudoku.model.solvers;

import nl.avans.avansudoku.control.SudokuGameRules;
import nl.avans.avansudoku.model.GameState;
import nl.avans.avansudoku.model.Tile;
import nl.avans.avansudoku.model.solvers.SolverTechnique;

/**
 * @author Rick van Son
 * @version 1.0
 * @since 2013-10-10
 * 
 * 
 *        This class tries to solve the sudoku puzzle using the Segmentation
 *        Algorithm.
 */
public class Segmentation implements SolverTechnique
{

	private static Segmentation	seg;

	/**
	 * 
	 */
	public Segmentation()
	{
		// TODO Auto-generated constructor stub
	}

	public static Segmentation getInstance()
	{
		if (seg == null)
		{
			seg = new Segmentation();
		}
		return seg;
	}

	@Override
	public boolean solve(GameState gamestate)
	{
		try
		{
			for (int i = SudokuGameRules.DEFAULT_MAX_INDEX_VALUE; i < SudokuGameRules.DEFAULT_MAX_INDEX_VALUE; i++)
			{
				Tile selectedTile = gamestate.getTile(i);

				// Step 1: search for a tile with candidates:

				if (selectedTile.getCandidateCount() > 0)
				{
					// Oh yes we have one. Let's Progress the given tile and
					// check if we could remove the candidate of it.

					return this.processTileWithCandidates(gamestate,
							selectedTile);
				}
			}
		}
		catch (Throwable tr)
		{
			tr.printStackTrace();
		}

		return false;
	}

	/**
	 * @return true when a candidate in the given tile could be AND HAS BEEN
	 *         removed. false when it isn't.
	 * 
	 * @param gamestate
	 * @param selectedTile
	 * @throws Throwable
	 */
	private boolean processTileWithCandidates(GameState gamestate,
			Tile selectedTile) throws Throwable
	{
		int xOfSelectedTile = selectedTile.getX();
		int yOfSelectedTile = selectedTile.getY();

		boolean[] candidatesOfSelectedTile = selectedTile.getCandidates();

		// Step 2: Pick a candidate:
		for (int j = 0; j < candidatesOfSelectedTile.length; j++)
		{
			if (candidatesOfSelectedTile[j])
			{
				// Oh yes this candidate is in the tile.
				int selectedCandidate = j;

				// Pick the other tiles of the row of the selected tile:
				Tile[] rowOfSelectedTile = gamestate.getRow(xOfSelectedTile);

				boolean[] whichTilesInRowHaveSameCandidate = this
						.checkTilesOnHavingSameCandidate(gamestate,
								rowOfSelectedTile, true, selectedCandidate,
								xOfSelectedTile, yOfSelectedTile);

				// Pick the other tiles of the column of the selected tile:
				Tile[] columnOfSelectedTile = gamestate
						.getColumn(yOfSelectedTile);

				boolean[] whichTilesInColumnHaveSameCandidate = this
						.checkTilesOnHavingSameCandidate(gamestate,
								columnOfSelectedTile, false, selectedCandidate,
								xOfSelectedTile, yOfSelectedTile);

				// Now, check the results of the previous two checks if we could
				// remove the selected candidate of the selected tile.
				boolean couldCandidateOfFirstSelectedTileBeRemoved = false;
				for (int k = 0; k < SudokuGameRules.DEFAULT_ROW_SIZE; k++)
				{
					// / Maybe some more checks later....not sure if
					// everything goes that well...

					if (whichTilesInRowHaveSameCandidate[k])
					{
						couldCandidateOfFirstSelectedTileBeRemoved = true;
					}
					else if (whichTilesInColumnHaveSameCandidate[k])
					{
						couldCandidateOfFirstSelectedTileBeRemoved = true;
					}
				}

				if (couldCandidateOfFirstSelectedTileBeRemoved)
				{
					// it seems like it's only on the same row,
					// so we could remove the candidate at the selected tile.
					selectedTile.setCandidate(selectedCandidate, false);

					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @return A fixed-array of booleans where the value 'true' at a specific
	 *         index specifies that the tile at that index has the same
	 *         candidate. 'false' means that the tile at that index doesn't have
	 *         the candidate.
	 * 
	 * @param gamestate
	 * @param tiles
	 * @param checkTilesAsRow
	 * @param candidate
	 * @param xOfSelectedTile
	 * @param yOfSelectedTile
	 * @throws Throwable
	 */
	private boolean[] checkTilesOnHavingSameCandidate(GameState gamestate,
			Tile[] tiles, boolean checkTilesAsRow, int candidate,
			int xOfSelectedTile, int yOfSelectedTile) throws Throwable
	{
		boolean[] whichTilesHaveSameCandidate = new boolean[9];

		for (int k = 0; k < SudokuGameRules.DEFAULT_ROW_SIZE; k++)
		{
			Tile secondSelectedTile = tiles[k];

			// Check if that tile has the same candidate:
			if (secondSelectedTile.getCandidate(candidate))
			{
				// it is.
				int yOfCandidateMatchedTile = secondSelectedTile.getY();
				int xOfCandidateMatchedTile = secondSelectedTile.getX();

				// Check if the candidate-matched Tile isn't accidently the
				// first one.
				// NOTE: if that checkInnerAsRow boolean wasn't there,
				// the conditional expression in the else-if would be true,
				// which would fuck up everything.
				if (checkTilesAsRow
						&& yOfCandidateMatchedTile != yOfSelectedTile)
				{
					whichTilesHaveSameCandidate[k] = this
							.checkIfThisCandidateIsntInOtherTilesOfTheBlock(
									gamestate, candidate, xOfSelectedTile,
									yOfCandidateMatchedTile, true, false);
				}
				else if (!checkTilesAsRow
						&& xOfCandidateMatchedTile != xOfSelectedTile)
				{
					whichTilesHaveSameCandidate[k] = this
							.checkIfThisCandidateIsntInOtherTilesOfTheBlock(
									gamestate, candidate,
									xOfCandidateMatchedTile, yOfSelectedTile,
									false, true);
				}
			}
		}
		return whichTilesHaveSameCandidate;
	}

	/**
	 * @return true when the given candidate is only in the row or column of the
	 *         given x and y values. false when it isn't.
	 * 
	 * @param gamestate
	 * @param candidate
	 * @param xIsInThisBlock
	 * @param yIsInThisBlock
	 * @param ignoreGivenXAxis
	 * @param ignoreGivenYAxis
	 * @throws Throwable
	 */
	private boolean checkIfThisCandidateIsntInOtherTilesOfTheBlock(
			GameState gamestate, int candidate, int xIsInThisBlock,
			int yIsInThisBlock, boolean ignoreGivenXAxis,
			boolean ignoreGivenYAxis) throws Throwable
	{
		Tile[] blockToBeChecked = gamestate.getBlock(xIsInThisBlock,
				yIsInThisBlock);

		for (int i = 0; i < SudokuGameRules.DEFAULT_BLOCK_SIZE; i++)
		{
			Tile selectedTile = blockToBeChecked[i];

			// Check if there is a Tile in the Block with the given candidate,
			// of course not the Tile with the given X and Y!
			if (selectedTile.getCandidate(candidate)
					&& selectedTile.getX() != xIsInThisBlock
					&& selectedTile.getY() != yIsInThisBlock)
			{
				// Are we checking the row, or column?
				if (ignoreGivenXAxis && selectedTile.getX() != xIsInThisBlock)
				{
					return true;
				}
				else if (ignoreGivenYAxis
						&& selectedTile.getY() != yIsInThisBlock)
				{
					return true;
				}
			}
		}
		return false;
	}
}
