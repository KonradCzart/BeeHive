package com.beehive.domain.bee.queen;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beehive.domain.bee.race.BeeRace;
import com.beehive.domain.bee.race.BeeRaceRepository;
import com.beehive.domain.hive.Hive;
import com.beehive.domain.hive.HiveRepository;
import com.beehive.infrastructure.payload.BeeQueenDTO;
import com.beehive.infrastructure.payload.BeeQueenRequest;

@Service
public class BeeQueenService {
	
	@Autowired
	private HiveRepository hiveRepository;
	
	@Autowired
	private BeeRaceRepository beeRaceRepository;
	
	@Autowired
	private BeeQueenRepository beeQueenRepository;
	
	public BeeQueen addBeeQueenToHive(BeeQueenRequest beeQueenRequest) throws NoSuchElementException{
		
		Hive hive = hiveRepository.findById(beeQueenRequest.getHive_id()).orElseThrow(() -> new NoSuchElementException("Hive id is not correct"));
		BeeRace race = beeRaceRepository.findById(beeQueenRequest.getRace_id()).orElseThrow( () -> new NoSuchElementException("BeeRace id os not correct"));
		
		BeeQueen newQueen = BeeQueen.builder()
				.withBeeRace(race)
				.withAge(beeQueenRequest.getAge())
				.withColor(beeQueenRequest.getColor())
				.withDescription(beeQueenRequest.getDescription())
				.withIsReproducing(beeQueenRequest.getIsReproducting())
				.build();
		
		
		newQueen = beeQueenRepository.save(newQueen);
		
		BeeQueen oldQueen = hive.getBeeQueen();
		hive.setBeeQueen(newQueen);
		hiveRepository.save(hive);
		
		if(oldQueen != null) {
			beeQueenRepository.delete(oldQueen);
		}
		
		return newQueen;
	}
	
	public BeeQueenDTO mapBeeQueenToBeeQueenDTO(BeeQueen queen) {
		return	BeeQueenDTO.builder()
				.withId(queen.getId())
				.withDescription(queen.getDescription())
				.withAge(queen.getAge())
				.withColor(queen.getColor())
				.withIsReproducting(queen.getIsReproducting())
				.withRaceName(queen.getBeeRace().getName())
				.build();
	}
	
}
