package com.beehive.domain.apiary;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beehive.domain.hive.HiveService;
import com.beehive.domain.location.Location;
import com.beehive.domain.location.LocationRepository;
import com.beehive.domain.location.LocationService;
import com.beehive.domain.privileges.Privilege;
import com.beehive.domain.privileges.PrivilegeProfile;
import com.beehive.domain.privileges.PrivilegeService;
import com.beehive.domain.user.User;
import com.beehive.domain.user.UserRepository;
import com.beehive.infrastructure.payload.ApiaryDTO;
import com.beehive.infrastructure.payload.ApiaryINFO;
import com.beehive.infrastructure.payload.ApiaryRequest;
import com.beehive.infrastructure.payload.AssociatedApiariesResponse;
import com.beehive.infrastructure.payload.HiveDTO;
import com.beehive.infrastructure.security.UserPrincipal;

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
	
	@Autowired
	private HiveService hiveService;
	
	@Autowired
	private PrivilegeService privilegeService;
	
	
	public Apiary createApiary(ApiaryRequest apiaryRequest) throws NoSuchElementException {
		
		Optional<Location> location = locationRepository.findByCountryAndCity(apiaryRequest.getCountry(), apiaryRequest.getCity());
		Location apiaryLocation = location.orElseGet(() -> locationService.createLocation(apiaryRequest.getCountry(), apiaryRequest.getCity()));
		
		Apiary apiary = new Apiary(apiaryRequest.getName(), apiaryLocation);
		
		apiaryRepository.save(apiary);
		
		User owner = userRepository.findById(apiaryRequest.getOwner_id()).orElseThrow();
		
		privilegeService.grantPrivileges(owner, apiary, Privilege.getAllAvailablePrivileges());
		
		return apiary;
		
	}
	
	public ApiaryDTO getApiaryById(Long id)  throws NoSuchElementException{
		
		Apiary apiary = apiaryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Apiary id is not correct"));
		
		ApiaryINFO apiaryINFO = mapApiatyToApiaryINFO(apiary);
		
		List<HiveDTO> hives = apiary.getHives().stream()
				.map(hive -> hiveService.mapHiveToHiveDTO(hive) )
				.collect(Collectors.toList());
		
		return new ApiaryDTO(apiaryINFO, hives);
	}
	
	public AssociatedApiariesResponse getAssociatedApiaries(User user) {
		Map<Boolean, List<ApiaryINFO>> ownedAndOnlyAssociatedApriariesMap = user.getAllPrivilegeProfiles()
				.stream()
				.collect(Collectors.groupingBy(PrivilegeProfile::isApiaryOwner, Collectors.mapping(this::getApiary,
						Collectors.mapping(this::mapApiatyToApiaryINFO, Collectors.toList()))));

		return new AssociatedApiariesResponse(ownedAndOnlyAssociatedApriariesMap.get(true), ownedAndOnlyAssociatedApriariesMap.get(false));
	}
	
	private Apiary getApiary(PrivilegeProfile profile) {
		return profile.getAffectedApiary();
	}
	
	public ApiaryINFO mapApiatyToApiaryINFO(Apiary apiary) {
		
		long hiveNumber = apiary.getHives()
				.stream()
				.count();
		
		return ApiaryINFO.builder()
				.withId(apiary.getId())
				.withName(apiary.getName())
				.withLocation(apiary.getLocation())
				.withHiveNumber(hiveNumber)
				.build();
	}
	
}
