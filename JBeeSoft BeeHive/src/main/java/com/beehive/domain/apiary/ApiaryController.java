package com.beehive.domain.apiary;

import java.net.URI;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.beehive.domain.location.Location;
import com.beehive.domain.location.LocationService;
import com.beehive.domain.privileges.PrivilegeService;
import com.beehive.domain.user.User;
import com.beehive.domain.user.UserService;
import com.beehive.infrastructure.payload.ApiResponse;
import com.beehive.infrastructure.payload.ApiaryDTO;
import com.beehive.infrastructure.payload.ApiaryRequest;
import com.beehive.infrastructure.payload.AssociatedApiariesResponse;
import com.beehive.infrastructure.security.CurrentUser;
import com.beehive.infrastructure.security.UserPrincipal;


@RestController
@RequestMapping("/api/apiary")
public class ApiaryController {
	
	
	@Autowired
    ApiaryService apiaryService;
	
    @Autowired
    UserService userService;
    
    @Autowired
    PrivilegeService privilegeService;
    
    @Autowired
    LocationService locationService;
    

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public AssociatedApiariesResponse getMeApiary(@CurrentUser UserPrincipal currentUser) {
    	User user = userService.getUserFormDatabase(currentUser.getId());   	
        return apiaryService.getAssociatedApiaries(user);
    }
    
    @GetMapping("/{apiaryId}")
    @PreAuthorize("hasRole('USER')")
    public ApiaryDTO getApiaryById(@CurrentUser UserPrincipal currentUser, @PathVariable Long apiaryId) {
    	Apiary apiary = apiaryService.getApiaryFromDatabase(apiaryId);
    	return apiaryService.mapToApiaryDTO(apiary);
    }
    
    @PostMapping("/new")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createApiary(@Valid @RequestBody ApiaryRequest apiaryRequest, @CurrentUser UserPrincipal currentUser) {

    	Apiary apiary;
    	try {
    		User user = userService.getUserFormDatabase(currentUser.getId());  		
    		apiary = apiaryService.createApiary(apiaryRequest, user);
    	}
        catch (IllegalArgumentException e) {
        	return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{apiaryId}")
                .buildAndExpand(apiary.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Apiary Created Successfully"));
    }
    
    @PutMapping("/modify/{apiaryId}")
	@PreAuthorize("hasRole('USER')")
	public ApiaryDTO modifyApiary(@Valid @RequestBody ApiaryRequest apiaryRequest, @CurrentUser UserPrincipal currentUser, @PathVariable Long apiaryId) {
		Apiary apiary = apiaryService.getApiaryFromDatabase(apiaryId);
		Location apiaryLocation = locationService.getOrCreateLocationIfNotExist(apiaryRequest.getCountry(), apiaryRequest.getCity());
		apiary.setName(apiaryRequest.getName());
		apiary.setLocation(apiaryLocation);
		apiary = apiaryService.modifyApiary(apiary);
		
		return apiaryService.mapToApiaryDTO(apiary);
	}

}
