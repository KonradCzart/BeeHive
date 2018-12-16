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
import com.beehive.domain.apiary.ApiaryRepository;
import com.beehive.domain.user.User;
import com.beehive.domain.user.UserRepository;
import com.beehive.infrastructure.payload.ApiResponse;
import com.beehive.infrastructure.payload.GrantPrivilegesRequest;
import com.beehive.infrastructure.payload.PrivilegesProfileRequest;
import com.beehive.infrastructure.security.CurrentUser;
import com.beehive.infrastructure.security.UserPrincipal;


@RestController
@RequestMapping("/api" + PrivilegeController.MAIN_PATH)
public class PrivilegeController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ApiaryRepository apiaryRepository;
	
	@Autowired
	private PrivilegeService privilegeService;

	public static final String MAIN_PATH = "/privileges";
	private static final String GRANT_PRIVILEGES_PATH = "/grant";
	private static final String NO_SUCH_USER = "There is no user with id {0}";
	private static final String NO_SUCH_APIARY = "There is no apiary with id {0}";
	private static final String GRANTED_SUCCESFULLY = "New privileges for user with id {0} granted succesfully";
	
    private static final Logger logger = LoggerFactory.getLogger(PrivilegeController.class);

    
    @PostMapping(GRANT_PRIVILEGES_PATH)
    @PreAuthorize("hasRole('USER')")
    public ApiResponse grantPrivileges(@Valid @RequestBody GrantPrivilegesRequest grantPrivilegesRequest, @CurrentUser UserPrincipal currentUser) {
    	
    	try {
    		User privilegeGiver = userRepository.findById(currentUser.getId()).orElseThrow(
    				() -> new IllegalArgumentException(MessageFormat.format(NO_SUCH_USER, currentUser.getId())));
    		
    		User targetUser = userRepository.findById(grantPrivilegesRequest.getTargetUser()).orElseThrow( 
    				() -> new IllegalArgumentException(MessageFormat.format(NO_SUCH_USER, grantPrivilegesRequest.getTargetUser())));
    		
    		Apiary targetApiary = apiaryRepository.findById(grantPrivilegesRequest.getAffectedApiaryId()).orElseThrow(
    				() -> new IllegalArgumentException(MessageFormat.format(NO_SUCH_APIARY, grantPrivilegesRequest.getAffectedApiaryId())));
    		
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

}