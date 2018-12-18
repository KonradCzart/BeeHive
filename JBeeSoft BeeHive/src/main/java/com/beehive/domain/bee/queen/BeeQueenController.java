package com.beehive.domain.bee.queen;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beehive.infrastructure.payload.ApiResponse;
import com.beehive.infrastructure.payload.BeeQueenDTO;
import com.beehive.infrastructure.payload.BeeQueenRequest;
import com.beehive.infrastructure.payload.ValueResponse;
import com.beehive.infrastructure.security.CurrentUser;
import com.beehive.infrastructure.security.UserPrincipal;


@RestController
@RequestMapping("/api/queen")
public class BeeQueenController {
	
	@Autowired
	BeeQueenService beeQueenService;
	
	@PostMapping("/new")
    public ResponseEntity<?> createHive(@Valid @RequestBody BeeQueenRequest beeQueenRequest) {
    	try {
    		beeQueenService.addBeeQueenToHive(beeQueenRequest);
    	}
        catch (Exception e) {
        	return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()),
        			HttpStatus.BAD_REQUEST);
		}
    	
        return ResponseEntity.ok(new ApiResponse(true, "Queen add to hive successfully"));
    }
	
	
    @GetMapping("/race")
    @PreAuthorize("hasRole('USER')")
    public List<ValueResponse> getAllQueenRace(@CurrentUser UserPrincipal currentUser){
    	return beeQueenService.getAllQueenRace();
    }
    
    @PutMapping("/modify")
    @PreAuthorize("hasRole('USER')")
    public BeeQueenDTO modifyQueen(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody BeeQueenDTO queenDTO) {
		BeeQueen queen = beeQueenService.getBeeQueenFromDatabase(queenDTO.getId());
		
		queen.setColor(queenDTO.getColor());
		queen.setDescription(queenDTO.getDescription());
		queen.setIsReproducting(queenDTO.getIsReproducting());
		
		queen = beeQueenService.modifyQueen(queen);
    	
    	return beeQueenService.mapBeeQueenToBeeQueenDTO(queen);
    }
    
}
