package com.beehive.domain.apiary;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.beehive.domain.user.UserRepository;
import com.beehive.infrastructure.payload.ApiResponse;
import com.beehive.infrastructure.payload.ApiaryDTO;
import com.beehive.infrastructure.payload.ApiaryRequest;
import com.beehive.infrastructure.security.CurrentUser;
import com.beehive.infrastructure.security.UserPrincipal;


@RestController
@RequestMapping("/api/apiary")
public class ApiaryController {
	
	
	@Autowired
    ApiaryService apiaryService;
	
    @Autowired
    UserRepository userRepository;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public List<ApiaryDTO> getMeApiary(@CurrentUser UserPrincipal currentUser) {
    	
        return apiaryService.getCurrentUserApiary(currentUser);
    }
    
    @PostMapping("/new")
    public ResponseEntity<?> createApiary(@Valid @RequestBody ApiaryRequest apiaryRequest) {

    	Apiary apiary;
    	try {
    		apiary = apiaryService.createApiary(apiaryRequest);
    	}
        catch (NoSuchElementException e) {
        	return new ResponseEntity(new ApiResponse(false, "User id is not correct!"),
        			HttpStatus.BAD_REQUEST);
		}

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{apiaryId}")
                .buildAndExpand(apiary.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Apiary Created Successfully"));
    }

}
