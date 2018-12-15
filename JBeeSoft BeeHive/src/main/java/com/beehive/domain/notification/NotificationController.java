package com.beehive.domain.notification;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beehive.infrastructure.payload.ApiResponse;
import com.beehive.infrastructure.payload.NotificationRequest;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
	
	@Autowired
	NotificationService notificationService;
	
	
	  @PostMapping("/new")
	  @PreAuthorize("hasRole('USER')")
	  public ResponseEntity<?> createNotification(@Valid @RequestBody NotificationRequest notificationRequest){
		  
		try {
			notificationService.createNotification(notificationRequest);
  		}
	    catch (Exception e) {
	      	return new ResponseEntity(new ApiResponse(false, "User id is not correct!"),
	      			HttpStatus.BAD_REQUEST);
		}

		  return ResponseEntity.ok(new ApiResponse(true, "Notification creat sucesfully"));
	  }
}
