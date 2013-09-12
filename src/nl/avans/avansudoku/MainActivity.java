package nl.avans.avansudoku;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/*
	 * Probeer een spelsituatie op te slaan
	 */
	private void trySave()
	{
		if (FileManager.save(getApplicationContext()))
		{
			Toast.makeText(getApplicationContext(), "Successvol opgeslagen",
					Toast.LENGTH_LONG).show();
		}
		else
		{
			Toast.makeText(getApplicationContext(), "Fout opgetreden bij opslaan",
					Toast.LENGTH_LONG).show();
		}
	}
}