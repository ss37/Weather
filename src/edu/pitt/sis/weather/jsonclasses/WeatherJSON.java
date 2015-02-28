package edu.pitt.sis.weather.jsonclasses;

import java.util.List;

public class WeatherJSON {

	private City city;
	private List<ListForecast> list; //need
	
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public List<ListForecast> getList() {
		return list;
	}
	public void setList(List<ListForecast> list) {
		this.list = list;
	}
}
