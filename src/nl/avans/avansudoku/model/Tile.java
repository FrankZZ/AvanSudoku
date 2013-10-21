/**
 * 
 */
package nl.avans.avansudoku.model;

import java.util.Arrays;

/**
 * @author Rick van Son
 * @version 1.2
 * @since 2013-09-29
 * 
 */
public class Tile
{

	private int			index;
	private int			x;
	private int			y;
	private boolean[]	compCandidates;
	private boolean[]	userCandidates;
	private int			compCandidateCount;
	private int			userCandidateCount;
	private int			value;
	private int			correctValue;
	private boolean		locked;

	// constructor 1 (for placing a tile with an horizontal and vertical value)
	public Tile(int x, int y, int value, boolean isLocked, int correctValue)
	{
		this.setX(x);
		this.setY(y);
		this.setIndex((y * 9) + x);
		this.setValue(value);
		this.compCandidates = new boolean[9];
		Arrays.fill(compCandidates, Boolean.TRUE);
		this.userCandidates = new boolean[9];
		Arrays.fill(userCandidates, Boolean.FALSE);
		this.compCandidateCount = 9;
		for (int i = 0; i < compCandidates.length; i++)
		{
			this.compCandidates[i] = true;
		}

		this.setLocked(isLocked);
		this.setCorrectValue(correctValue);
	}

	// constructor 2 (for placing a tile with an indexNr)
	public Tile(int index, int value, boolean isLocked, int correctValue)
	{
		this(index % 9, index / 9, value, isLocked, correctValue);
	}

	/**
	 * @return the x
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 * @throws Throwable
	 */
	public void setX(int x)
	{
		if (x >= 0 && x < 9)
		{
			this.x = x;
		}
	}

	/**
	 * @return the y
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 * @throws Throwable
	 */
	public void setY(int y)
	{
		if (y >= 0 && y < 9)
		{
			this.y = y;
		}
	}

	/**
	 * @return the index
	 */
	public int getIndex()
	{
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 * @throws Throwable
	 */
	public void setIndex(int index)
	{
		if (index >= 0 && index < 81)
		{
			this.index = index;
		}
	}

	/**
	 * @return the value of the cell
	 */
	public int getValue()
	{
		return value;
	}

	/**
	 * @param number
	 *            the value to set
	 * @throws Throwable
	 */
	public void setValue(int value)
	{
		if (value >= 0 && value <= 9)
		{
			this.value = value;
		}
	}

	private boolean[] getUserCandidates()
	{
		return userCandidates;
	}

	private void setUserCandidates(boolean[] userCandidates)
	{
		this.userCandidates = userCandidates;
	}

	private int getUserCandidateCount()
	{
		return userCandidateCount;
	}

	/**
	 * @return the candidates (not those small numbers in the tile).
	 */
	public boolean[] getCompCandidates()
	{
		return compCandidates;
	}

	/**
	 * @return If the candidate on this position is a candidate.
	 * @param pos
	 *            the specific position.
	 * @throws Throwable
	 */
	public boolean isCompCandidate(int index)
	{
		if (index >= 0 && index < compCandidates.length)
		{
			return compCandidates[index];
		}
		return false;
	}

	/**
	 * @return the number of computer generated candidates
	 */
	public int getCompCandidateCount()
	{
		return compCandidateCount;
	}

	/**
	 * @param pos
	 *            the specific position.
	 * @param candidate
	 *            The new candidate of a specific position.
	 * @throws Throwable
	 */
	public void setCompCandidate(int index, boolean candidateValue)
	{
		if (candidateValue == true && compCandidates[index] != true)
		{
			//verhoog het aantal kandidaten met een als dit nog geen kandidaat was.
			compCandidateCount++;
		}
		else
		{
			if (candidateValue == false && compCandidates[index] != false)
			{
				//verlaag het aantal kandidaten met een als dit nog een kandidaat was.
				compCandidateCount--;
			}
		}

		this.compCandidates[index] = candidateValue;
	}
	
	public void setCompCandidates(boolean[] candidates)
	{
		compCandidates = candidates;
	}

	public boolean isLocked()
	{
		return this.locked;
	}

	/**
	 * 
	 * @param isLocked
	 *            Enable or disable changing of the tile by the user.
	 */
	public void setLocked(boolean isLocked)
	{
		this.locked = isLocked;
	}

	/**
	 * @return the correctValue
	 */
	public int getCorrectValue()
	{
		return correctValue;
	}

	/**
	 * @param correctValue
	 *            the correctValue to set
	 * @throws Throwable
	 */
	public void setCorrectValue(int correctValue)
	{
		if (correctValue > 0 && correctValue <= 9)
		{
			this.correctValue = correctValue;
		}
	}
}
