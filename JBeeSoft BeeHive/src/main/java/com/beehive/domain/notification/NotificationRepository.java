package com.beehive.domain.notification;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beehive.domain.user.User;



@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
	
	Optional<Notification> findById(Long id);
	
	List<Notification>  findAllByUsers(Long id);
	
	List<Notification>  findAllByUsers(User users);

}
