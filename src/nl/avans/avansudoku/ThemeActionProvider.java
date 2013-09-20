package nl.avans.avansudoku;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;

@SuppressLint("NewApi")
public class ThemeActionProvider extends ActionProvider {

	@SuppressLint("NewApi")
	Context mContext;
	public ThemeActionProvider(Context context) {
		super(context);
		mContext = context;
	}

	@Override
	public View onCreateActionView() {
		LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.theme_picker,null);
        
		return view;
	}

}
