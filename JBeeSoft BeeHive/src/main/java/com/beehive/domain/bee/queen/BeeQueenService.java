package com.beehive.domain.bee.queen;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beehive.domain.bee.race.BeeRace;
import com.beehive.domain.bee.race.BeeRaceRepository;
import com.beehive.domain.hive.Hive;
import com.beehive.domain.hive.HiveService;
import com.beehive.infrastructure.payload.BeeQueenDTO;
import com.beehive.infrastructure.payload.BeeQueenRequest;
import com.beehive.infrastructure.payload.ValueResponse;

@Service
public class BeeQueenService {
	
	@Autowired
	private HiveService hiveService;
	
	@Autowired
	private BeeRaceRepository beeRaceRepository;
	
	@Autowired
	private BeeQueenRepository beeQueenRepository;
	
	private static final String NO_SUCH_QUEEN = "Queen with id {0} doesn't exist";
	private static final String NO_SUCH_RACE = "Bee race with id {0} doesn't exist";
	
	public BeeQueen addBeeQueenToHive(BeeQueenRequest beeQueenRequest) {		
		Hive hive = hiveService.getHiveFromDatabase(beeQueenRequest.getHiveId());
		BeeRace race = getBeeRaceFromDatabase(beeQueenRequest.getRaceId());
		
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
		hiveService.modifyHive(hive);
		
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
	
	public void deleteQueenById(Long id) {
		
		BeeQueen queen = getBeeQueenFromDatabase(id);
		beeQueenRepository.delete(queen);
	}
	
	public BeeQueen modifyQueen(BeeQueen queen){
		return beeQueenRepository.save(queen);
	}
	
	
	public BeeRace getBeeRaceFromDatabase(Long id) {
		return beeRaceRepository.findById(id)
				.orElseThrow( () -> new IllegalArgumentException(MessageFormat.format(NO_SUCH_RACE , id)) );
	}
	
	public BeeQueen getBeeQueenFromDatabase(Long id) {
		return beeQueenRepository.findById(id)
				.orElseThrow( () -> new IllegalArgumentException(MessageFormat.format(NO_SUCH_QUEEN , id)) );
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
