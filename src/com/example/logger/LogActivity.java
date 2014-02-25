package com.example.logger;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

public class LogActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log);
		 LayoutInflater inflater = (LayoutInflater) getActionBar().getThemedContext()
                 .getSystemService(LAYOUT_INFLATER_SERVICE);
         final View customActionBarView = inflater.inflate(
                 R.layout.actionbar_customlayout, null);
         
         //Actionbar modifications
         final ActionBar actionBar = getActionBar();
         actionBar.setDisplayShowHomeEnabled(false);
         actionBar.setDisplayShowTitleEnabled(false);
         actionBar.setDisplayShowCustomEnabled(true);
         View cView = getLayoutInflater().inflate(R.layout.actionbar_customlayout, null);
         actionBar.setCustomView(cView);
         
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
