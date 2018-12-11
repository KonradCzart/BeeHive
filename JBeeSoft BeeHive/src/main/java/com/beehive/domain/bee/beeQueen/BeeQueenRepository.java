package com.beehive.domain.action.beeQueenChanging;

import com.beehive.domain.action.honeyCollection.HoneyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BeeQueenRepository extends JpaRepository<BeeQueen, Long> {
    Optional<BeeQueen> findByBeeQueenId(BeeQueen beeQueenId);
}