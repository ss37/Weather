package edu.pitt.sis.weather.jsonparsers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import android.util.Log;

import com.google.gson.Gson;

import edu.pitt.sis.weather.jsonclasses.CurrentWeatherJSON;
import edu.pitt.sis.weather.models.CurrentWeatherModel;

public class CurrentWeatherParser {

	private static String urlString = "http://api.openweathermap.org/data/2.5/weather?";
	private boolean isParsingIncomplete;
	private CurrentWeatherModel currentWeatherModel;
	
	public CurrentWeatherParser(){
		isParsingIncomplete = true;
		currentWeatherModel = new CurrentWeatherModel();
	}

	public boolean isParsingIncomplete() {
		return isParsingIncomplete;
	}

	public void setParsingIncomplete(boolean isParsingIncomplete) {
		this.isParsingIncomplete = isParsingIncomplete;
	}

	public CurrentWeatherModel getCurrentWeatherModel() {
		return currentWeatherModel;
	}

	public void setCurrentWeatherModel(CurrentWeatherModel currentWeatherModel) {
		this.currentWeatherModel = currentWeatherModel;
	}
	
	public void fetchJson(final String location){
		Thread thread = new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					Scanner scanner = new Scanner(new URL(urlString+location+"&units=metric").openStream());
					String outputString = "";
					while(scanner.hasNext()){
						outputString += scanner.nextLine();
					}
					scanner.close();
					readAndParseJson(outputString);
					isParsingIncomplete = false;
				}
				catch (MalformedURLException e){
					Log.e(CurrentWeatherParser.class.toString(),e.getMessage());
				}
				catch(IOException e) {
					Log.e(CurrentWeatherParser.class.toString(),e.getMessage());
				}
				catch(Exception e){
					Log.e(CurrentWeatherParser.class.toString(),e.getMessage());
				}
			}
		});
		thread.start();
	}
	
	public void readAndParseJson(String outputString){
		Gson gson = new Gson();
		try{
			CurrentWeatherJSON currentWeatherJson = gson.fromJson(outputString, CurrentWeatherJSON.class);
			currentWeatherModel.setId(currentWeatherJson.getId());
			currentWeatherModel.setName(currentWeatherJson.getName());
			currentWeatherModel.setTemp(currentWeatherJson.getMain().getTemp());
		}
		catch(Exception e){
			Log.e(CurrentWeatherParser.class.toString(),e.getMessage());
		}
	}
}
