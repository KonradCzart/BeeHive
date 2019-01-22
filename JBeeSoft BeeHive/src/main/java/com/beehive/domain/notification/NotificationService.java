package com.beehive.domain.notification;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beehive.domain.user.User;
import com.beehive.domain.user.UserService;
import com.beehive.infrastructure.payload.NotificationDTO;
import com.beehive.infrastructure.payload.NotificationRequest;
import com.beehive.infrastructure.payload.UserDTO;
import com.beehive.infrastructure.security.UserPrincipal;

@Service
public class NotificationService {
	
	private static final String NO_SUCH_NOTIFICATION = "Notification with id {0} doesn't exist";
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@Autowired
	private UserService userService;
	
	public Notification createNotification(NotificationRequest notifyRequest, UserPrincipal currentUser) {
		
		List<User> usersList = userService.getUsersFromDatabase(notifyRequest.getUsersId());
		User author = userService.getUserFormDatabase(currentUser.getId());

		Set<User> users = new HashSet<>(usersList);
		
		Notification notification = Notification.builder()
				.withTitle(notifyRequest.getTitle())
				.withDescription(notifyRequest.getDescription())
				.withAuthor(author)
				.withDate(notifyRequest.getDate())
				.withUsers(users)
				.withIsRealize(notifyRequest.getIsRealize())
				.build();
		
		
		return notificationRepository.save(notification);
	}
	
	
	public void deleteNotificationById(Long id) throws NoSuchElementException{
		
		Notification note = notificationRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Notification id is not correct"));
		
		notificationRepository.delete(note);
	}
	
	public Map<String, List<NotificationDTO>> getUserAllNotification(UserPrincipal currentUser){

		User user = userService.getUserFormDatabase(currentUser.getId());

		Map<String, List<NotificationDTO>> mapNote = notificationRepository.findAllByUsers(user)
				.stream()
				.collect(Collectors.groupingBy(Notification::getDayDate, Collectors.mapping(this::mapNotificationToNotificationDTO, Collectors.toList())));
		
		return mapNote;
	}
	
	public Notification modifyNotification(Notification notification){
		return notificationRepository.save(notification);
	}
	
	public Notification getNotificationFromDatabase(Long id) {
		return notificationRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(MessageFormat.format(NO_SUCH_NOTIFICATION, id)));
	}
	
	
	public NotificationDTO mapNotificationToNotificationDTO(Notification note) {
		
		UserDTO author = userService.mapToUserDTO(note.getAuthor());
		List<UserDTO> users = note.getUsers()
				.stream()
				.map(userService::mapToUserDTO)
				.collect(Collectors.toList());
		
		return NotificationDTO.builder()
				.withId(note.getId())
				.withTitle(note.getTitle())
				.withDescription(note.getDescription())
				.withIsRealize(note.getIsRealize())
				.withDate(note.getDate())
				.withAuthor(author)
				.withUsers(users)
				.build();
	}
}
