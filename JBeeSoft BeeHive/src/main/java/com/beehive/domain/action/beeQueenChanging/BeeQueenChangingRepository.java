package com.beehive.domain.action.beeQueenChanging;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BeeQueenChangingRepository extends JpaRepository<BeeQueenChanging, Long> {
    Optional<BeeQueenChanging> findByBeeQueenChangingId(BeeQueenChanging beeQueenChangingId);
}