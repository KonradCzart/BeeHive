package com.beehive.domain.apiary;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beehive.domain.location.Location;
import com.beehive.domain.location.LocationRepository;
import com.beehive.domain.location.LocationService;
import com.beehive.domain.user.User;
import com.beehive.domain.user.UserRepository;
import com.beehive.infrastructure.payload.ApiaryRequest;

@Service
public class ApiaryService {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private ApiaryRepository apiaryRepository;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private LocationRepository locationRepository;
	
	
	public Apiary createApiary(ApiaryRequest apiaryRequest) throws NoSuchElementException {
		
		Optional<User> owner = userRepository.findById(apiaryRequest.getOwner_id());
		User apiaryOwner = owner.orElseThrow();
		
		Optional<Location> location = locationRepository.findByCountryAndCity(apiaryRequest.getCountry(), apiaryRequest.getCity());
		Location apiaryLocation = location.orElseGet(() -> locationService.createLocation(apiaryRequest.getCountry(), apiaryRequest.getCity()));
		
		Apiary apiary = new Apiary(apiaryRequest.getName(), apiaryOwner, apiaryLocation);
		
		return apiaryRepository.save(apiary);
	}
	
}
