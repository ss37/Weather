package edu.pitt.sis.weather.models;

import java.util.Date;

import android.graphics.Bitmap;

public class FourDayWeatherModel {

	private String city;
	private Double minTemp;
	private Double maxTemp;
	private String description;
	private Date date;
	private String icon;
	private Bitmap iconImage;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Double getMinTemp() {
		return minTemp;
	}
	public void setMinTemp(Double minTemp) {
		this.minTemp = minTemp;
	}
	public Double getMaxTemp() {
		return maxTemp;
	}
	public void setMaxTemp(Double maxTemp) {
		this.maxTemp = maxTemp;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Bitmap getIconImage() {
		return iconImage;
	}
	public void setIconImage(Bitmap iconImage) {
		this.iconImage = iconImage;
	}
}
