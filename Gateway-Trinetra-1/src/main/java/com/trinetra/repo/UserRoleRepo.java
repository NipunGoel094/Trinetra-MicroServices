package com.trinetra.repo;

import com.trinetra.model.entity.mapping.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole,Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "insert into users_roles (user_id,role_id) values (:user_id,:role_id)",
    nativeQuery = true)
    int updateRole(@Param("user_id") Long user_d,
                    @Param("role_id") Long role_id);
    @Modifying
    @Query(value = "delete from public.users_roles where user_id=:userId", nativeQuery = true)
	void deleteByUserId(@Param("userId") long userId);
	
    @Query(value = "select * from users_roles ur where user_id =:userId", nativeQuery = true)
    UserRole findByUserId(@Param("userId") int userId);
    

//    @Modifying(clearAutomatically = true)
//    @Query(value = "INSERT INTO UserRole u (u.user_id,u.role_id) values (:user_id,:role_id)")
//    int updateRoleByUsername(@Param("user_id") Long user_id,
//                             @Param("role_id") Long role_id);
//    //Update User u set u.roles = :role where u.id = :id

}
