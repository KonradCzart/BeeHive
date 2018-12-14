package com.beehive.domain.hive;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beehive.domain.apiary.Apiary;
import com.beehive.domain.apiary.ApiaryRepository;
import com.beehive.domain.bee.queen.BeeQueen;
import com.beehive.domain.bee.queen.BeeQueenService;
import com.beehive.infrastructure.payload.BeeQueenDTO;
import com.beehive.infrastructure.payload.HiveDTO;
import com.beehive.infrastructure.payload.HiveRequest;
import com.beehive.infrastructure.payload.ValueResponse;

@Service
public class HiveService {
	
	@Autowired
	private HiveRepository hiveRepository;
	
	@Autowired
	private HiveTypeRepository hiveTypeRepository;
	
	@Autowired
	private ApiaryRepository apiaryRepository;
	
	@Autowired
	private BeeQueenService beeQueenService;

	public Hive createHive(HiveRequest hiveRequest) throws NoSuchElementException{
		
		
		Optional<Apiary> apiary = apiaryRepository.findById(hiveRequest.getApiary_id());
		Optional<HiveType> type = hiveTypeRepository.findById(hiveRequest.getHiveType_id());
		
		Apiary apiaryForHive = apiary.orElseThrow(() -> new NoSuchElementException("Apiary id is not correct"));
		HiveType hiveType = type.orElseThrow(() -> new NoSuchElementException("Hive type id is not correct"));
		
		Hive hive = new Hive(hiveRequest.getName(), apiaryForHive, hiveType, hiveRequest.getBoxNumber());
		apiaryForHive.addHive(hive);
		hive = hiveRepository.save(hive);
		
		return hive;
	}
	
	public List<ValueResponse> getAllHiveType(){
		
		List<HiveType> types = hiveTypeRepository.findAll();
		
		return types.stream()
				.map( type -> mapHiveTypeToValueResponse(type))
				.collect(Collectors.toList());
	}
	
	public ValueResponse mapHiveTypeToValueResponse(HiveType type) {
		
		return new ValueResponse(type.getId(),type.getName());
	}
	
	public HiveDTO mapHiveToHiveDTO(Hive hive) {
		
		BeeQueen queen = hive.getBeeQueen();
		BeeQueenDTO queenDTO = null;
		
		if(queen != null) {
			queenDTO = beeQueenService.mapBeeQueenToBeeQueenDTO(queen);
		}
		
		return HiveDTO.builder()
				.withId(hive.getId())
				.withName(hive.getName())
				.withQueenDTO(queenDTO)
				.withTypeName(hive.getType().getName())
				.withBoxNumber(hive.getBoxNumber())
				.build();
	}
}
