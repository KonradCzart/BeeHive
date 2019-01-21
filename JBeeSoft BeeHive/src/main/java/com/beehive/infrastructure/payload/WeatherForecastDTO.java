package com.beehive.infrastructure.payload;

public class WeatherForecastDTO {
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

    public WeatherForecastDTO(double rainMililitersPer3h, double minTemp, double maxTemp, double temp,
                              double pressure, int  humidity, int cloudsPercentage, double windSpeed, double windDeg, String weatherType) {
        this.rainMililitersPer3h = rainMililitersPer3h;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.cloudsPercentage = cloudsPercentage;
        this.windSpeed = windSpeed;
        this.windDeg = windDeg;
        this.weatherType = weatherType;
    }

    public double getRainMililitersPer3h() { return rainMililitersPer3h; }
    public double getMinTemp() { return minTemp; }
    public double getMaxTemp() { return maxTemp; }
    public double getTemp() { return temp; }
    public double getPressure() { return pressure; }
    public int getHumidity() { return humidity; }
    public int getCloudsPercentage() { return cloudsPercentage; }
    public double getWindSpeed() { return windSpeed; }
    public double getWindDeg() { return windDeg; }
    public String getWeatherType() { return weatherType; }

    public void setRainMililitersPer3h(double rainMililitersPer3h) { this.rainMililitersPer3h = rainMililitersPer3h; }
    public void setMinTemp(double minTemp) { this.minTemp = minTemp; }
    public void setMaxTemp(double maxTemp) { this.maxTemp = maxTemp; }
    public void setTemp(double temp) { this.temp = temp; }
    public void setPressure(double pressure) { this.pressure = pressure; }
    public void setHumidity(int humidity) { this.humidity = humidity; }
    public void setCloudsPercentage(int cloudsPercentage) { this.cloudsPercentage = cloudsPercentage; }
    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }
    public void setWindDeg(double windDeg) { this.windDeg = windDeg; }
    public void setWeatherType(String weatherType) { this.weatherType = weatherType; }
}

