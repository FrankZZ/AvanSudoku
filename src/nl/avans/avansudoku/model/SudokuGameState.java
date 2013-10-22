package nl.avans.avansudoku.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Stack;

public class SudokuGameState implements GameState
{

	private Tile[]		tiles	= new Tile[9 * 9];
	private Tile[]		startTiles;
	private Stack<Tile>	undoStack;

	public SudokuGameState()
	{
		for (int i = 0; i < tiles.length; i++)
			tiles[i] = new Tile(i, 0, false, 0);
		undoStack = new Stack();
	}

	@Override
	public void addStartState(Tile[] tiles)
	{
		// Start state
		startTiles = tiles;
	}

	@Override
	public void updateCurrentState(Tile tile)
	{
		//is voor gui doe er wat mee of gooi hem weg! -  Maurits
	}

	@Override
	public void addUndoAction(Tile tile)
	{
		undoStack.add(tile);
	}

	@Override
	public void resetToStartState()
	{
		tiles = startTiles;
		undoStack.clear();
	}

	@Override
	public Tile retrieveUndoAction()
	{
		return undoStack.pop();
	}

	@Override
	public void undoLastAction()
	{
		undoStack.pop();
		Tile lastActionTile = undoStack.pop();
		int lastActionIndex = lastActionTile.getIndex();
		tiles[lastActionIndex].setValue(lastActionTile.getValue());
		
		//make the computer generate new candidates
		generateCandidatesForField();
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
	public void setRow(int y, Tile[] modifiedTiles)
	{
		int arrIndex = y*9;
		for(int i = 0; i < 9; i++)
		{
			tiles[arrIndex + i] = modifiedTiles[i];
		}
	}

	@Override
	public Tile[] getColumn(int x)
	{
		Tile[] result = new Tile[9];
		for(int i = 0; i < 9; i++)
		{
			result[i] = tiles[x + 9*i];
		}
		return result;
	}

	@Override
	public void setColumn(int x, Tile[] modifiedTiles)
	{
		for(int i = 0; i < 9; i++)
		{
			tiles[x + 9*i] = modifiedTiles[i];
		}
	}

	@Override
	public Tile[] getBlock(int x, int y)
	{
		Tile[] result = new Tile[9];
		int blockX = (x%3)*3;
		int blockY = (y%3)*3;
		int resultIterator = 0;

		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				int index = ((blockY+i)*9) + (blockX+j);
				result[resultIterator] = tiles[index];
				resultIterator++;
			}
		}

		return result;
	}

	@Override
	public void setBlock(int x, int y, Tile[] modifiedTiles)
	{
		int blockX = (x%3)*3;
		int blockY = (y%3)*3;
		int resultIterator = 0;

		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				int index = ((blockY+i)*9) + (blockX+j);
				tiles[index] = modifiedTiles[resultIterator];
				resultIterator++;
			}
		}
	}

	@Override
	public Tile askHintAction()
	{
		// TODO: Fixen, placeholder:
		return null;
	}

	@Override
	public Boolean checkNewState()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
