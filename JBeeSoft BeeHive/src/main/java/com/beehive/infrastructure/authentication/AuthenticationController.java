package com.beehive.infrastructure.authentication;

import com.beehive.domain.user.User;
import com.beehive.domain.user.UserService;
import com.beehive.domain.userrole.Role;
import com.beehive.domain.userrole.RoleName;
import com.beehive.domain.userrole.RoleService;
import com.beehive.infrastructure.payload.ApiResponse;
import com.beehive.infrastructure.payload.JwtAuthenticationResponse;
import com.beehive.infrastructure.payload.LoginRequest;
import com.beehive.infrastructure.payload.SignUpRequest;
import com.beehive.infrastructure.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService userService;
    
    @Autowired
	private RoleService roleService;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		User newUser; 
		try {
			Role userRole = roleService.getUserRoleFromDatabase(RoleName.ROLE_USER);
			User user = userService.mapToUser(signUpRequest);
			newUser = userService.registerUser(user, userRole);		
			
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
		}

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/users/{username}")
				.buildAndExpand(newUser.getUsername())
				.toUri();

		return ResponseEntity.created(location)
				.body(new ApiResponse(true, "User registered successfully"));
	}
}

