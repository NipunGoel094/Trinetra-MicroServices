package com.trinetra.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trinetra.model.useraccess.entity.MenuMaster;

@Repository
public interface MenuMasterRepo extends JpaRepository<MenuMaster, Integer> {

	public List<MenuMaster> findByUserTypeId(int userTypeId);
}
