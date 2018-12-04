package com.beehive.domain.action.hiver;

import com.beehive.domain.action.honeyCollection.HoneyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HiverRepository extends JpaRepository<Hiver, Long> {
    Optional<Hiver> findByHiverId(Hiver hiverId);
}