package com.example.logger;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class LogDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_details);
		 LayoutInflater inflater = (LayoutInflater) getActionBar().getThemedContext()
                 .getSystemService(LAYOUT_INFLATER_SERVICE);
         final View customActionBarView = inflater.inflate(
                 R.layout.actionbar_customlayout, null);
         
         //Actionbar modifications
         final ActionBar actionBar = getActionBar();
         actionBar.setDisplayShowHomeEnabled(false);
         actionBar.setDisplayShowTitleEnabled(false);
         actionBar.setDisplayShowCustomEnabled(true);
         actionBar.setCustomView(customActionBarView);
         
		final String receive = this.getIntent().getExtras().getString("objectId");
		TextView tv = (TextView)findViewById(R.id.textView3);
		final TextView startTimeView = (TextView)findViewById(R.id.textView5);
		final TextView endTimeView = (TextView)findViewById(R.id.textView7);
		tv.setText(receive);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Tasks");
	    // Restrict to cases where the author is the current user.
		query.whereEqualTo("UserName", "naveen");
		final List posts = new ArrayList();
		 // Run the query  
		query.findInBackground(new FindCallback<ParseObject>() {
		    @Override
		    public void done(List<ParseObject> postList,
		        ParseException e) {
		      if (e == null) {
		    	// If there are results, update the list of posts
		          // and notify the adapter
		          for (ParseObject post : postList) {
		        	  if(post.getString("TaskName").equalsIgnoreCase(receive)){
			        	  String startTime = post.getDate("TimeStarted").toString(); 
			        	  String endTime = post.getDate("TimeEnded").toString(); 
			        	  startTimeView.setText(startTime);
			        	  endTimeView.setText(endTime);
		        	  }
		          }
		          
		        }
		      else {
		        Log.d("Post retrieval", "Error: " + e.getMessage());
		      }
		    }
		                     
		  });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_details, menu);
		return true;
	}
	public void onClickClose(View v){
		this.finish();
	}
}
