package com.beehive.domain.notification;


import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beehive.infrastructure.payload.ApiResponse;
import com.beehive.infrastructure.payload.NotificationDTO;
import com.beehive.infrastructure.payload.NotificationRequest;
import com.beehive.infrastructure.security.CurrentUser;
import com.beehive.infrastructure.security.UserPrincipal;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
	
	@Autowired
	NotificationService notificationService;
	
	
	  @PostMapping("/new")
	  @PreAuthorize("hasRole('USER')")
	  public ResponseEntity<?> createNotification(@Valid @RequestBody NotificationRequest notificationRequest){
		
		System.out.println(notificationRequest.getUsersId());
		
		try {
			notificationService.createNotification(notificationRequest);
  		}
	    catch (Exception e) {
	      	return new ResponseEntity(new ApiResponse(false, "User id is not correct!"),
	      			HttpStatus.BAD_REQUEST);
		}

		  return ResponseEntity.ok(new ApiResponse(true, "Notification creat sucesfully"));
	  }
	  
	  @DeleteMapping("/delete/{noteId}")
	  @PreAuthorize("hasRole('USER')")
	  public  ResponseEntity<?> deleteHive(@CurrentUser UserPrincipal currentUser, @PathVariable Long noteId){
		  
		  notificationService.deleteNotificationById(noteId);
		  
		  return ResponseEntity.ok(new ApiResponse(true, "Notification delete successfully"));
		  
	  }
	  
	  @GetMapping("/me")
	  @PreAuthorize("hasRole('USER')")
	  public Map<Date, List<NotificationDTO>> getMeAllNotification(@CurrentUser UserPrincipal currentUser){
		  
		  return notificationService.getUserAllNotification(currentUser);
	  }
}
