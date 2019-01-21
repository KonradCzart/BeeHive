package com.beehive.domain.weather;

import com.beehive.infrastructure.payload.ValueResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public List<Record> getForecast(String city) throws IOException {
        String REST_URI = String.format("%s%s%s%s%s",url, "forecast?q=", city, "&units=imperial&type=accurate&", appid);

        String json = restTemplate.getForObject(REST_URI, String.class);
        return DeserializedForecast(json);
    }

    private List<Record> DeserializedForecast(String json) throws IOException {

        Map<String, Object> map = objectMapper.readValue(json, new TypeReference<Map<String,Object>>(){});
        List<Record> recordList = new ArrayList();
        return recordList;
    }
}