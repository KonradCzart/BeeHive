package com.beehive.domain.action.honeyCollection;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HoneyCollectionRepository extends JpaRepository<HoneyCollection, Long> {
    Optional<HoneyCollection> findByHoneyCollectionId(HoneyCollection honeyCollectionId);
}