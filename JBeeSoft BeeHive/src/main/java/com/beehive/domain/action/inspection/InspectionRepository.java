package com.beehive.domain.action.inspection;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InspectionRepository extends JpaRepository<Inspection, Long> {
    Optional<Inspection> findByInspectionId(Inspection inspectionId);
}