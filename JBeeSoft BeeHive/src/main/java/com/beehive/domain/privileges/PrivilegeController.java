package com.beehive.domain.privileges;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beehive.domain.apiary.Apiary;
import com.beehive.domain.apiary.ApiaryService;
import com.beehive.domain.user.User;
import com.beehive.domain.user.UserService;
import com.beehive.infrastructure.payload.ApiResponse;
import com.beehive.infrastructure.payload.ContributorDTO;
import com.beehive.infrastructure.payload.GrantPrivilegesRequest;
import com.beehive.infrastructure.security.CurrentUser;
import com.beehive.infrastructure.security.UserPrincipal;


@RestController
@RequestMapping("/api" + PrivilegeController.MAIN_PATH)
public class PrivilegeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ApiaryService apiaryService;
	
	@Autowired
	private PrivilegeService privilegeService;

	public static final String MAIN_PATH = "/privileges";
	private static final String GRANT_PRIVILEGES_PATH = "/grant";
	private static final String APIARY_ID = "apiary_id";
	private static final String GET_APIARY_CONTRIBUTORS_PATH = "/contributors/{" + APIARY_ID + "}";
	private static final String GRANTED_SUCCESFULLY = "New privileges for user with id {0} granted succesfully";
	
    private static final Logger logger = LoggerFactory.getLogger(PrivilegeController.class);

    
    @PostMapping(GRANT_PRIVILEGES_PATH)
    @PreAuthorize("hasRole('USER')")
    public ApiResponse grantPrivileges(@Valid @RequestBody GrantPrivilegesRequest grantPrivilegesRequest, @CurrentUser UserPrincipal currentUser) {    	
    	try {
    		User privilegeGiver = userService.getUserFormDatabase(currentUser.getId());   		
    		User targetUser = userService.getUserFormDatabase(grantPrivilegesRequest.getTargetUser());    	
    		Apiary targetApiary = apiaryService.getApiaryFromDatabase(grantPrivilegesRequest.getAffectedApiaryId());
    		
    		Set<Privilege> requestedPrivileges = grantPrivilegesRequest.getPrivileges()
    				.stream()
    				.map(Privilege::new)
    				.collect(Collectors.toSet());
    		
    		privilegeService.grantPrivilegesIfAuthorized(targetUser, targetApiary, privilegeGiver, requestedPrivileges);
    				
		} catch (IllegalArgumentException e) {
			return new ApiResponse(false, e.getMessage());
		}
    	return new ApiResponse(true, MessageFormat.format(GRANTED_SUCCESFULLY, grantPrivilegesRequest.getTargetUser()));
    }
    
    @GetMapping(GET_APIARY_CONTRIBUTORS_PATH)
    public List<ContributorDTO> getUsersWithPermissionsInApiary(@PathVariable(name = APIARY_ID) Long apiaryId) {
    	Apiary apiary = apiaryService.getApiaryFromDatabase(apiaryId);
    	List<PrivilegeProfile> profilesForApiary = privilegeService.getProfilesForApiary(apiary);
    	
    	return profilesForApiary.stream()
    			.map(privilegeService::mapToContributorDTO)
    			.collect(Collectors.toList());
    }

}