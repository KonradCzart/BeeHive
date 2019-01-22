package com.beehive.domain.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LocationService {
	
	@Autowired
	private LocationRepository locationRepository;
	
	
	public Location createLocation(String country, String city) {
		Location location = new Location(country, city);	
		return locationRepository.save(location);
	}
	
	public Location getOrCreateLocationIfNotExist(String country, String city) {
		return locationRepository.findByCountryAndCity(country, city)
				.orElseGet(() -> createLocation(country, city));
	}
	
}
