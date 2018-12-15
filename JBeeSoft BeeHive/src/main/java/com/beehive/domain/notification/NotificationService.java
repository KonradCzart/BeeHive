package com.beehive.domain.notification;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beehive.domain.user.User;
import com.beehive.domain.user.UserRepository;
import com.beehive.infrastructure.payload.NotificationRequest;

@Service
public class NotificationService {
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Notification createNotification(NotificationRequest notifyRequest) {
		
		List<User> usersList = userRepository.findAllById(notifyRequest.getUsersId());
		Set<User> users = new HashSet<>(usersList);
		
		Notification notification = Notification.builder()
				.withTitle(notifyRequest.getTitle())
				.withDescription(notifyRequest.getDescription())
				.withDate(notifyRequest.getDate())
				.withUsers(users)
				.build();
		
		return notificationRepository.save(notification);
	}
	

}
