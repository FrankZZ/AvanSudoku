package nl.avans.avansudoku;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class LoadActivity extends Activity {

	public final static String Difficulty_Text = "difficultytext";
	public final static String Loaded = "loaded";
	String filename = "SudokuSave.txt";
    private static final String FILENAME = "myFile.txt";
	
	String load;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load);
		Intent intent = getIntent();
		load = intent.getStringExtra(MainActivity.Loaded);
		ListView lv = (ListView)findViewById(R.id.listView1);
		//FileInputStream fis = openFileInput(filename);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
                    long id) 
		{
	    	 
		}});  
		String textToSaveString = "Hello Android";
        
        writeToFile(textToSaveString);
         
        String textFromFileString =  readFromFile();
        
        if ( textToSaveString.equals(textFromFileString) ) 
            Toast.makeText(getApplicationContext(), "both string are equal", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "there is a problem", Toast.LENGTH_SHORT).show();
    }
     
    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(FILENAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
        } 
         
    }
 
    private String readFromFile() {
         
        String ret = "";
         
        try {
            InputStream inputStream = openFileInput(FILENAME);
             
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                 
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }
                 
                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
 
        return ret;
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.load, menu);
		return true;
	}
}
