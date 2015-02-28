package edu.pitt.sis.weather.jsonclasses;

import java.util.List;

public class ListForecast {

	private Long dt; //need
	private Temp temp; //need
	private List<Weather> weather; //need
	
	public Long getDt() {
		return dt;
	}
	public void setDt(Long dt) {
		this.dt = dt;
	}
	public Temp getTemp() {
		return temp;
	}
	public void setTemp(Temp temp) {
		this.temp = temp;
	}
	public List<Weather> getWeather() {
		return weather;
	}
	public void setWeather(List<Weather> weather) {
		this.weather = weather;
	}
}
