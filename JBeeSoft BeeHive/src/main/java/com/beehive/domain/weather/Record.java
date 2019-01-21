package com.beehive.domain.weather;

public class Record {
    public double rainMililitersPer3h;

    public double minTemp;
    public double maxTemp;
    public double temp;
    public double pressure;
    public int humidity;

    public int cloudsPercentage;

    public double windSpeed;
    public double windDeg;

    public String weatherType;

    public Record(double rainMililitersPer3h, double minTemp, double maxTemp, double temp,
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
}

