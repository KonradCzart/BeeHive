package com.beehive.domain.weather;

import com.beehive.domain.apiary.Apiary;
import com.beehive.domain.apiary.ApiaryService;
import com.beehive.infrastructure.payload.WeatherDTO;
import com.beehive.infrastructure.security.CurrentUser;
import com.beehive.infrastructure.security.UserPrincipal;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/weather")
public class WeatherController
{
    @Autowired
    WeatherService weatherService;

    @Autowired
    ApiaryService apiaryService;

    @GetMapping("/{apiaryId}")
    @PreAuthorize("hasRole('USER')")
    public WeatherDTO getWeatherForecast(@CurrentUser UserPrincipal currentUser, @PathVariable Long apiaryId) throws IOException, JSONException {
        Apiary apiary = apiaryService.getApiaryFromDatabase(apiaryId);
        return weatherService.getWeatherForCity(apiary.getLocation().getCity());
    }
    
}
