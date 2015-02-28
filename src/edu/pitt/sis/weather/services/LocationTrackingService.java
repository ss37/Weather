package edu.pitt.sis.weather.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class LocationTrackingService extends Service implements LocationListener{

	private final Context mContext;
	
	private boolean isNetworkEnabled;
	private boolean canGetLocation;
	
	private Location location;
	private double latitude;
	private double longitude;
	
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
	
	protected LocationManager locationManager;
	
	public LocationTrackingService(Context context){
		this.mContext = context;
		getLocation();
	}
	
	public void getLocation(){
		try{
			locationManager = (LocationManager)mContext.getSystemService(LOCATION_SERVICE);
			isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			
			if(isNetworkEnabled){
				this.canGetLocation = true;
				locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
				if(locationManager != null){
					location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					if(location!=null){
						latitude = location.getLatitude();
						longitude = location.getLongitude();
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void onLocationChanged(Location location) {
		if(location!=null){
			latitude = location.getLatitude();
			longitude = location.getLongitude();
		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onProviderEnabled(String provider) {
		getLocation();
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(getApplicationContext(), "No network available.\nCheck network settings.", Toast.LENGTH_LONG).show();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public boolean isCanGetLocation() {
		return canGetLocation;
	}

	public double getLatitude() {
		if(location!=null)
			latitude = location.getLatitude();
		return latitude;
	}

	public double getLongitude() {
		if(location!=null)
			longitude = location.getLongitude();
		return longitude;
	}

}
