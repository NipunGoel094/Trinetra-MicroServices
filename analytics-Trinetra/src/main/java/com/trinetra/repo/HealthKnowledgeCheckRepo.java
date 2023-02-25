package com.trinetra.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.HealthKnowledgeCheck;

@Repository
public interface HealthKnowledgeCheckRepo extends JpaRepository<HealthKnowledgeCheck, Integer>{

	@Query(value = "select * from view_health_knowledge_check where deviceimei like %:text% order by receiveddatetime desc", nativeQuery = true)
    public Page<HealthKnowledgeCheck> getDeviceHealthKnowledgeCheckDetailsByImei(@Param("text") String text, Pageable pageable);
}
