package com.beehive.domain.action.honeyCollection;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HoneyTypeRepository extends JpaRepository<HoneyType, Long> {
    Optional<HoneyType> findByHoneyTypeId(HoneyType honeyTypeId);
}