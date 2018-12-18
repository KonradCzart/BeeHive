package com.beehive.domain.weather;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

public class WeatherHelper {

    private  String url = "http://api.openweathermap.org/data/2.5/";
    private  String appid = "appid=e68fc9cac657fcc8ebc1711aa6c8957d";

    public List<Record> Forcast(String city){
        String REST_URI = String.format("%s%s%s%s%s",url, "forecast?q=", city, "&units=imperial&type=accurate&", appid);

        String jsonS = ClientBuilder.newClient().target(REST_URI).request().accept(MediaType.APPLICATION_JSON).get(String.class);
        JSONObject obj = new JSONObject(jsonS);
        return  DeserializedForcast(obj);
    }

    private List<Record> DeserializedForcast(JSONObject obj){
        JSONArray arr = (JSONArray) obj.get("list");
        List<Record> recordList = new ArrayList<>();

        JSONObject jo;
        JSONObject temp;
        JSONArray temp2;
        for(int i = 0; i<40; i++)
        {
            Record r = new Record();
            jo = arr.optJSONObject(i);

            temp = (JSONObject) jo.get("rain");
            try{
                r.rainMililitersPer3h = (double) temp.get("3h");
            }catch (Exception e){
                r.rainMililitersPer3h = 0;
            }

            temp = (JSONObject) jo.get("main");
            r.minTemp = (double) temp.get("temp_min");
            r.maxTemp = (double) temp.get("temp_max");
            r.temp = (double) temp.get("temp");
            r.pressure = (double) temp.get("pressure");
            r.humidity = (int) temp.get("humidity");

            temp = (JSONObject) jo.get("clouds");
            r.cloudsPercentage = (int) temp.get("all");

            temp = (JSONObject) jo.get("wind");
            r.windDeg = Double.valueOf(String.valueOf(temp.get("deg")));
            r.windSpeed = (double) temp.get("speed");

            temp2 = (JSONArray) jo.get("weather");
            temp = temp2.optJSONObject(0);
            r.weatherType = (String) temp.get("main");

            recordList.add(r);
        }
    }

    public static void main(String[] args){
        WeatherHelper w = new WeatherHelper();
        List<Record> recordList = w.Forcast("London");
    }
}