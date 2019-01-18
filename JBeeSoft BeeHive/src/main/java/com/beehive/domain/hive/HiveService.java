package com.beehive.domain.hive;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beehive.domain.apiary.Apiary;
import com.beehive.domain.apiary.ApiaryService;
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
	private HiveTypeService hiveTypeService;
	
	@Autowired
	private BeeQueenService beeQueenService;
	
	@Autowired
	private ApiaryService apiaryService;
	
	private static final String NO_SUCH_HIVES = "Some hives with passed ids don't exist";
	private static final String NOT_ALL_HIVES_BELONG_TO_APIARY = "There are hives that don't belong to specified apiary!";
	private static final String NO_SUCH_HIVE = "Hive with id {0} doesn't exist";

	public Hive createHive(HiveRequest hiveRequest){		
		Apiary apiaryForHive = apiaryService.getApiaryFromDatabase(hiveRequest.getApiaryId());	
		HiveType hiveType = hiveTypeService.getHiveTypeFromDatabase(hiveRequest.getHiveTypeId());
		
		Hive hive = new Hive(hiveRequest.getName(), apiaryForHive, hiveType, hiveRequest.getBoxNumber());
		apiaryForHive.addHive(hive);
		
		return hiveRepository.save(hive);
	}
	
	public List<ValueResponse> getAllHiveType(){
		List<HiveType> types = hiveTypeService.getAllHiveTypeFromDatabase();
		
		return types.stream()
				.map( type -> mapHiveTypeToValueResponse(type))
				.collect(Collectors.toList());
	}
	
	public void deleteQueenWithHive(Long hiveId) {
		Hive hive = getHiveFromDatabase(hiveId);
		BeeQueen queen = hive.getBeeQueen();
		
		if(queen != null) {
			hive.setBeeQueen(null);
			beeQueenService.deleteQueenById(queen.getId());
		}
		
	}
	
	public void deleteHive(Long hiveId) {
		Hive hive = getHiveFromDatabase(hiveId);	
		this.deleteQueenWithHive(hiveId);
		hive.setIsExist(false);
		hiveRepository.save(hive);		
	}
	
	public Hive modifyHive(Hive hive) {
		return hiveRepository.save(hive);
	}	
	
	public ValueResponse mapHiveTypeToValueResponse(HiveType type) {		
		return new ValueResponse(type.getId(),type.getName());
	}
	
	public Set<Hive> getHivesFromDatabase(Set<Long> ids) {
		Set<Hive> hives = hiveRepository.findAllByIdIn(ids)
				.stream()
				.filter(Hive::getIsExist)
				.collect(Collectors.toSet());
		
		if(ids.size() != hives.size()) {
			throw new IllegalArgumentException(NO_SUCH_HIVES);
		}
		
		return hives;
	}
	
	public Set<Hive> getHivesInApiaryFromDatabase(Set<Long> ids, Long apiaryId) {
		Set<Hive> hives = getHivesFromDatabase(ids);
		
		if(!allHivesBelongsToApiary(hives, apiaryId)) {
			throw new IllegalAccessError(NOT_ALL_HIVES_BELONG_TO_APIARY);
		}
		
		return hives;
	}
	
	public Hive getHiveFromDatabase(Long id) {
		return hiveRepository.findById(id)
				.filter(Hive::getIsExist)
				.orElseThrow( () -> new IllegalArgumentException(MessageFormat.format(NO_SUCH_HIVE, id)));
	}
	
	private boolean allHivesBelongsToApiary(Set<Hive> hives, Long apiaryId) {
		return hives.stream()
				.allMatch(hive -> hive.getApiary().getId().equals(apiaryId));
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
				.withHiveTypeId(hive.getHiveType().getId())
				.withTypeName(hive.getType().getName())
				.withBoxNumber(hive.getBoxNumber())
				.withApiaryId(hive.getApiary().getId())
				.build();
	}
}
