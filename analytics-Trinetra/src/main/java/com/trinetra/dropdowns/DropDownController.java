package com.trinetra.dropdowns;

import static com.trinetra.model.enums.Status.SUCCESS;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trinetra.model.entity.DistrictMasterView;
import com.trinetra.model.entity.RtoResponseView;
import com.trinetra.model.entity.StateMasterView;
import com.trinetra.model.wrapper.ResponseWrapper;
import com.trinetra.service.DropDownControllerService;

@RestController
@RequestMapping("/api/v1/dropdown")
public class DropDownController {
	
	@Autowired
	public DropDownControllerService dropDownControllerService;

	@GetMapping("/statename/States")
	public ResponseEntity<ResponseWrapper> getStateFromStateMaster(@RequestParam(required = false, value = "stateId") String stateId) {

		List<StateMasterView> list = dropDownControllerService.getStates(stateId);
		return new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name, list), HttpStatus.OK);
	}

	@GetMapping("/getRto/{stateId}")
	public ResponseEntity<ResponseWrapper> getData(@PathVariable Integer stateId) {
		
		List <RtoResponseView> list= new ArrayList<RtoResponseView>();	    
		list=dropDownControllerService.getRtoByStateId(stateId);
		return  new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name,list),HttpStatus.OK);
	}
	
	@GetMapping("/getDistrictListByStateId")
	public ResponseEntity<ResponseWrapper> getDistrictListByStateId(@RequestParam("stateId")int stateId){
		List<DistrictMasterView> districtList=dropDownControllerService.getDistrictListByStateId(stateId);
		return  new ResponseEntity<ResponseWrapper>(new ResponseWrapper(SUCCESS.name,districtList),HttpStatus.OK);

	}
}
