package com.example.logger;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabLayoutActivity extends TabActivity {
	private NfcAdapter nfcAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_layout);
		 LayoutInflater inflater = (LayoutInflater) getActionBar().getThemedContext()
                 .getSystemService(LAYOUT_INFLATER_SERVICE);
         final View customActionBarView = inflater.inflate(
                 R.layout.actionbar_customlayout, null);
         nfcAdapter = NfcAdapter.getDefaultAdapter(this);
         //Actionbar modifications
         final ActionBar actionBar = getActionBar();
         actionBar.setDisplayShowHomeEnabled(false);
         actionBar.setDisplayShowTitleEnabled(false);
         actionBar.setDisplayShowCustomEnabled(true);
         View cView = getLayoutInflater().inflate(R.layout.actionbar_customlayout, null);
         actionBar.setCustomView(cView);
         
		TabHost tabHost = getTabHost();
        
        // Tab for Tasks
        TabSpec tasksTabSpec = tabHost.newTabSpec("Tasks");
        // setting Title and Icon for the Tab
        tasksTabSpec.setIndicator("Tasks");
        Intent tasksIntent = new Intent(this, StartupActivity.class);
        tasksTabSpec.setContent(tasksIntent);
        
        // Tab for Tasks
        TabSpec registerTabSpec = tabHost.newTabSpec("Register");
        // setting Title and Icon for the Tab
        registerTabSpec.setIndicator("Register");
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        registerTabSpec.setContent(registerIntent);
         
        
         
        // Adding all TabSpec to TabHost
        tabHost.addTab(tasksTabSpec); // Adding photos tab
        tabHost.addTab(registerTabSpec); // Adding songs tab
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tab_layout, menu);
		return true;
	}
	@Override
	protected void onResume() {
		super.onResume();
	  PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
	  nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
	}
	protected void onNewIntent(Intent intent) {
		  String action = intent.getAction();
		  if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)){
		    Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
		    NdefMessage[] messages;
		    if (rawMsgs != null) {
		      messages = new NdefMessage[rawMsgs.length];
		      for (int i = 0; i < rawMsgs.length; i++) {
		        messages[i] = (NdefMessage) rawMsgs[i];     
		        // To get a NdefRecord and its different properties from a NdefMesssage   
		     NdefRecord record = messages[i].getRecords()[i];
		     String message = getTextData(record.getPayload());
		     
		     AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						TabLayoutActivity.this);
		 
					// set title
					alertDialogBuilder.setTitle("New Log");
		 
					// set dialog message
					alertDialogBuilder
						.setMessage("Detected the Psychology Tag for your Activity. \n Do You want to start logging?")
						.setCancelable(false)
						.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, close
								// current activity
								Intent intent = new Intent(TabLayoutActivity.this, TimerActivity.class);
								intent.putExtra("TAG", "nfc");
								startActivity(intent);
							}
						  })
						.setNegativeButton("No",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, just close
								// the dialog box and do nothing
								dialog.cancel();
							}
						});
		 
						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();
		 
						// show it
						alertDialog.show();
		      }
		    }
		  }
	}
	// Decoding a payload containing text
	private String getTextData(byte[] payload) {
	  if(payload == null) 
	    return null;
	  try {
	    String encoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
	    int langageCodeLength = payload[0] & 0077;
	    return new String(payload, langageCodeLength + 1, payload.length - langageCodeLength - 1, encoding);     
	  } catch(Exception e) {
	    e.printStackTrace();
	  }
	  return null;
	}
}
