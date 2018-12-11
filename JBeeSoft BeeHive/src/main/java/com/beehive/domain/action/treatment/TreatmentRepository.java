package com.beehive.domain.action.treatment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
    Optional<Treatment> findByTreatmentId(Treatment treatmentId);
}