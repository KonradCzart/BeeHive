package com.beehive.domain.weather;

import com.beehive.infrastructure.payload.WeatherDTO;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.ObjectInput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {
	
	private static final String WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String APP_ID = "e68fc9cac657fcc8ebc1711aa6c8957d";
    private RestTemplate restTemplate = new RestTemplate();
        
    public WeatherDTO getWeatherForCity(String city) throws IOException, JSONException {
    	UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(WEATHER_API_URL)
    			.queryParam("q", city)
    			.queryParam("units", "metric")
    			.queryParam("type", "accurate")
    			.queryParam("appid", APP_ID);
    			
        return restTemplate.getForObject(uriBuilder.toUriString(), WeatherDTO.class);
    }
    
}