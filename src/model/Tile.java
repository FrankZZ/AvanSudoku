/**
 * 
 */
package model;

/**
 * @author Rick van Son
 * @version 1.0
 * @since 2013-09-18
 * 
 */
public class Tile
{
	private int index;

	private int value;

	private boolean[] candidates;

	public Tile( int index, int number ) throws Throwable
	{
		this.setIndex( index );
		this.setValue( value );
		this.setCandidates( new boolean[9] );
		for( int i = 0; i < candidates.length; i++ )
		{
			this.candidates[i] = false;
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
			throw new Exception( "Oops! Wrong value of tile no. "
					+ this.index + ": "  + value + "!" );
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

}
