package com.beehive.domain.location;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{
	
	Optional<Location> findByCountryAndCity(String country, String city);

}
