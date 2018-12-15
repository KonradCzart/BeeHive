package com.beehive.domain.notification;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
	
	Optional<Notification> findById(Long id);
	
	List<Notification>  findAllByUsers(Long user_id);

}
