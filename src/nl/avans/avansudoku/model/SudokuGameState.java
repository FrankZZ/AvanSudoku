package nl.avans.avansudoku.model;

import java.util.ArrayList;
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
		undoStack.add(getTile((y * 9) + x));
	}

	public void setTileValue(int index, int value)
	{
		if (tiles[index].getValue()==0)
		{
			//make the computer generate new candidates
			removeCandidatesInNeighborTiles(index, value);
		}
		else
		{
			//make the computer generate new candidates
			generateCandidatesForField();
		}
		//change the value of the tile
		tiles[index].setValue(value);
	}

	//	Disabled for now. SetTile does not update the candidates in other tiles 
	//	and it's expensive to check what has updated on this tile maybe something for later.
	//	public void setTile(int x, int y, Tile newTile)
	//	{
	//		setTile((y * 9) + x, newTile);
	//	}
	//	
	//	public void setTile(int index, Tile newTile)
	//	{
	//		tiles[index] = newTile;
	//	}

	public Tile getTile(int x, int y)
	{
		return tiles[(y * 9) + x];
	}

	public Tile getTile(int idx)
	{
		return tiles[idx];
	}

	private void removeCandidatesInNeighborTiles(int index, int candidateValue)
	{
		int x = index % 9;
		int y = index / 9;

		removeCandidatesInRow(x, y, candidateValue);
		removeCandidatesInColumn(x, y, candidateValue);
		removeCandidatesInBlock(x, y, candidateValue);
	}

	private void removeCandidatesInRow(int x, int y, int candidateValue)
	{
		for (int i = 0; i < 9; i++)
		{
			if (i != x)
			{
				getTile(i, y).setCompCandidate(candidateValue, false);
			}
		}
	}

	private void removeCandidatesInColumn(int x, int y, int candidateValue)
	{
		for (int i = 0; i < 9; i++)
		{
			if (i != y)
			{
				getTile(x, i).setCompCandidate(candidateValue, false);
			}
		}
	}

	private void removeCandidatesInBlock(int x, int y, int candidateValue)
	{
		int xIndexBlock = x / 3;
		int yIndexBlock = y / 3;

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				if (i != x && j != y)
				{
					getTile(i * xIndexBlock, j * yIndexBlock).setCompCandidate(
							candidateValue, false);
				}
			}
		}
	}

	private void addCandidatesInNeighborTiles(int index, int candidateValue)
	{
		int x = index % 9;
		int y = index / 9;

		addCandidatesInNeighborTiles(x, y, candidateValue);
	}

	private void addCandidatesInNeighborTiles(int x, int y, int candidateValue)
	{
		addCandidatesInRow(x, y, candidateValue);
		addCandidatesInColumn(x, y, candidateValue);
		addCandidatesInBlock(x, y, candidateValue);
	}

	private void addCandidatesInRow(int x, int y, int candidateValue)
	{
		for (int i = 0; i < 9; i++)
		{
			if (i != x)
			{
				getTile(i, y).setCompCandidate(candidateValue, true);
			}
		}
	}

	private void addCandidatesInColumn(int x, int y, int candidateValue)
	{
		for (int i = 0; i < 9; i++)
		{
			if (i != y)
			{
				getTile(x, i).setCompCandidate(candidateValue, true);
			}
		}
	}

	private void addCandidatesInBlock(int x, int y, int candidateValue)
	{
		int xIndexBlock = x / 3;
		int yIndexBlock = y / 3;

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				if (i != x && j != y)
				{
					getTile(i * xIndexBlock, j * yIndexBlock).setCompCandidate(
							candidateValue, true);
				}
			}
		}
	}

	public void generateCandidatesForField()
	{
		for (int x = 0; x < 9; x++)
		{
			for (int y = 0; y < 9; y++)
			{
				generateCandidatesForTile(x,y);
			}
		}
	}

	public void generateCandidatesForTile(int x, int y)
	{
		Tile[] row = getRow(y);
		Tile[] column = getColumn(x);
		Tile[] block = getBlock(x,y);
		boolean[] candidates = new boolean[9];
		for (int i = 0; i < 9; i++)
		{
			//look for a value in the row on position i
			if(row[i].getValue()!=0)
			{
				candidates[row[i].getValue()-1] = true;
			}

			//look for a value in the column on position i
			if(column[i].getValue()!=0)
			{
				candidates[column[i].getValue()-1] = true;
			}

			//look for a value in the block on position i
			if(block[i].getValue()!=0)
			{
				candidates[block[i].getValue()-1] = true;
			}
		}
		tiles[y*9+x].setCompCandidates(candidates);
	}

	@Override
	public Tile[] getRow(int y)
	{
		Tile[] result = new Tile[9];
		int arrIndex = y*9;
		for(int i = 0; i < 9; i++)
		{
			result[i] = tiles[arrIndex + i];
		}
		return result;
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
		int blockX = x%3;
		int blockY = y%3;
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
		int blockX = x%3;
		int blockY = y%3;
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
