package nl.avans.avansudoku;

import java.io.IOException;
import java.io.OutputStreamWriter;
import android.content.Context;
import android.util.Log;

class FileManager
{

	static String	_fileName	= "avansudoku.txt";

	//Example array
	static double[][]	_data	= 	{
								{9.0, 2.0, 3.0, 4.0, 5.02162, 6.41032, 7.1295, 8.4123, 9.18672},
								{7.0, 6.0, 3.0, 4.0, 5.02162, 9.41032, 4.1295, 8.4123, 9.184236},
								{3.0, 4.0, 3.0, 4.0, 5.02162, 7.41032, 2.1295, 8.4123, 9.18896},
								{2.0, 2.0, 3.0, 4.0, 5.02162, 5.41032, 9.1295, 8.4123, 9.18726},
								{5.0, 1.0, 7.0, 2.0, 4.021523, 1.41032, 7.1295, 8.4123, 9.18426},
								{1.0, 2.0, 3.0, 4.0, 5.02162, 6.41032, 7.1295, 8.4123, 6.18426},
								{1.0, 2.0, 3.0, 4.0, 5.02162, 6.41032, 7.1354, 8.4123, 7.18426},
								{1.0, 2.0, 3.0, 4.0, 5.02162, 7.41032, 7.1295, 8.4123, 2.18426},
								{1.0, 2.0, 3.0, 4.0, 5.02162, 6.41032, 7.1254, 2.4243, 1.14326}
							};


	public static boolean save(Context context)
	{
		try
		{
			// File writer openen in current context. MODE_PRIVATE: Alleen de huidige applicatie kan deze data openen
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					context.openFileOutput(_fileName, Context.MODE_PRIVATE));

			// Loop over alle rijen van de array heen, 1e dimensie
			for (int r = 0; r < _data.length; r++)
			{
				double[] row = _data[r];
				
				// Loop over alle kolommen heen, 2e dimensie
				for (int c = 0; c < row.length; c++)
				{
					double col = row[c];

					// Bij eerste rij en eerste kolom GEEN ; teken prependen
					outputStreamWriter.write( (r == 0 && c == 0 ? "" : ";") + Double.toString(col) );
				}
			}
			outputStreamWriter.close();
		}
		catch (IOException e)
		{
			Log.e("Exception", "Error occured " + e.toString());
			return false;
		}
		
		return true;

	}
}
