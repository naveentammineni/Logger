package com.example.logger;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class RegisterActivity extends Activity {

	EditText taskNameView;
	EditText taskAddressView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		Parse.initialize(this, "ECVnSoqGDMWp2drP4iLSFgDMXShh9dzq3gttYsDi", "3xdcVEG2m8K6l7N5Ojp7dg65yuIUhc9t5SVcX8nD");
		 taskNameView = (EditText)findViewById(R.id.editText1);
		 taskAddressView = (EditText)findViewById(R.id.editText2);
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	public void onClickCancel(View view){
		Intent intent = new Intent(this,TabLayoutActivity.class);
		startActivity(intent);
	}
	public void onClickRegister(View view){
		String taskName = taskNameView.getText().toString();
		String taskAddress = taskAddressView.getText().toString();
		ParseObject post = new ParseObject("Tasks");
		  post.put("UserName", "naveen");
		  post.put("Location", taskAddress);
		  post.put("TaskName", taskName);
		  // Save the post and return
		  post.saveInBackground(new SaveCallback () {
		 
		    @Override
		    public void done(ParseException e) {
		      if (e == null) {
		    	  Toast.makeText(getApplicationContext(), "Saved Succesfully", Toast.LENGTH_LONG).show();
		    	  Intent intent = new Intent(RegisterActivity.this,TabLayoutActivity.class);
		  			startActivity(intent);
		  			finish();
		      } else {
		        Toast.makeText(getApplicationContext(),
		        "Error saving: " + e.getMessage(),
		               Toast.LENGTH_SHORT)
		               .show();
		      }
		    }
		 
		  });
	}
	public void onClickGetAddress(View view){
		turnGPSOn(); // method to turn on the GPS if its in off state.
        getMyCurrentLocation(); 
	}
	/** Method to turn on GPS **/

    public void turnGPSOn(){
        try
        {     

        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);    

        if(!provider.contains("gps")){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
        }
        catch (Exception e) {           

        }
    }
 // Method to turn off the GPS
    public void turnGPSOff(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if(provider.contains("gps")){ //if gps is enabled

            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }  

    // turning off the GPS if its in on state. to avoid the battery drain.

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        turnGPSOff();
    }
    /** Check the type of GPS Provider available at that instance and  collect the location informations
    @Output Latitude and Longitude
   * */
   void getMyCurrentLocation() {    

       LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
       LocationListener locListener = new MyLocationListener();  
        try{gps_enabled=locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);}catch(Exception ex){}           try{network_enabled=locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);}catch(Exception ex){}
           //don't start listeners if no provider is enabled

           //if(!gps_enabled && !network_enabled)

               //return false;

           if(gps_enabled){
               locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);              

           }


           if(gps_enabled){
               location=locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

           }          

           if(network_enabled && location==null){

               locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);             

           }  
           if(network_enabled && location==null)    {
               location=locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

           }

       if (location != null) {          

           MyLat = location.getLatitude();
           MyLong = location.getLongitude();
       } else {
           Location loc= getLastKnownLocation(this);
           if (loc != null) {  
               MyLat = loc.getLatitude();
               MyLong = loc.getLongitude();
           }
       }

       locManager.removeUpdates(locListener); // removes the periodic updates from location listener to avoid battery drainage. If you want to get location at the periodic intervals call this method using pending intent.

       try
       {
//Getting address from found locations.
       Geocoder geocoder;  
       List<Address> addresses;
       geocoder = new Geocoder(this, Locale.getDefault());
        addresses = geocoder.getFromLocation(MyLat, MyLong, 1);
        Address returnedAddress = addresses.get(0);
        StringBuilder strReturnedAddress = new StringBuilder("");
        for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
       	    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(", ");
        }
      
       String locationAdress = strReturnedAddress.toString();
       taskAddressView.setText(locationAdress);
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
       
   }  

   // Location listener class. to get location.
   public class MyLocationListener implements LocationListener {
       public void onLocationChanged(Location location) {
           if (location != null) {
           }
       }        public void onProviderDisabled(String provider) {
           // TODO Auto-generated method stub
       }

       public void onProviderEnabled(String provider) {
           // TODO Auto-generated method stub
       }

       public void onStatusChanged(String provider, int status, Bundle extras) {
           // TODO Auto-generated method stub
       }
   }

  

   private boolean gps_enabled=false;
   private boolean network_enabled=false;
   Location location; 
   Double MyLat, MyLong;



//below method to get the last remembered location. because we don't get locations all the times .At some instances we are unable to get the location from GPS. so at that moment it will show us the last stored location. 

   public static Location getLastKnownLocation(Context context)

   {
       Location location = null;
       LocationManager locationmanager = (LocationManager)context.getSystemService("location");
       List list = locationmanager.getAllProviders();
       boolean i = false;
       Iterator iterator = list.iterator();
       do
       {
           //System.out.println("---------------------------------------------------------------------");
           if(!iterator.hasNext())
               break;
           String s = (String)iterator.next();
           //if(i != 0 && !locationmanager.isProviderEnabled(s))
           if(i != false && !locationmanager.isProviderEnabled(s))
               continue;
          // System.out.println("provider ===> "+s);
           Location location1 = locationmanager.getLastKnownLocation(s);
           if(location1 == null)
               continue;
           if(location != null)
           {
               //System.out.println("location ===> "+location);
               //System.out.println("location1 ===> "+location);
               float f = location.getAccuracy();
               float f1 = location1.getAccuracy();
               if(f >= f1)
               {
                   long l = location1.getTime();
                   long l1 = location.getTime();
                   if(l - l1 <= 600000L)
                       continue;
               }
           }
           location = location1;
          // System.out.println("location  out ===> "+location);
           //System.out.println("location1 out===> "+location);
           i = locationmanager.isProviderEnabled(s);
          // System.out.println("---------------------------------------------------------------------");
       } while(true);
       return location;    
       }
}
