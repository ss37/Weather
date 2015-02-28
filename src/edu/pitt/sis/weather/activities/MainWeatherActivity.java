package edu.pitt.sis.weather.activities;

import java.util.List;

import edu.pitt.sis.weather.jsonparsers.CurrentWeatherParser;
import edu.pitt.sis.weather.jsonparsers.FourDayWeatherParser;
import edu.pitt.sis.weather.models.CurrentWeatherModel;
import edu.pitt.sis.weather.models.FourDayWeatherModel;
import edu.pitt.sis.weather.services.LocationTrackingService;
import edu.pitt.sis.weather.test.TestLocationProvider;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainWeatherActivity extends ActionBarActivity implements LocationListener {

	private boolean isGetByCity;
	private String location;
	
	private TestLocationProvider testLocationProvider;
	private LocationTrackingService locationTrackingService;
	
	private CurrentWeatherParser currentWeatherParser;
	private CurrentWeatherModel currentWeatherModel;
	private FourDayWeatherParser fourDayWeatherParser;
	private List<FourDayWeatherModel> fourDayWeatherModelList;
	
	private ImageView weatherConditionImage;
	private TextView weatherConditionText;
	
	private String cityName;
	private Double latitude;
	private Double longitude;
	
	private static String websiteUrl = "http://openweathermap.org/city/";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_weather);
		
		isGetByCity = false;
				
		testLocationProvider = new TestLocationProvider(LocationManager.NETWORK_PROVIDER, this);
		testLocationProvider.setLocation(40.44, -80.00);
		
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, this);
		
		getLocation();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_weather, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch(id){
		case R.id.action_city:
			showCityListDialog();
			return true;
		case R.id.action_refresh:
			showWeather();
			return true;
		case R.id.action_updateLocation:
			getLocation();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void getLocation(){
		isGetByCity = false;
		locationTrackingService = new LocationTrackingService(MainWeatherActivity.this);
		if(locationTrackingService.isCanGetLocation()){
			latitude = locationTrackingService.getLatitude();
			longitude = locationTrackingService.getLongitude();
			showWeather();
		}
		else{
			Toast.makeText(getApplicationContext(), "Cannot get Location.", Toast.LENGTH_LONG).show();
		}
	}
	
	public void showWeather(){
		if(isGetByCity){
			location = "q="+cityName;
		}
		else{
			location = "lat="+Double.toString(40.44)+"&lon="+Double.toString(-80.00);
		}
		
		currentWeatherParser = new CurrentWeatherParser();
		currentWeatherParser.fetchJson(location);
		fourDayWeatherParser = new FourDayWeatherParser();
		fourDayWeatherParser.fetchJson(location);
		while(currentWeatherParser.isParsingIncomplete() || fourDayWeatherParser.isParsingIncomplete());
		currentWeatherModel = currentWeatherParser.getCurrentWeatherModel();
		fourDayWeatherModelList = fourDayWeatherParser.getFourDayWeatherModelList();
		
		if(currentWeatherModel!=null && fourDayWeatherModelList!=null){
			Log.d(MainWeatherActivity.class.toString(),getSupportActionBar().toString());
			this.getSupportActionBar().setDisplayShowTitleEnabled(true);
			this.getSupportActionBar().setTitle(currentWeatherModel.getName());
			weatherConditionImage = (ImageView)findViewById(R.id.todayWeatherConditionImage);
			weatherConditionImage.setImageBitmap(fourDayWeatherModelList.get(0).getIconImage());
			weatherConditionImage = (ImageView)findViewById(R.id.day1WeatherConditionImage);
			weatherConditionImage.setImageBitmap(fourDayWeatherModelList.get(1).getIconImage());
			weatherConditionImage = (ImageView)findViewById(R.id.day2WeatherConditionImage);
			weatherConditionImage.setImageBitmap(fourDayWeatherModelList.get(2).getIconImage());
			weatherConditionImage = (ImageView)findViewById(R.id.day3WeatherConditionImage);
			weatherConditionImage.setImageBitmap(fourDayWeatherModelList.get(3).getIconImage());
			weatherConditionImage = (ImageView)findViewById(R.id.day4WeatherConditionImage);
			weatherConditionImage.setImageBitmap(fourDayWeatherModelList.get(4).getIconImage());
			
			weatherConditionText = (TextView)findViewById(R.id.currentTemperatureText);
			weatherConditionText.setText("Current: "+currentWeatherModel.getTemp().toString()+(char)0x00B0 +"C");
			weatherConditionText = (TextView)findViewById(R.id.currentMaxTemperatureText);
			weatherConditionText.setText("Max: "+fourDayWeatherModelList.get(0).getMaxTemp().toString()+(char)0x00B0 +"C");
			weatherConditionText = (TextView)findViewById(R.id.currentMinTemperatureText);
			weatherConditionText.setText("Min: "+fourDayWeatherModelList.get(0).getMinTemp().toString()+(char)0x00B0 +"C");
			weatherConditionText = (TextView)findViewById(R.id.currentDescriptionText);
			weatherConditionText.setText(fourDayWeatherModelList.get(0).getDescription());
			
			weatherConditionText = (TextView)findViewById(R.id.day1Text);
			weatherConditionText.setText(fourDayWeatherModelList.get(1).getDate().toString().substring(0, 3));
			weatherConditionText = (TextView)findViewById(R.id.day2Text);
			weatherConditionText.setText(fourDayWeatherModelList.get(2).getDate().toString().substring(0, 3));
			weatherConditionText = (TextView)findViewById(R.id.day3Text);
			weatherConditionText.setText(fourDayWeatherModelList.get(3).getDate().toString().substring(0, 3));
			weatherConditionText = (TextView)findViewById(R.id.day4Text);
			weatherConditionText.setText(fourDayWeatherModelList.get(4).getDate().toString().substring(0, 3));
			
			weatherConditionText = (TextView)findViewById(R.id.date1Text);
			weatherConditionText.setText(fourDayWeatherModelList.get(1).getDate().toString().substring(4, 10));
			weatherConditionText = (TextView)findViewById(R.id.date2Text);
			weatherConditionText.setText(fourDayWeatherModelList.get(2).getDate().toString().substring(4, 10));
			weatherConditionText = (TextView)findViewById(R.id.date3Text);
			weatherConditionText.setText(fourDayWeatherModelList.get(3).getDate().toString().substring(4, 10));
			weatherConditionText = (TextView)findViewById(R.id.date4Text);
			weatherConditionText.setText(fourDayWeatherModelList.get(4).getDate().toString().substring(4, 10));
			
			weatherConditionText = (TextView)findViewById(R.id.temp1Text);
			weatherConditionText.setText("Max: "+fourDayWeatherModelList.get(1).getMaxTemp().toString()+(char)0x00B0 +"C\nMin: "+fourDayWeatherModelList.get(0).getMinTemp().toString()+(char)0x00B0 +"C");
			weatherConditionText = (TextView)findViewById(R.id.temp2Text);
			weatherConditionText.setText("Max: "+fourDayWeatherModelList.get(2).getMaxTemp().toString()+(char)0x00B0 +"C\nMin: "+fourDayWeatherModelList.get(0).getMinTemp().toString()+(char)0x00B0 +"C");
			weatherConditionText = (TextView)findViewById(R.id.temp3Text);
			weatherConditionText.setText("Max: "+fourDayWeatherModelList.get(3).getMaxTemp().toString()+(char)0x00B0 +"C\nMin: "+fourDayWeatherModelList.get(0).getMinTemp().toString()+(char)0x00B0 +"C");
			weatherConditionText = (TextView)findViewById(R.id.temp4Text);
			weatherConditionText.setText("Max: "+fourDayWeatherModelList.get(4).getMaxTemp().toString()+(char)0x00B0 +"C\nMin: "+fourDayWeatherModelList.get(0).getMinTemp().toString()+(char)0x00B0 +"C");
		}
	}

	public AlertDialog showCityListDialog() {
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle(R.string.action_city)
	           .setItems(R.array.cities_array, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) {
	            	   switch(which){
	            	   case 0:
	            		   cityName = "London";
	            		   break;
	            	   case 1:
	            		   cityName = "Mumbai";
	            		   break;
	            	   case 2:
	            		   cityName = "Pittsburgh";
	            		   break;
	            	   }
	            	   isGetByCity = true;
	            	   showWeather();
	           }
	    });
	    return builder.show();
	}
	
	public void openWebsite(View view){
		final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(websiteUrl+currentWeatherModel.getId()));
		startActivity(intent);
	}
	
	@Override
	public void onLocationChanged(Location location) {
		if(location!=null){
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			showWeather();
		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		if(!isGetByCity)
			getLocation();
		else
			showWeather();
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(getApplicationContext(), "No network available.\nCheck your network settings.", Toast.LENGTH_LONG).show();
	}
}
