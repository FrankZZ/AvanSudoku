package nl.avans.avansudoku.model;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

import android.util.Log;

public class SudokuGameState implements GameState
{

	private Tile[]		tiles	= new Tile[9 * 9];

	private Tile[]		startTiles;
	private Stack<Tile>	undoStack;

	public SudokuGameState()
	{
		for (int i = 0; i < tiles.length; i++)
			tiles[i] = new Tile(i, 0, false, 0);
	}

	@Override
	public SudokuGameState addStartState(Tile[] tiles)
	{
		// Start state
		if (this.startTiles == null)
		{
			this.startTiles = tiles;
		}

		return this;
	}

	@Override
	public SudokuGameState updateCurrentState(Tile tile)
			throws EmptyStackException
	{
		// De bovenste op de stack updaten.
		if (undoStack.peek() != null)
		{
			undoStack.pop();
			undoStack.add(tile);
		}

		return this;
	}

	@Override
	public SudokuGameState addUndoAction(Tile tile)
	{
		// Toevoegen bovenop de stack.
		undoStack.add(tile);

		return this;
	}

	@Override
	public SudokuGameState resetToStartState()
	{
		undoStack.clear();

		return this;
	}

	@Override
	public Tile retrieveUndoAction()
	{
		return new Tile(0, 1, false, 1);
	}

	public void setTile(int x, int y, Tile tile)
	{
		tiles[(y * 9) + x] = tile;
	}

	public void setTileValue(int x, int y, int value)
	{
		setTileValue((y * 9) + x, value);
	}

	public void setTileValue(int index, int value)
	{
		// Nieuwe value aan neighbor candidates toevoegen
		addCandidateToNeighborTiles(index, value);
		
		// De waarde setten
		getTile(index).setValue(value);
	}

	public Tile getTile(int x, int y)
	{
		return tiles[(y * 9) + x];
	}

	public Tile getTile(int idx)
	{
		return tiles[idx];
	}

	/*
	 * @author Frank Wammes
	 * 
	 * @desc Alle tiles doorlopen en overal de candidates instellen.
	 */
	public void processCandidates()
	{
		for (int x = 0; x < 9; x++)
		{
			for (int y = 0; y < 9; y++)
			{
				processNeighborTileCandidates(x, y);
			}
		}
	}

	/*
	 * Add a candidate to all neighbor tiles in the block, x and y axis. Removes
	 * the previous value of the origin tile from other candidates too
	 */
	private void addCandidateToNeighborTiles(int x, int y, int value)
	{
		int prevValue = getTile(x, y).getValue();

		int blockX = (x / 3) * 3;
		int blockY = (y / 3) * 3;
		int blockIdx = blockY * 9 + blockX;

		for (int i = 0; i < 9; i++)
		{
			// We itereren over de X en Y as en de block in 1 loop

			// Zichzelf overslaan binnen de X as
			if (y != i)
			{
				Tile TileX = getTile(x, i);

				// Vorige value weer candidate maken
				if (prevValue > 0)
				{
					TileX.setCompCandidate(prevValue, true);
					String posString = "[" + x + "][" + i + "]";
					String changedValueString = "candidate: " + prevValue + " is set to: true";
					Log.w("CandidateChanged in tile: " + posString, changedValueString);
				}

				// Nieuwe value geen candidate maken
				TileX.setCompCandidate(value, false);
				String posString = "[" + x + "][" + i + "]";
				String changedValueString = "candidate: " + value + " is set to: false";
				Log.w("CandidateChanged in tile: " + posString, changedValueString);
			}

			// Zichzelf overslaan binnen de Y as
			if (x != i)
			{
				Tile TileY = getTile(i, y);

				// Vorige value weer candidate maken
				if (prevValue > 0)
				{
					TileY.setCompCandidate(prevValue, true);
					String posString = "[" + i + "][" + y + "]";
					String changedValueString = "candidate: " + prevValue + " is set to: true";
					Log.w("CandidateChanged in tile: " + posString, changedValueString);
				}

				// Nieuwe value geen candidate maken
				TileY.setCompCandidate(value, false);
				String posString = "[" + i + "][" + y + "]";
				String changedValueString = "candidate: " + value + " is set to: false";
				Log.w("CandidateChanged in tile: " + posString, changedValueString);
			}

			// Zichzelf overslaan binnen het block
			if ((blockIdx + i) != ((y * 9) + x))
			{
				Tile BlockTile = getTile(blockIdx + i);
				int pos = blockIdx + i;
				int xPos = pos % 9;
				int yPos = pos / 9;

				// Vorige value weer candidate maken
				if (prevValue > 0)
				{
					BlockTile.setCompCandidate(prevValue, true);
					String posString = "[" + xPos + "][" + yPos + "]";
					String changedValueString = "candidate: " + prevValue + " is set to: true";
					Log.w("CandidateChanged in tile: " + posString, changedValueString);
				}

				// Nieuwe value geen candidate maxken
				BlockTile.setCompCandidate(value, false);
				String posString = "[" + xPos + "][" + yPos + "]";
				String changedValueString = "candidate: " + value + " is set to: false";
				Log.w("CandidateChanged in tile: " + posString, changedValueString);
			}
		}
	}

