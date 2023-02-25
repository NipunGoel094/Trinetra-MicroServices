package com.trinetra.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trinetra.model.entity.DistrictMasterView;
import com.trinetra.model.entity.RtoResponseView;
import com.trinetra.model.entity.StateMasterView;
import com.trinetra.repo.DistrictMasterViewRepo;
import com.trinetra.repo.DropDownControllerRepo;
import com.trinetra.repo.RtoResponseViewRepo;


@Service
public class DropDownControllerService {

	@Autowired
	private DropDownControllerRepo dropDownControllerRepo;

	@Autowired
	private RtoResponseViewRepo rtoResponseViewRepo;
	
	@Autowired
	private DistrictMasterViewRepo districtMasterViewRepo;
	
	public List<StateMasterView> getStates(String stateId) {
		List<StateMasterView> data = new ArrayList<StateMasterView>();
		if(!stateId.equalsIgnoreCase("All")){
			data=dropDownControllerRepo.getStateFromStateMaster(stateId);
		}else {
			data=dropDownControllerRepo.getStateFromStateMaster();
			
		}
		return data;
	}

	public List<RtoResponseView> getRtoByStateId(Integer stateId) {

		List<RtoResponseView> list = rtoResponseViewRepo.getRto(stateId);
		return list;
	}
	
	public List<DistrictMasterView> getDistrictListByStateId(int stateId) {
		return districtMasterViewRepo.getDistrictListByStateId(stateId);
	}

}
