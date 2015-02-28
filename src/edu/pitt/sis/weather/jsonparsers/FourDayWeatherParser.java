package edu.pitt.sis.weather.jsonparsers;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.Gson;

import edu.pitt.sis.weather.jsonclasses.WeatherJSON;
import edu.pitt.sis.weather.models.FourDayWeatherModel;

public class FourDayWeatherParser {

	private static String urlString = "http://api.openweathermap.org/data/2.5/forecast/daily?";
	private static String urlImage = "http://openweathermap.org/img/w/";
	private boolean isParsingIncomplete;
	private List<FourDayWeatherModel> fourDayWeatherModelList;
	
	public FourDayWeatherParser(){
		this.isParsingIncomplete = true;
		this.fourDayWeatherModelList = new ArrayList<FourDayWeatherModel>();
	}

	public static String getUrlImage() {
		return urlImage;
	}

	public static void setUrlImage(String urlImage) {
		FourDayWeatherParser.urlImage = urlImage;
	}

	public boolean isParsingIncomplete() {
		return isParsingIncomplete;
	}

	public void isParsingIncomplete(boolean isParsingIncomplete) {
		this.isParsingIncomplete = isParsingIncomplete;
	}

	public List<FourDayWeatherModel> getFourDayWeatherModelList() {
		return fourDayWeatherModelList;
	}

	public void setFourDayWeatherModelList(List<FourDayWeatherModel> fourDayWeatherModelList) {
		this.fourDayWeatherModelList = fourDayWeatherModelList;
	}
	
	public void fetchJson(final String location){
		Thread thread = new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					Scanner scanner = new Scanner(new URL(urlString+location+"&units=metric&cnt=5").openStream());
					String outputString = "";
					while(scanner.hasNext()){
						outputString += scanner.nextLine();
					}
					scanner.close();
					readAndParseJson(outputString);
					isParsingIncomplete = false;
				}
				catch (MalformedURLException e){
					Log.e(FourDayWeatherParser.class.toString(),e.getMessage());
				}
				catch(IOException e) {
					Log.e(FourDayWeatherParser.class.toString(),e.getMessage());
				}
				catch(Exception e){
					Log.e(FourDayWeatherParser.class.toString(),e.getMessage());
				}
			}
		});
		thread.start();
	}
	
	public void readAndParseJson(String outputString){
		Gson gson = new Gson();
		try{
			WeatherJSON weatherJson = gson.fromJson(outputString, WeatherJSON.class);
			for(int i=0; i<5; i++){
				FourDayWeatherModel fourDayWeatherModel = new FourDayWeatherModel();
				fourDayWeatherModel.setCity(weatherJson.getCity().getName());
				Long timeInMillis = weatherJson.getList().get(i).getDt() * 1000;
				Date date = new Date(timeInMillis);
				fourDayWeatherModel.setDate(date);
				fourDayWeatherModel.setDescription(weatherJson.getList().get(i).getWeather().get(0).getDescription());
				fourDayWeatherModel.setIcon(weatherJson.getList().get(i).getWeather().get(0).getIcon());
				fourDayWeatherModel.setMaxTemp(weatherJson.getList().get(i).getTemp().getMax());
				fourDayWeatherModel.setMinTemp(weatherJson.getList().get(i).getTemp().getMin());
				fourDayWeatherModel.setIconImage(getIconImage(urlImage+fourDayWeatherModel.getIcon()+".png"));
				fourDayWeatherModelList.add(fourDayWeatherModel);
			}
		}
		catch(Exception e){
			Log.e(FourDayWeatherParser.class.toString(),e.getMessage());
		}
	}
	
	private Bitmap getIconImage(String urlImage){
		try {
			HttpURLConnection con = (HttpURLConnection)(new URL(urlImage)).openConnection();
			con.setRequestMethod("GET");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();
			InputStream inputStream = con.getInputStream();
			return BitmapFactory.decodeStream(inputStream);
		}
		catch (MalformedURLException e){
			Log.e(FourDayWeatherParser.class.toString(),e.getMessage());
		}
		catch(IOException e) {
			Log.e(FourDayWeatherParser.class.toString(),e.getMessage());
		}
		catch(Exception e){
			Log.e(FourDayWeatherParser.class.toString(),e.getMessage());
		}
		return null;
	}
}
