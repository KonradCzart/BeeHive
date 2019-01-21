package com.beehive.domain.weather;

import com.beehive.domain.apiary.Apiary;
import com.beehive.domain.apiary.ApiaryService;
import com.beehive.infrastructure.payload.WeatherForecastDTO;
import com.beehive.infrastructure.security.CurrentUser;
import com.beehive.infrastructure.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
    public List<WeatherForecastDTO> getWeatherForecast(@CurrentUser UserPrincipal currentUser, @PathVariable Long apiaryId) throws IOException{
        Apiary apiary = apiaryService.getApiaryFromDatabase(apiaryId);
        return weatherService.getForecast(apiary.getLocation().getCity());
    }
}
