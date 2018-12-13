package com.beehive.domain.bee.queen;

import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beehive.infrastructure.payload.ApiResponse;
import com.beehive.infrastructure.payload.BeeQueenRequest;


@RestController
@RequestMapping("/api/queen")
public class BeeQueenController {
	
	@Autowired
	BeeQueenService beeQueenService;
	
	@PostMapping("/new")
    public ResponseEntity<?> createHive(@Valid @RequestBody BeeQueenRequest beeQueenRequest) {

    	    	
    	try {
    		beeQueenService.addBeeQueenToHive(beeQueenRequest);
    	}
        catch (NoSuchElementException e) {
        	return new ResponseEntity(new ApiResponse(false, e.getMessage()),
        			HttpStatus.BAD_REQUEST);
		}
    	
        return ResponseEntity.ok(new ApiResponse(true, "Queen add to hive successfully"));
    }
}
