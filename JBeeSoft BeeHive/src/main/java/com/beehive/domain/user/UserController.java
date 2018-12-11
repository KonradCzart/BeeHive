package com.beehive.domain.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beehive.infrastructure.payload.UserDTO;
import com.beehive.infrastructure.security.CurrentUser;
import com.beehive.infrastructure.security.UserPrincipal;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserDTO getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return UserDTO.builder()
        		.withId(currentUser.getId())
        		.withName(currentUser.getName())
        		.withUsername(currentUser.getUsername())
        		.build();
    }

}