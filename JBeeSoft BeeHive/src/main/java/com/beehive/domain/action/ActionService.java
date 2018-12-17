package com.beehive.domain.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionService {
	@Autowired
	ActionRepository actionRepository;

	public void registerPerformedAction(Action action) {
		actionRepository.save(action);
	}
}
