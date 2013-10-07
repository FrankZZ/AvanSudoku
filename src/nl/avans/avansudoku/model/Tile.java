/**
 * 
 */
package nl.avans.avansudoku.model;

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

	private int			value;

	private boolean[]	candidates;

	private boolean		locked;

	private int			correctValue;

	public Tile(int x, int y, int value, boolean isLocked, int correctValue)
	{
		this.setX(x);
		this.setY(y);
		this.setIndex((y * 9) + x);
		this.setValue(value);
		this.setCandidates(new boolean[9]);
		for (int i = 0; i < candidates.length; i++)
		{
			this.candidates[i] = false;
		}
		this.setLocked(isLocked);
		this.setCorrectValue(correctValue);
	}

	public Tile(int index, int value, boolean isLocked, int correctValue)
	{
		int x = index % 9;
		int y = index / 9;

		this.setX(x);
		this.setY(y);
		this.setIndex(index);
		this.setValue(value);
		this.setCandidates(new boolean[9]);
		for (int i = 0; i < candidates.length; i++)
		{
			this.candidates[i] = false;
		}
		this.setLocked(isLocked);
		this.setCorrectValue(correctValue);
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
		if (x >= 0 || x < 9)
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
		if (y >= 0 || y < 9)
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
		if (index >= 0 || index < 81)
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
		if (value >= 0 || value < 9)
		{
			this.value = value;
		}

	}

	/**
	 * @return the candidates (those small numbers in the tile).
	 */
	public boolean[] getCandidates()
	{
		return candidates;
	}

	/**
	 * @param candidates
	 *            the candidates to set
	 * @throws Throwable
	 */
	public void setCandidates(boolean[] candidates)
	{
		this.candidates = candidates;
	}

	/**
	 * @return The given candidate of a specific position.
	 * @param pos
	 *            the specific position.
	 * @throws Throwable
	 */
	public boolean getCandidate(int pos) throws Throwable
	{
		if (pos >= 0 || pos < candidates.length)
		{
			return candidates[pos];
		}

		return false;

	}

	/**
	 * @param pos
	 *            the specific position.
	 * @param candidate
	 *            The new candidate of a specific position.
	 * @throws Throwable
	 */
	public void setCandidate(int pos, boolean candidate) throws Throwable
	{
		if (pos >= 0 || pos < candidates.length)
		{
			this.candidates[pos] = candidate;
		}
	}

	/**
	 * @return the amount of candidates of this tile
	 */
	public int getAmountOfCandidates()
	{
		return candidates.length;
	}

	public boolean isLocked()
	{
		return this.locked;

	}

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
		if (correctValue > 0 || correctValue <= 9)
		{
			this.correctValue = correctValue;
		}
	}
}
