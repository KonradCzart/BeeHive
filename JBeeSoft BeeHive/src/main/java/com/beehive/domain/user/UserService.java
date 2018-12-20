package com.beehive.domain.user;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.beehive.domain.userrole.Role;
import com.beehive.infrastructure.payload.SignUpRequest;
import com.beehive.infrastructure.payload.UserDTO;
import com.beehive.infrastructure.payload.ValueResponse;

@Service
public class UserService {
	
	private static final String USERNAME_ALREADY_TAKEN_MSG = "User with username {0} already exists!";
	private static final String EMAIL_ALREADY_TAKEN_MSG = "User with email {0} already exists!";
	private static final String NO_SUCH_USER = "There is no user with id {0}";
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Autowired
	private UserRepository userRepository;
	
	public User registerUser(User newUser, Role userRole) {	
		if(userRepository.existsByUsername(newUser.getUsername())) {
			throw new IllegalArgumentException(MessageFormat.format(USERNAME_ALREADY_TAKEN_MSG, newUser.getUsername()));
		}
		
		if(userRepository.existsByEmail(newUser.getEmail())) {
			throw new IllegalArgumentException(MessageFormat.format(EMAIL_ALREADY_TAKEN_MSG, newUser.getEmail()));
		}
		
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        newUser.setRoles(Collections.singleton(userRole));

        return userRepository.save(newUser);
	}
	
	public User getUserFormDatabase(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException(MessageFormat.format(NO_SUCH_USER, userId)));
	}
	
	public List<User> getUsersContains(String contains){
		return userRepository.findByUsernameContaining(contains);
	}
	
	public UserDTO mapToUserDTO(User user) {
		return UserDTO.builder()
				.withId(user.getId())
				.withUsername(user.getUsername())
				.withName(user.getName())
				.withEmail(user.getEmail())
				.build();
	}
	
	public User mapToUser(SignUpRequest signUpRequest) {
		return User.builder()
				.withUsername(signUpRequest.getUsername())
				.withName(signUpRequest.getName())
				.withEmail(signUpRequest.getEmail())
				.withPassword(signUpRequest.getPassword())
				.build();
	}
	
	public ValueResponse mapUserToValueResponse(User user) {		
		return new ValueResponse(user.getId(), user.getUsername());
	}
}
