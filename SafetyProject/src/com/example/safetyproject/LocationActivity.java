package com.example.safetyproject;

import android.location.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LocationActivity extends Activity {

	String locationProvider;
	LocationManager locationManager;
	Location location, currentBestLocation;
	TextView mytext;
	String name; 
	Long phone;
	int age;
	String healthStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
        
        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
       // locationProvider = LocationManager.GPS_PROVIDER;
        
        locationProvider = LocationManager.NETWORK_PROVIDER;
        locationManager.requestLocationUpdates(locationProvider, 10, 10, locationListener);
        location = locationManager.getLastKnownLocation(locationProvider);
        currentBestLocation = location;
        mytext = (TextView)findViewById(R.id.mytext);
        mytext.setText("Your first position is:\n" + "latitude: " +  location.getLatitude() + "\nlongitude: " + location.getLongitude() + "\n");
        
    }
    public void onClickUpdateLocation(View v){
    	locationManager.requestLocationUpdates(locationProvider, 10, 10, locationListener);
    	makeUseOfNewLocation(currentBestLocation);
    	final EditText nameField = (EditText) findViewById(R.id.EditTextName);  
    	name = nameField.getText().toString(); 
    	System.out.println("name: " + name);
    	final EditText ageField = (EditText) findViewById(R.id.EditTextAge);  
    	age = Integer.parseInt(ageField.getText().toString()); 
    	System.out.println("age: " + age);
//    	final EditText healthField = (EditText) findViewById(R.id.EditTextHealth);
//    	healthStatus = healthField.getText().toString(); 
//    	System.out.println("health: " + healthStatus);
    	
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void makeUseOfNewLocation(Location location)
    {
    	mytext.setText("Your latest position is:\n" + "latitude: " +  location.getLatitude() + "\nlongitude: " + location.getLongitude() + "\n");
   
    }
    
    
    
    
    
    
 // Acquire a reference to the system Location Manager
    //LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    
    // Define a listener that responds to location updates
    LocationListener locationListener = new LocationListener() {
    	public void onLocationChanged(Location location) {
          // Called when a new location is found by the network location provider.
          
    		if(isBetterLocation(location, currentBestLocation));
    		{
    			currentBestLocation = location;
    			
    		}
    		makeUseOfNewLocation(currentBestLocation);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        	//nothing for now
        }

        public void onProviderEnabled(String provider) {}

        public void onProviderDisabled(String provider) {}
      };

      private static final int TWO_MINUTES = 1000 * 60 * 2;

      /** Determines whether one Location reading is better than the current Location fix
        * @param location  The new Location that you want to evaluate
        * @param currentBestLocation  The current Location fix, to which you want to compare the new one
        */
      protected boolean isBetterLocation(Location location, Location currentBestLocation) {
          if (currentBestLocation == null) {
              // A new location is always better than no location
              return true;
          }

          // Check whether the new location fix is newer or older
          long timeDelta = location.getTime() - currentBestLocation.getTime();
          boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
          boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
          boolean isNewer = timeDelta > 0;

          // If it's been more than two minutes since the current location, use the new location
          // because the user has likely moved
          if (isSignificantlyNewer) {
              return true;
          // If the new location is more than two minutes older, it must be worse
          } else if (isSignificantlyOlder) {
              return false;
          }

          // Check whether the new location fix is more or less accurate
          int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
          boolean isLessAccurate = accuracyDelta > 0;
          boolean isMoreAccurate = accuracyDelta < 0;
          boolean isSignificantlyLessAccurate = accuracyDelta > 200;

          // Check if the old and new location are from the same provider
          boolean isFromSameProvider = isSameProvider(location.getProvider(),
                  currentBestLocation.getProvider());

          // Determine location quality using a combination of timeliness and accuracy
          if (isMoreAccurate) {
              return true;
          } else if (isNewer && !isLessAccurate) {
              return true;
          } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
              return true;
          }
          return false;
      }

      /** Checks whether two providers are the same */
      private boolean isSameProvider(String provider1, String provider2) {
          if (provider1 == null) {
            return provider2 == null;
          }
          return provider1.equals(provider2);
      }
      
}
