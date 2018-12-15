package com.beehive.domain.notification;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beehive.domain.user.User;
import com.beehive.domain.user.UserRepository;
import com.beehive.infrastructure.payload.NotificationDTO;
import com.beehive.infrastructure.payload.NotificationRequest;
import com.beehive.infrastructure.security.UserPrincipal;

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
				.withIsRealize(notifyRequest.getIsRealize())
				.build();
		
		
		System.out.println("dsada");
		return notificationRepository.save(notification);
	}
	
	
	public void deleteNotificationById(Long id) throws NoSuchElementException{
		
		Notification note = notificationRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Notification id is not correct"));
		
		notificationRepository.delete(note);
	}
	
	public Map<Date, List<NotificationDTO>> getUserAllNotification(UserPrincipal currentUser){

		User user = userRepository.findById(currentUser.getId()).orElseThrow();

		Map<Date, List<NotificationDTO>> mapNote = notificationRepository.findAllByUsers(user)
				.stream()
				.collect(Collectors.groupingBy(Notification::getDate, Collectors.mapping(this::mapNotificationToNotificationDTO, Collectors.toList())));
		
		return mapNote;
	}
	
	
	
	
	public NotificationDTO mapNotificationToNotificationDTO(Notification note) {
		
		return NotificationDTO.builder()
				.withId(note.getId())
				.withTitle(note.getTitle())
				.withDescription(note.getDescription())
				.withIsRealize(note.getIsRealize())
				.withDate(note.getDate())
				.build();
	}
}
