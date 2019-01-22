package com.beehive.domain.apiary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApiaryRepository extends JpaRepository<Apiary, Long>{
	
	Optional<Apiary> findById(Long id);
	
	List<Apiary> findByLocationId(Long location_id);

}
