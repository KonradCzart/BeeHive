package com.beehive.domain.apiary;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beehive.domain.hive.HiveService;
import com.beehive.domain.location.Location;
import com.beehive.domain.location.LocationService;
import com.beehive.domain.privileges.Privilege;
import com.beehive.domain.privileges.PrivilegeProfile;
import com.beehive.domain.privileges.PrivilegeService;
import com.beehive.domain.user.User;
import com.beehive.infrastructure.payload.ApiaryDTO;
import com.beehive.infrastructure.payload.ApiaryINFO;
import com.beehive.infrastructure.payload.ApiaryRequest;
import com.beehive.infrastructure.payload.AssociatedApiariesResponse;
import com.beehive.infrastructure.payload.HiveDTO;

@Service
public class ApiaryService {
	
	@Autowired
	private ApiaryRepository apiaryRepository;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private HiveService hiveService;
	
	@Autowired
	private PrivilegeService privilegeService;
	
	private static final String NO_SUCH_APIARY = "There is no apiary with id {0}";
	
	
	public Apiary createApiary(ApiaryRequest apiaryRequest, User owner) {		
		Location apiaryLocation = locationService.getOrCreateLocationIfNotExist(apiaryRequest.getCountry(), apiaryRequest.getCity());
		Apiary apiary = new Apiary(apiaryRequest.getName(), apiaryLocation);		
		apiaryRepository.save(apiary);		
		privilegeService.grantPrivileges(owner, apiary, Privilege.getAllAvailablePrivileges());	
		return apiary;
	}
	
	public AssociatedApiariesResponse getAssociatedApiaries(User user) {
		Map<Boolean, List<ApiaryINFO>> ownedAndOnlyAssociatedApriariesMap = user.getAllPrivilegeProfiles()
				.stream()
				.collect(Collectors.groupingBy(PrivilegeProfile::isApiaryOwner, Collectors.mapping(this::getApiaryFromProfile,
						Collectors.mapping(this::mapToApiaryINFO, Collectors.toList()))));

		return new AssociatedApiariesResponse(ownedAndOnlyAssociatedApriariesMap.get(true), ownedAndOnlyAssociatedApriariesMap.get(false));
	}
	
	private Apiary getApiaryFromProfile(PrivilegeProfile profile) {
		return profile.getAffectedApiary();
	}
	
	public Apiary getApiaryFromDatabase(Long apiaryId) {
		return apiaryRepository.findById(apiaryId)
				.orElseThrow(() -> new IllegalArgumentException(MessageFormat.format(NO_SUCH_APIARY, apiaryId)));
	}
	
	public Apiary modifyApiary(Apiary apiary) {
		return apiaryRepository.save(apiary);
	}
	
	public ApiaryINFO mapToApiaryINFO(Apiary apiary) {		
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
	
	public ApiaryDTO mapToApiaryDTO(Apiary apiary) {
		ApiaryINFO apiaryINFO = mapToApiaryINFO(apiary);
		List<HiveDTO> hives = apiary.getHives()
				.stream()
				.map(hive -> hiveService.mapHiveToHiveDTO(hive))
				.collect(Collectors.toList());
		
		return new ApiaryDTO(apiaryINFO, hives);
	}
	
}
