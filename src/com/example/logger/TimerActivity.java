package com.example.logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class TimerActivity extends Activity {

	 int time = 20;  
	 Timer t;  
	 TimerTask task;  
	 Button stop;  
	 Time now;
	 Time Start,Finish;
	  @Override  
	 protected void onCreate(Bundle savedInstanceState) {  
	  
	   super.onCreate(savedInstanceState);  
	  setContentView(R.layout.activity_timer);    
	  Intent intent = getIntent();
	  String tag =intent.getStringExtra("TAG");
	  
	  stop = (Button) findViewById(R.id.button1);  
	   now = new Time();
	   Start = new Time();
	   Finish =new Time();
	   stop.setOnClickListener(new OnClickListener() {  
	  
	    @Override  
	   public void onClick(View v) { 
	    	int min = now.minute - Start.minute;
	    	int hour = now.hour - Start.hour;
	    	int sec = now.second - Start.second;
	    	String timediff = hour+":"+min +":"+sec;
	    	
	    	finish();
	   }  
	  });  
	 
     time = 20;  
     startTimer();       
     Start = now;     
		 
	  
	  
	  ListView list = (ListView) findViewById(R.id.mylist);
	  ArrayList<HashMap<String, String>> mylistData =
	                     new ArrayList<HashMap<String, String>>();
	   
	  String[] columnTags = new String[] {"col1", "col2"};
	   
	  int[] columnIds = new int[] {R.id.column1, R.id.column2};
	  
	  HashMap<String,String> map = new HashMap<String, String>();
	  HashMap<String,String> map1 = new HashMap<String, String>();
	  HashMap<String,String> map2 = new HashMap<String, String>();
	  HashMap<String,String> map3 = new HashMap<String, String>();
	  HashMap<String,String> map4 = new HashMap<String, String>();
	  
	  
	  map.put("col1","02-01-2014");  
	  map.put("col2", "01:00:10"); 
	  
	  map1.put("col1","02-03-2014");  
	  map1.put("col2", "00:50:40"); 
	  
	  map2.put("col1","02-10-2014");  
	  map2.put("col2", "00:40:35"); 
	  
	  map3.put("col1","02-15-2014");  
	  map3.put("col2", "00:51:45"); 
	  
	  map4.put("col1","02-20-2014");  
	  map4.put("col2", "02:10:04"); 
	  
	  
	   mylistData.add(map);
	   mylistData.add(map1);
	   mylistData.add(map2);
	   mylistData.add(map3);
	   mylistData.add(map4);
	  
	  SimpleAdapter arrayAdapter =
	                 new SimpleAdapter(this, mylistData, R.layout.mylistrow,
	                               columnTags , columnIds);
	  list.setAdapter(arrayAdapter);
	  
	  
	  }  
	 public void startTimer(){  
	  t = new Timer();     
	  task = new TimerTask() {  
	  
	    @Override  
	   public void run() {  
	    runOnUiThread(new Runnable() {  
	  
	      @Override  
	     public void run() {  
	      TextView tv1 = (TextView) findViewById(R.id.textView1); 
	      now.setToNow();
	      tv1.setText(now.hour +":" + now.minute + ":"+ now.second);  
	     }  
	    });
	    
	   }  
	  };  
	  t.scheduleAtFixedRate(task, 0, 1000);  
	 }  

}
