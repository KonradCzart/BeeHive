package com.beehive.domain.honey;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HoneyTypeService {
	
	@Autowired
	HoneyTypeRepository honeyTypeRepository;
	
	private static final String NO_SUCH_HONEY_TYPE = "Honey type with id {0} doesn't exist";
	
	public HoneyType geHoneyTypeFromDatabase(Long id) {
		return honeyTypeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(MessageFormat.format(NO_SUCH_HONEY_TYPE, id)));
	}
}
