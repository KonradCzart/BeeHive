package com.beehive.domain.hive;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beehive.infrastructure.payload.ApiResponse;
import com.beehive.infrastructure.payload.HiveDTO;
import com.beehive.infrastructure.payload.HiveRequest;
import com.beehive.infrastructure.payload.ValueResponse;
import com.beehive.infrastructure.security.CurrentUser;
import com.beehive.infrastructure.security.UserPrincipal;

@RestController
@RequestMapping("/api/hive")
public class HiveController {
	
	@Autowired
	HiveService hiveService;
	
	@Autowired
	HiveTypeService hiveTypeService;
	
    @PostMapping("/new")
    public ResponseEntity<?> createHive(@Valid @RequestBody HiveRequest hiveRequest) {

    	    	
    	try {
    		hiveService.createHive(hiveRequest);
    	}
        catch (Exception e) {
        	return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()),
        			HttpStatus.BAD_REQUEST);
		}
    	
        return ResponseEntity.ok(new ApiResponse(true, "Hive created Successfully"));
    }
    
    
    @GetMapping("/type")
    @PreAuthorize("hasRole('USER')")
    public List<ValueResponse> getAllHiveType(@CurrentUser UserPrincipal currentUser){  	
    	return hiveService.getAllHiveType();
    }
    
    @DeleteMapping("/delete/{hiveId}/queen")
    @PreAuthorize("hasRole('USER')")
    public  ResponseEntity<?> deleteQueenWithHive(@CurrentUser UserPrincipal currentUser, @PathVariable Long hiveId){
    	
    	try {
    		hiveService.deleteQueenWithHive(hiveId);
    	}
        catch (Exception e) {
        	return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()),
        			HttpStatus.BAD_REQUEST);
		}
    	
    	return ResponseEntity.ok(new ApiResponse(true, "Queen delete successfully"));
    }
    
    @DeleteMapping("/delete/{hiveId}/all")
    @PreAuthorize("hasRole('USER')")
    public  ResponseEntity<?> deleteHive(@CurrentUser UserPrincipal currentUser, @PathVariable Long hiveId){
    	
    	try {
    		hiveService.deleteHive(hiveId);
    	}
        catch (Exception e) {
        	return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()),
        			HttpStatus.BAD_REQUEST);
		}
    	
    	return ResponseEntity.ok(new ApiResponse(true, "Hive delete successfully"));
    }
    
    @PutMapping("/modify")
    @PreAuthorize("hasRole('USER')")
    public  ResponseEntity<?> modifyHive(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody HiveDTO hiveDTO) {
    	
    	try {
    		Hive hive = hiveService.getHiveFromDatabase(hiveDTO.getId());		
    		HiveType type = hiveTypeService.getHiveTypeFromDatabase(hiveDTO.getTypeName());
    		
    		hive.setBoxNumber(hiveDTO.getBoxNumber());
    		hive.setName(hiveDTO.getName());
    		hive.setHiveType(type);
    		
    		hive = hiveService.modifyHive(hive);
    		hiveDTO = hiveService.mapHiveToHiveDTO(hive);
    	}
        catch (Exception e) {
        	return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()),
        			HttpStatus.BAD_REQUEST);
		}
    	
    	return ResponseEntity.ok(hiveDTO);
    }
}
