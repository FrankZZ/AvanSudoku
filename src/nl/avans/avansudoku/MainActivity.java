package nl.avans.avansudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends Activity {
  public static final String THEMA = "themaint";
private EditText text;

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
	  startActivity(intent);
  }

  // Converts to celsius
  private float convertFahrenheitToCelsius(float fahrenheit) {
    return ((fahrenheit - 32) * 5 / 9);
  }

  // Converts to fahrenheit
  private float convertCelsiusToFahrenheit(float celsius) {
    return ((celsius * 9) / 5) + 32;
  }
} 