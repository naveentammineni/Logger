package com.example.logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class StartupActivity extends Activity {
	public static String TAG="StartupActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startup);

        try{
          /*  LayoutInflater inflater = (LayoutInflater) getActionBar().getThemedContext()
                    .getSystemService(LAYOUT_INFLATER_SERVICE);
            final View customActionBarView = inflater.inflate(
                    R.layout.actionbar_customlayout, null);
            
            //Actionbar modifications
            final ActionBar actionBar = getActionBar();
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(customActionBarView);
            */
            // Add your initialization code here
    		Parse.initialize(this, "ECVnSoqGDMWp2drP4iLSFgDMXShh9dzq3gttYsDi", "3xdcVEG2m8K6l7N5Ojp7dg65yuIUhc9t5SVcX8nD");
    		final List posts = new ArrayList();
    	   final ListView listview = (ListView) findViewById(R.id.listView1);
		   final StableArrayAdapter adapter = new StableArrayAdapter(this,
			        android.R.layout.simple_list_item_1, posts);
	   		listview.setAdapter(adapter);
	   		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	   	      @Override
	   	      public void onItemClick(AdapterView<?> parent, final View view,
	   	          final int position, long id) {
	   	        final String item = (String) parent.getItemAtPosition(position);
	   	        view.animate().setDuration(20).alpha(50).withStartAction(new Runnable() {
	   	              @Override
	   	              public void run() {
	   	              Intent intent = new Intent(StartupActivity.this,LogDetailsActivity.class);
	   	              	intent.putExtra("objectId",posts.get(position).toString());
	   	           		startActivity(intent);
	   	              }
	   	            });
	   	      }

	   	    });
	   	// Create query for objects of type "Post"
	   		ParseQuery<ParseObject> query = ParseQuery.getQuery("Tasks");
			    // Restrict to cases where the author is the current user.
	   		query.whereEqualTo("UserName", "naveen");
	   		
			    // Run the query  
	   		query.findInBackground(new FindCallback<ParseObject>() {
				    @Override
				    public void done(List<ParseObject> postList,
				        ParseException e) {
				      if (e == null) {
				    	// If there are results, update the list of posts
				          // and notify the adapter
				          posts.clear();
				          for (ParseObject post : postList) {
				            posts.add(post.get("TaskName"));
				          }
				          adapter.notifyDataSetChanged();
				        }
				      else {
				        Log.d("Post retrieval", "Error: " + e.getMessage());
				      }
				    }
				                     
				  });
        }catch (Exception e){
            e.printStackTrace();
         }
	   		
	}
	private class StableArrayAdapter extends ArrayAdapter<String> {

	    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

	    public StableArrayAdapter(Context context, int textViewResourceId,
	        List<String> objects) {
	      super(context, textViewResourceId, objects);
	      for (int i = 0; i < objects.size(); ++i) {
	        mIdMap.put(((ParseObject)objects).getString("TaskName"), i);
	      }
	    }

	    @Override
	    public long getItemId(int position) {
	      String item = getItem(position);
	      if(mIdMap == null){
	    	  return 0;
	      }
	      else if(mIdMap.isEmpty()){
	    	  return 0;
	      }
	      return mIdMap.get(item);
	    }

	    @Override
	    public boolean hasStableIds() {
	      return true;
	    }

	  }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.startup, menu);
		return true;
	}
	

}
