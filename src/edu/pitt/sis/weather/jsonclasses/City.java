package edu.pitt.sis.weather.jsonclasses;

public class City {

	private String name;
	private Coord coord;	//check if you need later
	private String country;	//check if you need later
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Coord getCoord() {
		return coord;
	}
	public void setCoord(Coord coord) {
		this.coord = coord;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
