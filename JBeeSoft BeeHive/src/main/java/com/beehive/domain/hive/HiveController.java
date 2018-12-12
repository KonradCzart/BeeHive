package com.beehive.domain.hive;

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

import com.beehive.domain.apiary.Apiary;
import com.beehive.infrastructure.payload.ApiResponse;
import com.beehive.infrastructure.payload.ApiaryRequest;
import com.beehive.infrastructure.payload.HiveRequest;

@RestController
@RequestMapping("/api/hive")
public class HiveController {
	
	@Autowired
	HiveService hiveService;
	
    @PostMapping("/new")
    public ResponseEntity<?> createHive(@Valid @RequestBody HiveRequest hiveRequest) {

    	    	
    	try {
    		hiveService.createHive(hiveRequest);
    	}
        catch (NoSuchElementException e) {
        	return new ResponseEntity(new ApiResponse(false, e.getMessage()),
        			HttpStatus.BAD_REQUEST);
		}
    	
        return ResponseEntity.ok(new ApiResponse(true, "Hive created Successfully"));
    }
}
