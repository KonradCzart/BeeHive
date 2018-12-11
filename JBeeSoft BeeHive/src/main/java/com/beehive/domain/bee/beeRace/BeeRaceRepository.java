package com.beehive.domain.action.beeQueenChanging;

import com.beehive.domain.action.honeyCollection.HoneyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BeeRaceRepository extends JpaRepository<BeeRace, Long> {
    Optional<BeeRace> findByBeeRaceId(BeeRace beeRaceId);
}