package com.beehive.domain.hive;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beehive.domain.apiary.Apiary;
import com.beehive.domain.apiary.ApiaryRepository;
import com.beehive.infrastructure.payload.HiveRequest;

@Service
public class HiveService {
	
	@Autowired
	private HiveRepository hiveRepository;
	
	@Autowired
	private HiveTypeRepository hiveTypeRepository;
	
	@Autowired
	private ApiaryRepository apiaryRepository;

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
}