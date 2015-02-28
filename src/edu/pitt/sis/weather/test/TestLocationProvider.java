package edu.pitt.sis.weather.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

public class TestLocationProvider {

	private String provider;
	private Context mContext;
	
	public TestLocationProvider(String provider, Context mContext){
		this.provider = provider;
		this.mContext = mContext;
		
		LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		locationManager.addTestProvider(provider, false, false, false, false, false, true, true, 0, 5);
		locationManager.setTestProviderEnabled(provider, true);
	}
	
	public void setLocation(double latitude, double longitude){
		try{
		LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
		Location testLocation = new Location(provider);
		testLocation.setLatitude(latitude);
		testLocation.setLongitude(longitude);
		testLocation.setAltitude(0);
		testLocation.setTime(System.currentTimeMillis());
		testLocation.setAccuracy(0);
		
		Method locationJellyBeanFixMethod = Location.class.getMethod("makeComplete");
		if (locationJellyBeanFixMethod != null) {
		   locationJellyBeanFixMethod.invoke(testLocation);
		}
		locationManager.setTestProviderLocation(provider, testLocation);
		}
		catch(NoSuchMethodException e){
			Log.e(TestLocationProvider.class.toString(), e.getMessage());
		} catch (IllegalAccessException e) {
			Log.e(TestLocationProvider.class.toString(), e.getMessage());
		} catch (IllegalArgumentException e) {
			Log.e(TestLocationProvider.class.toString(), e.getMessage());
		} catch (InvocationTargetException e) {
			Log.e(TestLocationProvider.class.toString(), e.getMessage());
		}
	}
	
	public void shutdown(){
		LocationManager locationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
		locationManager.removeTestProvider(provider);
	}
}
