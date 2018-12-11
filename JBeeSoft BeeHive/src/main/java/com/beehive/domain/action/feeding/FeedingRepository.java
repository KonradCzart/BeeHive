package com.beehive.domain.action.feeding;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedingRepository extends JpaRepository<Feeding, Long> {
    Optional<Feeding> findByFeedingId(Feeding feedingId);
}