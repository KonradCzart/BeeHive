package com.beehive.domain.honey;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beehive.infrastructure.payload.ValueResponse;

@Service
public class HoneyTypeService {
	
	@Autowired
	HoneyTypeRepository honeyTypeRepository;
	
	private static final String NO_SUCH_HONEY_TYPE = "Honey type with id {0} doesn't exist";
	
	public HoneyType geHoneyTypeFromDatabase(Long id) {
		return honeyTypeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(MessageFormat.format(NO_SUCH_HONEY_TYPE, id)));
	}
	
	public List<ValueResponse> getAllHoneyType(){
		List<HoneyType> types = honeyTypeRepository.findAll();
		
		return types.stream()
				.map( type -> mapHoneyTypeToValueResponse(type))
				.collect(Collectors.toList());
	}

	public ValueResponse mapHoneyTypeToValueResponse(HoneyType type) {
		return new ValueResponse(type.getId(),type.getName());
	}
}