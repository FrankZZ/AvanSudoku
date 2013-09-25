/**
 * 
 */
package nl.avans.avansudoku.model;

/**
 * @author Rick van Son
 * @version 1.1
 * @since 2013-09-22
 * 
 */
public class Tile
{
	private int index;

	private int x;

	private int y;

	private int value;

	private boolean[] candidates;

	private boolean locked;

	public Tile( int x, int y, int number, boolean isLocked )
			throws Throwable
	{
		this.setX( x );
		this.setY( y );
		this.setIndex( index );
		this.setValue( value );
		this.setCandidates( new boolean[9] );
		for( int i = 0; i < candidates.length; i++ )
		{
			this.candidates[i] = false;
		}
		this.setLocked( isLocked );
	}

	
	public Tile( int x, int y, int index, int number, boolean isLocked )
			throws Throwable
	{
		this.setX( x );
		this.setY( y );
		this.setIndex( index );
		this.setValue( value );
		this.setCandidates( new boolean[9] );
		for( int i = 0; i < candidates.length; i++ )
		{
			this.candidates[i] = false;
		}
		this.setLocked( isLocked );
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
	public void setX( int x ) throws Throwable
	{
		if( x < 0 || 9 <= x )
		{
			throw new Exception( "Oops! Wrong x value: " + x + "!" );
		}
		this.x = x;
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
	public void setY( int y ) throws Throwable
	{
		if( y < 0 || 9 <= y )
		{
			throw new Exception( "Oops! Wrong y value: " + y + "!" );
		}
		this.y = y;
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
	public void setIndex( int index ) throws Throwable
	{
		if( index < 0 || 81 <= index )
		{
			throw new Exception( "Oops! Wrong tile index: " + index + "!" );
		}
		this.index = index;
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
	public void setValue( int value ) throws Throwable
	{
		if( value < 0 || 9 < value )
		{
			throw new Exception( "Oops! Wrong value of tile no. " + this.index
					+ ": " + value + "!" );
		}
		this.value = value;
	}

	/**
	 * @return the candidates
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
	public void setCandidates( boolean[] candidates ) throws Throwable
	{
		if( candidates.length < 0 || 9 <= candidates.length )
		{
			throw new Exception( "Oops! Wrong candidates size of tile no. "
					+ this.index + ": " + candidates.length + "!" );
		}
		this.candidates = candidates;
	}

	/**
	 * @return The given candidate of a specific position.
	 * @param pos
	 *            the specific position.
	 * @throws Throwable
	 */
	public boolean getCandidate( int pos ) throws Throwable
	{
		if( pos < 0 || 9 <= pos )
		{
			throw new Exception( "Oops! Wrong candidate position of tile no. "
					+ this.index + ": " + pos + "!" );
		}
		return candidates[pos];
	}

	/**
	 * @param pos
	 *            the specific position.
	 * @param candidate
	 *            The new candidate of a specific position.
	 * @throws Throwable
	 */
	public void setCandidate( int pos, boolean candidate ) throws Throwable
	{
		if( pos < 0 || 9 <= pos )
		{
			throw new Exception( "Oops! Wrong candidate position of tile no. "
					+ this.index + ": " + pos + "!" );
		}
		this.candidates[pos] = candidate;
	}

	public boolean isLocked()
	{
		return this.locked;

	}

	public void setLocked( boolean isLocked )
	{
		this.locked = isLocked;

	}
}
