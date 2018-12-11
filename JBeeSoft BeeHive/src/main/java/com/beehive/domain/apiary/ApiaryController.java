package com.beehive.domain.apiary;

import java.net.URI;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.beehive.domain.user.UserRepository;
import com.beehive.infrastructure.payload.ApiResponse;
import com.beehive.infrastructure.payload.ApiaryRequest;


@RestController
@RequestMapping("/api/apiary")
public class ApiaryController {
	
	
	@Autowired
    ApiaryService apiaryService;
	
    @Autowired
    UserRepository userRepository;

    
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
