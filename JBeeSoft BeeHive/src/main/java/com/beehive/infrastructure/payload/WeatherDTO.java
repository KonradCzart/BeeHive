package com.beehive.infrastructure.payload;

import com.beehive.domain.weather.WeatherDTODeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = WeatherDTODeserializer.class)
public class WeatherDTO {

	private double rainMililitersPer3h;
	private double minTemp;
	private double maxTemp;
	private double temp;
	private double pressure;
	private int humidity;
	private int cloudsPercentage;
	private double windSpeed;
	private double windDeg;
	private String weatherType;
	
	public WeatherDTO(Builder builder) {
		rainMililitersPer3h = builder.rainMililitersPer3h;
		minTemp = builder.minTemp;
		maxTemp = builder.maxTemp;
		temp = builder.temp;
		pressure = builder.pressure;
		humidity = builder.humidity;
		cloudsPercentage = builder.cloudsPercentage;
		windSpeed = builder.windSpeed;
		windDeg = builder.windDeg;
		weatherType = builder.weatherType;
	}

	public double getRainMililitersPer3h() {
		return rainMililitersPer3h;
	}

	public void setRainMililitersPer3h(double rainMililitersPer3h) {
		this.rainMililitersPer3h = rainMililitersPer3h;
	}

	public double getMinTemp() {
		return minTemp;
	}

	public void setMinTemp(double minTemp) {
		this.minTemp = minTemp;
	}

	public double getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(double maxTemp) {
		this.maxTemp = maxTemp;

	}

	public double getTemp() {
		return temp;
	}
	
	public void setTemp(double temp) {
		this.temp = temp;
	}

	public double getPressure() {
		return pressure;
	}
	
	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
	
	public int getCloudsPercentage() {
		return cloudsPercentage;
	}
	
	public void setCloudsPercentage(int cloudsPercentage) {
		this.cloudsPercentage = cloudsPercentage;
	}

	public double getWindSpeed() {
		return windSpeed;
	}
	
	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}

	public double getWindDeg() {
		return windDeg;
	}

	public void setWindDeg(double windDeg) {
		this.windDeg = windDeg;
	}
	
	public String getWeatherType() {
		return weatherType;
	}

	public void setWeatherType(String weatherType) {
		this.weatherType = weatherType;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private double rainMililitersPer3h;
		private double minTemp;
		private double maxTemp;
		private double temp;
		private double pressure;
		private int humidity;
		private int cloudsPercentage;
		private double windSpeed;
		private double windDeg;
		private String weatherType;

		public Builder withRainMililitersPer3h(double rainMililitersPer3h) {
			this.rainMililitersPer3h = rainMililitersPer3h;
			return this;
		}

		public Builder withMinTemp(double minTemp) {
			this.minTemp = minTemp;
			return this;
		}

		public Builder withMaxTemp(double maxTemp) {
			this.maxTemp = maxTemp;
			return this;
		}

		public Builder withTemp(double temp) {
			this.temp = temp;
			return this;
		}

		public Builder withPressure(double pressure) {
			this.pressure = pressure;
			return this;
		}
		
		public Builder withHumidity(int humidity) {
			this.humidity = humidity;
			return this;
		}

		public Builder withCloudPercentage(int cloudPercentage) {
			this.cloudsPercentage = cloudPercentage;
			return this;
		}

		public Builder withWindSpeed(double windSpeed) {
			this.windSpeed = windSpeed;
			return this;
		}

		public Builder withWindDeg(double windDeg) {
			this.windDeg = windDeg;
			return this;
		}

		public Builder withWeatherType(String weatherType) {
			this.weatherType = weatherType;
			return this;
		}

		public WeatherDTO build() {
			return new WeatherDTO(this);
		}
	}

}
