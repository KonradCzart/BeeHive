package com.beehive.domain.bee.queen;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beehive.domain.bee.race.BeeRace;
import com.beehive.domain.bee.race.BeeRaceRepository;
import com.beehive.domain.hive.Hive;
import com.beehive.domain.hive.HiveRepository;
import com.beehive.infrastructure.payload.BeeQueenDTO;
import com.beehive.infrastructure.payload.BeeQueenRequest;
import com.beehive.infrastructure.payload.ValueResponse;

@Service
public class BeeQueenService {
	
	@Autowired
	private HiveRepository hiveRepository;
	
	@Autowired
	private BeeRaceRepository beeRaceRepository;
	
	@Autowired
	private BeeQueenRepository beeQueenRepository;
	
	public BeeQueen addBeeQueenToHive(BeeQueenRequest beeQueenRequest) throws NoSuchElementException{
		
		Hive hive = hiveRepository.findById(beeQueenRequest.getHive_id())
				.orElseThrow(() -> new NoSuchElementException("Hive id is not correct"));
		
		BeeRace race = beeRaceRepository.findById(beeQueenRequest.getRace_id())
				.orElseThrow( () -> new NoSuchElementException("BeeRace id os not correct"));
		
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
	
	public List<ValueResponse> getAllQueenRace(){
		
		List<BeeRace> races = beeRaceRepository.findAll();
		
		return races.stream()
				.map( race -> mapBeeRaceToValueResponse(race))
				.collect(Collectors.toList());
	}
	
	public void deleteQueenById(Long id) throws NoSuchElementException {
		
		BeeQueen queen = beeQueenRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("BeeQueen id os not correct"));
		
		beeQueenRepository.delete(queen);
	}
	
	public BeeQueenDTO modifyQueen(BeeQueenDTO queenDTO) throws NoSuchElementException{
		
		BeeQueen queen = beeQueenRepository.findById(queenDTO.getId())
				.orElseThrow(() -> new NoSuchElementException("BeeQueen id os not correct"));
		
		queen.setColor(queenDTO.getColor());
		queen.setDescription(queenDTO.getDescription());
		queen.setIsReproducting(queenDTO.getIsReproducting());
		
		queen = beeQueenRepository.save(queen);
		
		return mapBeeQueenToBeeQueenDTO(queen);
	}
	
	public ValueResponse mapBeeRaceToValueResponse(BeeRace race) {
		
		return new ValueResponse(race.getId(),race.getName());
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
