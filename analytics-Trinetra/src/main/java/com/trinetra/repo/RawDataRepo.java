package com.trinetra.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.RawData;

@Repository
public interface RawDataRepo extends JpaRepository<RawData, Long>{

	@Query(value ="SELECT * FROM raw_data dl where dl.created_at  > now() - interval '24 hours' order by dl.created_at desc",nativeQuery = true)
	 public Page<RawData> getAllRawData(Pageable pageable);


	//@Query(value="select * from raw_data dl where dl.raw_data like %:imei%", nativeQuery= true)
@Query(value = "select * from raw_data dl where dl.raw_data like %:imei% and dl.created_at  > now() - interval '24 hours' order by created_at desc limit 1",nativeQuery= true)
	public Object[][] fingByImei(@Param("imei")String imei);

@Query(value="select * from raw_data dl where dl.raw_data like %:imei% order by dl.created_at desc limit 5000", nativeQuery= true)
public Object[][] getRawDataByImei(String imei);
 
@Query(value = "select * from raw_data dl where dl.raw_data like %:imei% and dl.log_date  > now() - interval '24 hours' order by dl.created_at desc limit 1",nativeQuery= true)
public Object[][] fingByImeiNo(@Param("imei")String imei);

@Query(value="select * from raw_data dl where dl.log_date::::character varying like %:input% or dl.raw_data like %:input% \r\n"
		+ "order by dl.log_date  desc",nativeQuery = true)
public Page<RawData> getRawDataBasedOnImeiOrDate(String input, Pageable pageable);
}
