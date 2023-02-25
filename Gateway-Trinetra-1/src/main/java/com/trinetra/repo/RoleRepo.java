package com.trinetra.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.master.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {
    Boolean existsByName(String name);
    
    Role findByName(String name);

    @Query(value="select * from public.role_master r where r.name in (:roles)",nativeQuery = true)
    List<Role> findByRoleNames(@Param("roles") List<String> roles);
    
    @Query(value = "select * from role_master rm where not id in (21,:roleId)", nativeQuery = true)
    List<Role> getAllRoles(@Param("roleId") long roleId);
}
