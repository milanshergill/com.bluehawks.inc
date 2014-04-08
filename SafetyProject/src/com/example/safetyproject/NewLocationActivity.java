package com.example.safetyproject;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class NewLocationActivity extends Activity {

	// Delta time to check new location's validity
	private static final int TWO_MINUTES = 1000 * 60 * 2;
	Location currentKnownLocation;
	LocationManager locationManager;
	LocationListener locationListener;
	String locationProvider;
	TextView locationText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_location);

		locationText = (TextView) findViewById(R.id.locationTextView);

		// Acquire a reference to the system Location Manager
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location
				// provider or GPS provider.
				makeUseOfNewLocation(location);
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
				// Add the newly enabled provider for location updates
				locationManager.requestLocationUpdates(provider, 0, 0,
						locationListener);
			}

			public void onProviderDisabled(String provider) {
			}
		};
	}

	protected void makeUseOfNewLocation(Location newLocation) {
		boolean isBetter = isBetterLocation(newLocation, currentKnownLocation);

		// Save new location as the current known location
		if (isBetter)
			currentKnownLocation = newLocation;
	}

	public void printCurrentLocation(View v) {
		if (currentKnownLocation != null) {
			// Set the textView to display current best location
			locationText.setText("Longitude: "
					+ currentKnownLocation.getLongitude() + "\nLatitude: "
					+ currentKnownLocation.getLatitude());
		} else {
			// Set the textView to display current best location
			locationText
					.setText("Longitude: Value is null\nLatitude: Value is null");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_location, menu);
		return true;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (locationManager != null) {

			// Register the listener with the Location Manager to receive
			// location
			// updates from the Network Provide - Cell Tower or Wifi
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

			// Register the listener with the Location Manager to receive
			// location
			// updates from GPS provider
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, locationListener);

			locationProvider = LocationManager.GPS_PROVIDER;

			// Try to get the cached gps location for faster response
			currentKnownLocation = locationManager
					.getLastKnownLocation(locationProvider);
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		if (locationManager != null) {
			// Remove the listener you previously added
			locationManager.removeUpdates(locationListener);
		}
	}

	/**
	 * Determines whether one Location reading is better than the current
	 * Location fix
	 * 
	 * @param location
	 *            The new Location that you want to evaluate
	 * @param currentBestLocation
	 *            The current Location fix, to which you want to compare the new
	 *            one
	 */
	protected boolean isBetterLocation(Location location,
			Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
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
