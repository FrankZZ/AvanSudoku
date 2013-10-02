package nl.avans.avansudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
  public static final String Loaded = "loaded";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

  }

  // This method is called at button click because we assigned the name to the
  // "OnClick property" of the button
  public void onClick(View view) {
	  switch(view.getId())
	  {
	  }
  }
  public void newGame(View view)
  {
	  Intent intent = new Intent(this, DifficultySelectActivity.class );
		intent.putExtra(Loaded, 0);
	  startActivity(intent);
  }
  public void loadGame(View view)
  {
	  Intent intent = new Intent(this, LoadActivity.class );
		intent.putExtra(Loaded, 1);
	  startActivity(intent);
  }
} 