package com.beehive.domain.notification;


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
import org.springframework.web.bind.annotation.PutMapping;
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
		
		
		try {
			notificationService.createNotification(notificationRequest);
  		}
	    catch (Exception e) {
	      	return new ResponseEntity<ApiResponse>(new ApiResponse(false, "User id is not correct!"),
	      			HttpStatus.BAD_REQUEST);
		}

		  return ResponseEntity.ok(new ApiResponse(true, "Notification creat sucesfully"));
	  }
	  
	  @DeleteMapping("/delete/{noteId}")
	  @PreAuthorize("hasRole('USER')")
	  public  ResponseEntity<?> deleteHive(@CurrentUser UserPrincipal currentUser, @PathVariable Long noteId){
		  
		  try {
			  notificationService.deleteNotificationById(noteId);
		  }
		  catch (Exception e) {
			  return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()),
		      			HttpStatus.BAD_REQUEST);
		}
		  
		  return ResponseEntity.ok(new ApiResponse(true, "Notification delete successfully"));
		  
	  }
	  
	  @GetMapping("/me")
	  @PreAuthorize("hasRole('USER')")
	  public Map<String, List<NotificationDTO>> getMeAllNotification(@CurrentUser UserPrincipal currentUser){
		  
		  return notificationService.getUserAllNotification(currentUser);
	  }
	  
	  @PutMapping("/modify")
	  @PreAuthorize("hasRole('USER')")
	  public  ResponseEntity<?> modifyNotification(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody NotificationDTO noteDTO) {
			
			try {
				Notification note = notificationService.getNotificationFromDatabase(noteDTO.getId());
				
				
				note.setDate(noteDTO.getDate());
				note.setDescription(noteDTO.getDescription());
				note.setTitle(noteDTO.getTitle());
				note.setIsRealize(noteDTO.getIsRealize());		
				
				note = notificationService.modifyNotification(note);
				noteDTO = notificationService.mapNotificationToNotificationDTO(note);
	  		}
		    catch (Exception e) {
		      	return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()),
		      			HttpStatus.BAD_REQUEST);
			}
			
			return ResponseEntity.ok(noteDTO);
	  }
	  
	  @PutMapping("/realize/{noteId}")
	  @PreAuthorize("hasRole('USER')")
	  public  ResponseEntity<?> realizeNotification(@CurrentUser UserPrincipal currentUser, @PathVariable Long noteId) {
		 
		NotificationDTO noteDTO;
		try {
			Notification note = notificationService.getNotificationFromDatabase(noteId);
			note.setIsRealize(true);
			note = notificationService.modifyNotification(note);
			noteDTO = notificationService.mapNotificationToNotificationDTO(note);
			
  		}
	    catch (Exception e) {
	      	return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()),
	      			HttpStatus.BAD_REQUEST);
	    }
		      	
		      			
		return ResponseEntity.ok(noteDTO);
	  }
	  
	  @PutMapping("/unrealize/{noteId}")
	  @PreAuthorize("hasRole('USER')")
	  public  ResponseEntity<?> unrealizeNotification(@CurrentUser UserPrincipal currentUser, @PathVariable Long noteId) {
		 
		NotificationDTO noteDTO;
		try {
			Notification note = notificationService.getNotificationFromDatabase(noteId);
			note.setIsRealize(false);
			note = notificationService.modifyNotification(note);
			noteDTO = notificationService.mapNotificationToNotificationDTO(note);
			
  		}
	    catch (Exception e) {
	      	return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()),
	      			HttpStatus.BAD_REQUEST);
	    }
		      	
		      			
		return ResponseEntity.ok(noteDTO);
	  }
}
