/**
 * 
 */
package control;

/**
 * @author Rick van Son
 * @version 1.0
 * @since 2013-09-22
 * 
 */
public abstract class GameRules
{
	public abstract boolean checkInput() throws Throwable;

	protected abstract boolean isValueAlreadyInThisRow( int atRowIndex,
			int onValue, int butNotColumnIndex );

	protected abstract boolean isValueAlreadyInThisColumn( int atColumnIndex,
			int onValue, int butNotRowIndex );

	protected abstract boolean isValueAlreadyInThisBlock( int atRowIndex,
			int atColumnIndex, int onValue );

	protected abstract void removeSuggestionsThen( int atRowIndex,
			int atColumnIndex, int ofThisValue ) throws Throwable;
}
