package com.beehive.domain.weather;

import com.beehive.infrastructure.payload.WeatherForecastDTO;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    private String url = "http://api.openweathermap.org/data/2.5/";
    private String appid = "appid=e68fc9cac657fcc8ebc1711aa6c8957d";
    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();

    public List<WeatherForecastDTO> getForecast(String city) throws IOException, JSONException {
        String REST_URI = String.format("%s%s%s%s%s",url, "forecast?q=", city, "&units=imperial&type=accurate&", appid);

        String json = restTemplate.getForObject(REST_URI, String.class);
        JSONObject obj = new JSONObject(json);

        return DeserializedForecast(obj);
    }

    private List<WeatherForecastDTO> DeserializedForecast(JSONObject obj) throws IOException, JSONException {

            JSONArray arr = (JSONArray) obj.get("list");
            List<WeatherForecastDTO> recordList = new ArrayList<>();

            JSONObject jo;
            JSONObject temp;
            JSONArray temp2;
            for(int i = 0; i<1; i++)
            {
                WeatherForecastDTO r = new WeatherForecastDTO();
                jo = arr.optJSONObject(i);

                temp = (JSONObject) jo.get("rain");
                try{
                    r.setRainMililitersPer3h((double) temp.get("3h"));
                }catch (Exception e){
                    r.setRainMililitersPer3h((double) 0);
                }

                temp = (JSONObject) jo.get("main");
                r.setMinTemp((double) temp.get("temp_min"));
                r.setMaxTemp((double) temp.get("temp_max"));
                r.setTemp((double) temp.get("temp"));
                r.setPressure((double) temp.get("pressure"));
                r.setHumidity((int) temp.get("humidity"));

                temp = (JSONObject) jo.get("clouds");
                r.setCloudsPercentage((int) temp.get("all"));

                temp = (JSONObject) jo.get("wind");
                r.setWindDeg(Double.valueOf(String.valueOf(temp.get("deg"))));
                r.setWindSpeed((double) temp.get("speed"));

                temp2 = (JSONArray) jo.get("weather");
                temp = temp2.optJSONObject(0);
                r.setWeatherType((String) temp.get("main"));

                recordList.add(r);
            }

        return recordList;
    }
}