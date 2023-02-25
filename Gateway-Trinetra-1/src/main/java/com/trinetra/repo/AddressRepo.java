package com.trinetra.repo;

import com.trinetra.model.entity.Address;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<Address,Long> {
	
	@Query(value = "select * from address_master where address1 = :address1 limit 1", nativeQuery = true)
	public Optional<Address> getAddressByAddress1(@Param("address1") String address1);
}
