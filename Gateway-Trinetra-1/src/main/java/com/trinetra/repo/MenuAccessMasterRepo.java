package com.trinetra.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trinetra.model.useraccess.entity.MenuAccessMaster;

@Repository
public interface MenuAccessMasterRepo extends JpaRepository<MenuAccessMaster, Integer> {

	public MenuAccessMaster findByUserTypeId(int userTypeId);
}
