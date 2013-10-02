package nl.avans.avansudoku;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class DifficultySelectActivity extends Activity {

	public final static String Difficulty_Text = "difficultytext";
	public final static String Loaded = "loaded";
	
	String load;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_difficulty_select);
		Intent intent = getIntent();
		load = intent.getStringExtra(MainActivity.Loaded);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.difficulty_select, menu);
		return true;
	}
	public void easyGame(View view)
	{
		startGame(1, "Easy");
	}
	public void mediumGame(View view)
	{
		startGame(2, "Medium");
	}
	public void hardGame(View view)
	{
		startGame(3, "Hard");
	}
	public void extremeGame(View view)
	{
		startGame(4, "Extreme");
	}
	private void startGame(int difficulty, String difficultyText)
	{
		Intent intent = new Intent(this, GameActivity.class );
		intent.putExtra(Difficulty_Text, difficultyText);
		intent.putExtra(Loaded, load);
		startActivity(intent);
	}

}
