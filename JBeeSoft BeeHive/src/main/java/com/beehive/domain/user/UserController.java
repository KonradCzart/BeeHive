package com.beehive.domain.user;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beehive.infrastructure.payload.AvailabilityResponse;
import com.beehive.infrastructure.payload.UserDTO;
import com.beehive.infrastructure.payload.ValueResponse;
import com.beehive.infrastructure.security.CurrentUser;
import com.beehive.infrastructure.security.UserPrincipal;


@RestController
@RequestMapping("/api" + UserController.MAIN_PATH)
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;

	public static final String MAIN_PATH = "/user";
	private static final String ME_PATH = "/me";
	public static final String CHECK_AVAILABILITY_PATH = "/isAvailable";
	public static final String EMAIL = "requested_email";
	public static final String EMAIL_PATH = "/email/{" + EMAIL + "}";
	public static final String USERNAME = "requested_username";
	public static final String USERNAME_PATH = "/username/{" + USERNAME + "}";
	
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping(ME_PATH)
    @PreAuthorize("hasRole('USER')")
    public UserDTO getCurrentUser(@CurrentUser UserPrincipal currentUser) {
    	User user = userService.getUserFormDatabase(currentUser.getId());
    	return userService.mapToUserDTO(user);
    }
    
    @GetMapping(CHECK_AVAILABILITY_PATH + USERNAME_PATH)
    public AvailabilityResponse checkUsernameAvailability(@PathVariable(USERNAME) String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new AvailabilityResponse(isAvailable);
    }

    @GetMapping(CHECK_AVAILABILITY_PATH + EMAIL_PATH)
    public AvailabilityResponse checkEmailAvailability(@PathVariable(EMAIL) String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new AvailabilityResponse(isAvailable);
    }
    
    @GetMapping("/like/{contains}")
    @PreAuthorize("hasRole('USER')")
    public List<ValueResponse> getUsersContains(@PathVariable String contains){
    	List<User> users = userService.getUsersContains(contains);
    	return users.stream()
    			.map( user -> userService.mapUserToValueResponse(user))
    			.collect(Collectors.toList());
    }

}