	private void addCandidateToNeighborTiles(int index, int candidate)
	{
		addCandidateToNeighborTiles(index % 9, index / 9, candidate);
	}

	private void processNeighborTileCandidates(int x, int y)
	{
		Tile currentTile = getTile(x, y);

		int blockX = (x / 3) * 3;
		int blockY = (y / 3) * 3;
		int blockIdx = (blockY * 9) + blockX;

		for (int i = 0; i < 9; i++)
		{
			// We itereren over de X en Y as en de block in 1 loop

			// Zichzelf overslaan binnen de X as
			if (y != i)
			{
				Tile tileY = getTile(x, i);
				processTileCandidate(tileY, currentTile);
			}

			// Zichzelf overslaan binnen de Y as
			if (x != i)
			{
				Tile tileX = getTile(i, y);
				processTileCandidate(tileX, currentTile);
			}

			// Zichzelf overslaan binnen het block
			if ((blockIdx + i) != ((y * 9) + x))
			{
				Tile tileBlock = getTile(blockIdx + i);
				processTileCandidate(tileBlock, currentTile);
			}
		}
	}

	private void processTileCandidate(Tile tile, Tile otherTile)
	{
		// tile niet leeg
		if (otherTile.getValue() > 0)
		{
			tile.setCompCandidate(otherTile.getValue(), false);
		}
	}

	@Override
	public Tile[] getRow(int atX)
	{
		return null;
	}

	@Override
	public void setRow(int atX, Tile[] modifiedTiles)
	{
		// Stub
	}

	@Override
	public Tile[] getColumn(int atY)
	{
		return null;
	}

	@Override
	public void setColumn(int atY, Tile[] modifiedTiles)
	{
		// Stub
	}

	@Override
	public Tile[] getBlock(int x, int y)
	{
		int index = (y * 9) + x;

		/*
		 * if (tiles.length >= index) { getPossibilities moet Tile array[]
		 * returnen. } ??? WHAAAAT do you mean?? :S
		 */

		ArrayList<Tile> temp = new ArrayList<Tile>();

		// Let's get the index of the upper left corder of this block:
		index = this.upperLeftCornerIndex(index);

		for (int i = 0; i < 9; i++)
		{
			if (i % 3 == 0)
			{
				if (i != 0)
				{
					index += 7;
				}
			}
			else
			{
				index++;
			}
			temp.add(tiles[index]);
		}

		Tile[] fixedTemp = new Tile[9];

		for (int i = 0; i < temp.size(); i++)
		{
			fixedTemp[i] = temp.get(i);

		}
		return fixedTemp;
	}

	/**
	 * @return The index of upper left corner cell of the Block of the given
	 *         index.
	 * @param index
	 */
	private int upperLeftCornerIndex(int index)
	{
		{
			int x, y;

			// First the X:
			x = index % 9;
			if (x < 3)
			{
				x = 0;
			}
			else
				if (x > 5)
				{
					x = 2;
				}
				else
				{
					x = 1;
				}

			// Now the Y:
			y = index / 9;
			if (y < 3)
			{
				y = 0;
			}
			else
				if (y > 5)
				{
					y = 2;
				}
				else
				{
					y = 1;
				}

			return (x * 3) + (y * 27);
		}
	}

	@Override
	public void setBlock(int atX, int atY, Tile[] modifiedTiles)
	{
		// Stub
	}

	@Override
	public Tile askHintAction()
	{
		// TODO: Fixen, placeholder:
		return null;
		// return new Tile(2,5);
	}

	@Override
	public Boolean checkNewState()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
