package com.beehive.domain.hive;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HiveTypeService {
	
	@Autowired
	private HiveTypeRepository hiveTypeRepository;
	
	private static final String NO_SUCH_HIVE_TYPE = "Hive type with id {0} doesn't exist";
	private static final String NO_SUCH_TYPE_NAME = "Hive type with name {0} doesn't exist";
	
	public HiveType getHiveTypeFromDatabase(Long id) {
		return hiveTypeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(MessageFormat.format(NO_SUCH_HIVE_TYPE, id)));
	}
	
	public List<HiveType> getAllHiveTypeFromDatabase(){
		return hiveTypeRepository.findAll();
	}
	
	public HiveType getHiveTypeFromDatabase(String name) {
		return hiveTypeRepository.findByName(name)
				.orElseThrow(() -> new IllegalArgumentException(MessageFormat.format(NO_SUCH_TYPE_NAME, name)));
	}
}
