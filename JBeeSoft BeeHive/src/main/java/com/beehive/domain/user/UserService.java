package com.beehive.domain.user;

import java.text.MessageFormat;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.beehive.domain.userrole.Role;
import com.beehive.domain.userrole.RoleName;
import com.beehive.domain.userrole.RoleRepository;
import com.beehive.infrastructure.exceptions.AppException;
import com.beehive.infrastructure.payload.SignUpRequest;
import com.beehive.infrastructure.payload.UserDTO;

@Service
public class UserService {
	
	private static final String USERNAME_ALREADY_TAKEN_MSG = "User with username {0} already exists!";
	private static final String EMAIL_ALREADY_TAKEN_MSG = "User with email {0} already exists!";
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public User registerUser(SignUpRequest signUpRequest) {
		
		if(userRepository.existsByUsername(signUpRequest.getUsername())) {
			throw new IllegalArgumentException(MessageFormat.format(USERNAME_ALREADY_TAKEN_MSG, signUpRequest.getUsername()));
		}
		
		if(userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new IllegalArgumentException(MessageFormat.format(EMAIL_ALREADY_TAKEN_MSG, signUpRequest.getEmail()));
		}
		
		User user = User.builder()
        		.withName(signUpRequest.getName())
        		.withUsername(signUpRequest.getUsername())
        		.withEmail(signUpRequest.getEmail())
        		.withPassword(signUpRequest.getPassword())
        		.build();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        return userRepository.save(user);
	}
	
	public UserDTO mapToUserDTO(User user) {
		return UserDTO.builder()
				.withId(user.getId())
				.withUsername(user.getUsername())
				.withName(user.getName())
				.withEmail(user.getEmail())
				.build();
	}
	
	public User mapToUser(UserDTO userDTO) {
		return User.builder()
				.withId(userDTO.getId())
				.withUsername(userDTO.getUsername())
				.withName(userDTO.getName())
				.withEmail(userDTO.getEmail())
				.build();
	}
}
