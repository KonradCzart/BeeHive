package com.beehive.domain.honey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beehive.infrastructure.payload.ValueResponse;
import com.beehive.infrastructure.security.CurrentUser;
import com.beehive.infrastructure.security.UserPrincipal;

@RestController
@RequestMapping("/api/honey")
public class HoneyTypeController {
	
	@Autowired
	HoneyTypeService honeyTypeService;
	
    @GetMapping("/type")
    @PreAuthorize("hasRole('USER')")
    public List<ValueResponse> getAllhoneyType(@CurrentUser UserPrincipal currentUser){  	
    	return honeyTypeService.getAllHoneyType();
    }
    

}
