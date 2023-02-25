package com.trinetra.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.RtoResponseView;

@Repository
public interface RtoResponseViewRepo extends JpaRepository<RtoResponseView, Integer>{

	@Query(value="select * from rto_response_view where state_id=?1",nativeQuery = true)
	public List<RtoResponseView> getRto(Integer stateId);
}
