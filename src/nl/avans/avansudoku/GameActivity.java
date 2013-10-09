package nl.avans.avansudoku;

/*import model.SudokuGameState;
 import model.Tile;*/
import nl.avans.avansudoku.R;
import nl.avans.avansudoku.control.*;
import nl.avans.avansudoku.model.*;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class GameActivity extends Activity
{

	double[]		sudokuveld		= new double[81];
	private int		vak0ID;
	SudokuGameState	sudokugamestate	= new SudokuGameState();
	SudokuCreator	sudokucreater	= new SudokuCreator();

	int				theme			= 0;
	int[]			symbols			= new int[10];

	int				selectednumber	= 0;
	int				prevselectedID	= 0;
	int				prevChalk		= 0;
	boolean			groot			= true;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		Intent intent = getIntent();
		String message = intent
				.getStringExtra(DifficultySelectActivity.Difficulty_Text);
		TextView mTextView = (TextView) findViewById(R.id.Sudokunaam);
		mTextView.setText(message);
		int load = intent.getIntExtra(MainActivity.Loaded, 0);
		if (load == 1)
		{
			LoadSudoku();
		}
		setSymbols();
		for (int i = 0; i < sudokuveld.length; i++)
		{
			sudokuveld[i] = 0;
		}
		vak0ID = ((ImageView) findViewById(R.id.v00)).getId();
		NewSudoku();
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		MenuItem s1 = menu.findItem(R.id.spinner1);
		View v1 = s1.getActionView();
		if (v1 instanceof Spinner)
		{
			final Spinner spinner = (Spinner) v1;
			ArrayAdapter<CharSequence> adapter = ArrayAdapter
					.createFromResource(this,
							nl.avans.avansudoku.R.array.themes_arrays,
							android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);
			spinner.setOnItemSelectedListener(new OnItemSelectedListener()
			{

				@Override
				public void onItemSelected(AdapterView<?> parent, View arg1,
						int arg2, long arg3)
				{
					theme = parent.getSelectedItemPosition();
					setSymbols();
					redrawSudokuAll();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0)
				{
				}
			});
		}
		return true;
	}

	private void LoadSudoku()
	{

	}

	private void NewSudoku()
	{
		try
		{
			// sudokuveld =
			SudokuGameState gameState = sudokucreater.getGameState(); // geeft
																		// hopelijk
																		// iets
																		// terug
			String str = "";
			for (int i = 0; i < 81; i++)
			{
				if (i % 9 == 0)
					str += "\n";
				
				sudokuveld[i] = gameState.getTile(i).getValue();
				str += gameState.getTile(i).getValue();
				
				
			}
			Log.d("dfa", str);
			// sudokugamestate.setStartState(sudokuveld);
			redrawSudokuAll();
		}
		catch (Throwable tr)
		{
			tr.printStackTrace();
		}
	}

	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle presses on the action bar items
		item.getItemId();
		return true;
	}

	public void Restart(View view)
	{
		for (int idx = 0; idx < sudokuveld.length; idx++)
		{
			double waarde = sudokuveld[idx];
			if (waarde < 10 || waarde > 20)
			{
				sudokuveld[idx] = 0;
			}
		}
		prevChalk = 0;
		prevselectedID = 0;
		redrawSudokuAll();
	}

	public void Undo(View view)
	{
		Tile undo = sudokugamestate.retrieveUndoAction();
		if (undo != null)
		{
			int vak = undo.getIndex();
			double waarde = undo.getValue();
			sudokuveld[vak] = waarde;
			redrawSudokuOne(vak);
		}
	}

	public void Hint(View view)
	{
		Tile hint = sudokugamestate.askHintAction();

		int vak = hint.getIndex();
		double waarde = hint.getValue();
		sudokuveld[vak] = waarde + 40;
		redrawSudokuOne(vak);
	}

	public void GrootteAanpassen(View view)
	{
		int id = view.getId();
		if (groot)
		{
			((ImageView) findViewById(id))
					.setImageResource(R.drawable.chalkklein);
			groot = false;
		}
		else
		{
			((ImageView) findViewById(id))
					.setImageResource(R.drawable.chalkgroot);
			groot = true;
		}
	}

	private void updateState(int locatie)
	{
		// if(sudokugamestate.updateCurrentState(sudokuveld))
		redrawSudokuOne(locatie);
	}

	private void setSymbols()
	{
		if (theme == 0)
		{
			symbols[0] = R.drawable.chalk0;
			symbols[1] = R.drawable.chalk1;
			symbols[2] = R.drawable.chalk2;
			symbols[3] = R.drawable.chalk3;
			symbols[4] = R.drawable.chalk4;
			symbols[5] = R.drawable.chalk5;
			symbols[6] = R.drawable.chalk6;
			symbols[7] = R.drawable.chalk7;
			symbols[8] = R.drawable.chalk8;
			symbols[9] = R.drawable.chalk9;
		}
		else
		{
			symbols[0] = R.drawable.chalk0;
			symbols[1] = R.drawable.dora1;
			symbols[2] = R.drawable.dora2;
			symbols[3] = R.drawable.dora3;
			symbols[4] = R.drawable.dora4;
			symbols[5] = R.drawable.dora5;
			symbols[6] = R.drawable.dora6;
			symbols[7] = R.drawable.dora7;
			symbols[8] = R.drawable.dora8;
			symbols[9] = R.drawable.dora9;
		}
	}

	private void redrawSudokuAll()
	{
		for (int idx = 0; idx < 81; idx++)
		{
			ImageView imageview = (ImageView) findViewById(vak0ID + idx);
			double waarde = sudokuveld[idx];
			if (waarde >= 40)
			{
				imageview
						.setImageDrawable(HintChalk(symbols[(int) waarde - 40]));
			}
			else
				if (waarde >= 30)
				{
					imageview
							.setImageDrawable(WrongChalk(symbols[(int) waarde - 30]));
				}
				else
					if (waarde >= 20)
					{
						if (waarde % 1 != 0.0)
							imageview
									.setImageDrawable(selectedChalk(symbols[(int) waarde - 20]));
						else
							imageview.setImageDrawable(selectedSmallChalk(
									symbols[(int) waarde - 20], waarde));
					}
					else
						if (waarde >= 10)
						{
							imageview
									.setImageDrawable(PredeterminedChalk(symbols[(int) waarde - 10]));
						}
						else
						{
							if (waarde == 0.0)
								imageview
										.setImageResource(symbols[(int) waarde]);
							else
								imageview.setImageDrawable(smallChalk(
										symbols[(int) waarde], waarde));
						}
		}
		((ImageView) findViewById(R.id.c1)).setImageResource(symbols[1]);
		((ImageView) findViewById(R.id.c2)).setImageResource(symbols[2]);
		((ImageView) findViewById(R.id.c3)).setImageResource(symbols[3]);
		((ImageView) findViewById(R.id.c4)).setImageResource(symbols[4]);
		((ImageView) findViewById(R.id.c5)).setImageResource(symbols[5]);
		((ImageView) findViewById(R.id.c6)).setImageResource(symbols[6]);
		((ImageView) findViewById(R.id.c7)).setImageResource(symbols[7]);
		((ImageView) findViewById(R.id.c8)).setImageResource(symbols[8]);
		((ImageView) findViewById(R.id.c9)).setImageResource(symbols[9]);

	}

	private void redrawSudokuOne(int index)
	{
		ImageView imageview = (ImageView) findViewById(vak0ID + index);
		double waarde = sudokuveld[index];
		if (waarde >= 40)
		{
			imageview.setImageDrawable(HintChalk(symbols[(int) waarde - 40]));
		}
		else
			if (waarde >= 30)
			{
				imageview
						.setImageDrawable(WrongChalk(symbols[(int) waarde - 30]));
			}
			else
				if (waarde >= 20)
				{
					if (waarde % 1 == 0.0)
						imageview
								.setImageDrawable(selectedChalk(symbols[(int) waarde - 20]));
					else
						imageview.setImageDrawable(selectedSmallChalk(
								symbols[(int) waarde - 20], waarde));
				}
				else
					if (waarde >= 10)
					{
						imageview
								.setImageDrawable(PredeterminedChalk(symbols[(int) waarde - 10]));
					}
					else
					{
						if (waarde % 1 == 0.0)
							imageview.setImageResource(symbols[(int) waarde]);
						else
							imageview.setImageDrawable(smallChalk(
									symbols[(int) waarde], waarde));
					}
	}

	private BitmapDrawable smallChalk(int chalk, double waarde)
	{
		Bitmap bg = BitmapFactory.decodeResource(getResources(), chalk);
		Bitmap out = Bitmap.createBitmap(bg);
		Bitmap outc = out.copy(Bitmap.Config.ARGB_8888, true);
		Canvas combo = new Canvas(outc);
		waarde = waarde % 1;
		int breed = bg.getWidth();
		int hoog = bg.getHeight();
		for (int idx = 0; idx < 9; idx++)
		{
			waarde = waarde * 10;
			int value = (int) waarde % 10;
			if (value != 0)
			{
				Bitmap fg = BitmapFactory.decodeResource(getResources(),
						symbols[value]);
				float left = ((value - 1) % 3) * (breed / 3);
				float top = ((value - 1) / 3) * (hoog / 3);
				int b = breed / 3;
				int h = hoog / 3;
				fg = Bitmap.createScaledBitmap(fg, b, h, false);
				combo.drawBitmap(fg, left, top, null);
			}
		}
		return new BitmapDrawable(getResources(), outc);
	}

	private BitmapDrawable selectedSmallChalk(int chalk, double waarde)
	{
		Bitmap bg = BitmapFactory.decodeResource(getResources(), chalk);
		Bitmap out = Bitmap.createBitmap(bg);
		Bitmap outc = out.copy(Bitmap.Config.ARGB_8888, true);
		Canvas combo = new Canvas(outc);
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(5);
		waarde = waarde % 1;
		int breed = bg.getWidth();
		int hoog = bg.getHeight();
		for (int idx = 0; idx < 9; idx++)
		{
			waarde = waarde * 10;
			int value = (int) waarde % 10;
			if (value != 0)
			{
				Bitmap fg = BitmapFactory.decodeResource(getResources(),
						symbols[value]);
				float left = ((value - 1) % 3) * (breed / 3);
				float top = ((value - 1) / 3) * (hoog / 3);
				int b = breed / 3;
				int h = hoog / 3;
				fg = Bitmap.createScaledBitmap(fg, b, h, false);
				combo.drawBitmap(fg, left, top, null);
			}
		}
		combo.drawRoundRect(new RectF(0, 0, outc.getWidth(), outc.getHeight()),
				3, 3, paint);
		return new BitmapDrawable(getResources(), outc);
	}

	private BitmapDrawable selectedChalk(int chalk)
	{
		Bitmap bg = BitmapFactory.decodeResource(getResources(), chalk);
		Bitmap out = Bitmap.createBitmap(bg);
		Bitmap outc = out.copy(Bitmap.Config.ARGB_8888, true);
		Canvas combo = new Canvas(outc);
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(5);
		combo.drawRoundRect(new RectF(0, 0, outc.getWidth(), outc.getHeight()),
				3, 3, paint);
		return new BitmapDrawable(getResources(), outc);
	}

	private BitmapDrawable PredeterminedChalk(int chalk)
	{
		Bitmap fg = BitmapFactory.decodeResource(getResources(), chalk);
		Bitmap bg = BitmapFactory.decodeResource(getResources(),
				R.drawable.chalkpre);
		Bitmap out = Bitmap.createBitmap(bg);
		Bitmap outc = out.copy(Bitmap.Config.ARGB_8888, true);
		Canvas combo = new Canvas(outc);
		combo.drawBitmap(fg, 0, 0, null);
		return new BitmapDrawable(getResources(), outc);
	}

	private BitmapDrawable WrongChalk(int chalk)
	{
		Bitmap fg = BitmapFactory.decodeResource(getResources(), chalk);
		Bitmap bg = BitmapFactory.decodeResource(getResources(),
				R.drawable.chalkwrong);
		Bitmap out = Bitmap.createBitmap(bg);
		Bitmap outc = out.copy(Bitmap.Config.ARGB_8888, true);
		Canvas combo = new Canvas(outc);
		combo.drawBitmap(fg, 0, 0, null);
		return new BitmapDrawable(getResources(), outc);
	}

	private BitmapDrawable HintChalk(int chalk)
	{
		Bitmap fg = BitmapFactory.decodeResource(getResources(), chalk);
		Bitmap bg = BitmapFactory.decodeResource(getResources(),
				R.drawable.chalkhint);
		Bitmap out = Bitmap.createBitmap(bg);
		Bitmap outc = out.copy(Bitmap.Config.ARGB_8888, true);
		Canvas combo = new Canvas(outc);
		combo.drawBitmap(fg, 0, 0, null);
		return new BitmapDrawable(getResources(), outc);
	}

	public void onClickV(View view)
	{
		int id = view.getId();
		int lijstlocatie = id - vak0ID;
		int prevlijstlocatie = prevselectedID - vak0ID;
		double waarde = sudokuveld[lijstlocatie];
		if (waarde < 10 || waarde > 20)
		{
			sudokuveld[lijstlocatie] += 20;
			if (prevselectedID != 0 && sudokuveld[prevlijstlocatie] < 30)
			{
				sudokuveld[prevlijstlocatie] -= 20;
				updateState(prevlijstlocatie);
			}
			prevselectedID = id;
			updateState(lijstlocatie);
		}
	}

	public void onClickInvoer(View view)
	{
		int id = view.getId();
		int lijstlocatie = prevselectedID - vak0ID;
		double waarde = sudokuveld[lijstlocatie];
		if (waarde < 10 || waarde >= 20)
		{
			String naam = getResources().getResourceName(id);
			int nummer = Integer.parseInt(naam.split("/c")[1]);

			if (groot)
			{
				sudokugamestate.getTile(lijstlocatie).setValue(nummer);

				if (sudokugamestate.checkNewState())
					sudokuveld[lijstlocatie] = nummer + 20;

				else
					sudokuveld[lijstlocatie] = nummer + 30;
			}
			else
			{
				double deler = Math.pow(10, nummer);
				double Nummer = (nummer / deler) + 0.0000000001;
				if (waarde % 1 != 0)
				{
					int value = (int) (waarde * deler) % 10;
					if (value != nummer)
						sudokuveld[lijstlocatie] += Nummer;
				}
				else
					sudokuveld[lijstlocatie] += Nummer;
			}
			updateState(lijstlocatie);
		}
	}
}
