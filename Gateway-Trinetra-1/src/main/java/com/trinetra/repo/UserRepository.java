package com.trinetra.repo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trinetra.model.entity.User;
import com.trinetra.model.page.response.UserDetailsReponse;
import com.trinetra.model.page.response.UserDetailsVO;
import com.trinetra.model.page.response.UserRolesVO;
import com.trinetra.model.page.response.UserTypeListResponse;
import com.trinetra.model.page.response.UsernameListResponse;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findBymobileNumber(String mobileNo);

    User findByResetPasswordToken(String token);

    Boolean existsByUsername(String username);

    Boolean existsById(long id);

    Boolean existsByEmail(String email);

    void deleteByUsername(String username);

//    @Query(value = "delete from User u where u.id = :id")
//    @Transactional
//    @Modifying
    void deleteById( Long id);

    @Modifying(clearAutomatically = true)
    @Query(value = "Update User u set u.roles = :role where u.id = :id")
    int updateRoleByUsername(@Param("role") String role,
                             @Param("id") Long id);

    @Query(value = "SELECT * FROM users",
            countQuery = "SELECT count(*) FROM users",
            nativeQuery = true)
    Page<User> getAllUser(Pageable pageable);
    
    @Query(value = "SELECT U.ID USERID, TM.ID ROLEID, TM.DESCRIPTION USERTYPE FROM PUBLIC.USERS U LEFT JOIN "
    		+ "PUBLIC.TYPE_MASTER TM ON U.USER_TYPE_ID = TM.ID WHERE LOWER(U.USERNAME) =:userName "
    		+ "AND(TM.DESCRIPTION <> 'SUPERADMIN' OR U.USER_TYPE_ID IS NULL)",
            nativeQuery = true)
    UserRolesVO findUserByUsername(@Param("userName") String userName);
    
    @Query(value = "SELECT U.ID ID, TEXTCAT(TEXTCAT(U.FIRSTNAME, ' '), U.LASTNAME) NAME, U.USERNAME USERLOGIN, "
    		+ "U.EMAIL_ID EMAIL, U.CONTACT PHONENO, RM.NAME ROLE, U.IS_ACTIVE ACTIVE FROM USERS U LEFT JOIN "
    		+ "PUBLIC.TYPE_MASTER TM ON U.USER_TYPE_ID = TM.ID LEFT JOIN PUBLIC.USERS_ROLES UR ON U.ID = UR.USER_ID LEFT "
    		+ "JOIN PUBLIC.ROLE_MASTER RM ON UR.ROLE_ID = RM.ID WHERE U.CREATED_BY=:userId",
            countQuery = "SELECT count(*) FROM USERS U LEFT JOIN \r\n"
            		+ "PUBLIC.TYPE_MASTER TM ON U.USER_TYPE_ID = TM.ID LEFT JOIN PUBLIC.USERS_ROLES UR ON U.ID = UR.USER_ID LEFT \r\n"
            		+ "JOIN PUBLIC.ROLE_MASTER RM ON UR.ROLE_ID = RM.ID WHERE U.CREATED_BY=:userId",
            nativeQuery = true)
    Page<UserDetailsVO> findAllByOtherLoggedInUser(Pageable pageable,@Param("userId") int userId);
    
    @Query(value = "SELECT U.ID ID, TEXTCAT(TEXTCAT(U.FIRSTNAME, ' '), U.LASTNAME) NAME, U.USERNAME USERLOGIN, "
    		+ "U.EMAIL_ID EMAIL, U.CONTACT PHONENO, RM.NAME ROLE, U.IS_ACTIVE ACTIVE FROM USERS U LEFT JOIN "
    		+ "PUBLIC.TYPE_MASTER TM ON U.USER_TYPE_ID = TM.ID LEFT JOIN PUBLIC.USERS_ROLES UR ON U.ID = UR.USER_ID LEFT "
    		+ "JOIN PUBLIC.ROLE_MASTER RM ON UR.ROLE_ID = RM.ID",
            countQuery = "SELECT count(*) FROM USERS U LEFT JOIN \r\n"
            		+ "PUBLIC.TYPE_MASTER TM ON U.USER_TYPE_ID = TM.ID LEFT JOIN PUBLIC.USERS_ROLES UR ON U.ID = UR.USER_ID LEFT \r\n"
            		+ "JOIN PUBLIC.ROLE_MASTER RM ON UR.ROLE_ID = RM.ID",
            nativeQuery = true)
    Page<UserDetailsVO> findAllBySuperUser(Pageable pageable);
    
    @Query(value = "SELECT U.ID ID, TEXTCAT(TEXTCAT(U.FIRSTNAME, ' '), U.LASTNAME) NAME, U.USERNAME USERLOGIN, "
    		+ "U.EMAIL_ID EMAIL, U.CONTACT PHONENO, RM.NAME ROLE, U.IS_ACTIVE ACTIVE FROM USERS U LEFT "
    		+ "JOIN PUBLIC.TYPE_MASTER TM ON U.USER_TYPE_ID = TM.ID LEFT JOIN PUBLIC.USERS_ROLES UR ON U.ID = UR.USER_ID"
    		+ " LEFT JOIN PUBLIC.ROLE_MASTER RM ON UR.ROLE_ID = RM.ID WHERE U.CREATED_BY=:userId AND "
    		+ "(LOWER(TEXTCAT(TEXTCAT(U.FIRSTNAME, ' '), U.LASTNAME)) like %:inputText% OR LOWER(U.USERNAME) "
    		+ "like %:inputText% OR LOWER(U.EMAIL_ID) like %:inputText% OR LOWER(U.CONTACT) like %:inputText% "
    		+ "OR LOWER(RM.NAME) like %:inputText%)",
            countQuery = "SELECT count(*) FROM USERS U LEFT "
            		+ "JOIN PUBLIC.TYPE_MASTER TM ON U.USER_TYPE_ID = TM.ID LEFT JOIN PUBLIC.USERS_ROLES UR ON U.ID = UR.USER_ID"
            		+ " LEFT JOIN PUBLIC.ROLE_MASTER RM ON UR.ROLE_ID = RM.ID WHERE U.CREATED_BY=:userId AND "
            		+ "(LOWER(TEXTCAT(TEXTCAT(U.FIRSTNAME, ' '), U.LASTNAME)) like %:inputText% OR LOWER(U.USERNAME) "
            		+ "like %:inputText% OR LOWER(U.EMAIL_ID) like %:inputText% OR LOWER(U.CONTACT) like %:inputText% "
            		+ "OR LOWER(RM.NAME) like %:inputText%)",
            nativeQuery = true)
    Page<UserDetailsVO> findByFilterWithOtherLoggedInUser(Pageable pageable, @Param("inputText") String inputText, @Param("userId") int userId);
    
    @Query(value = "SELECT U.ID ID, TEXTCAT(TEXTCAT(U.FIRSTNAME, ' '), U.LASTNAME) NAME, U.USERNAME USERLOGIN,"
    		+ " U.EMAIL_ID EMAIL, U.CONTACT PHONENO, RM.NAME ROLE, U.IS_ACTIVE ACTIVE FROM USERS U LEFT JOIN "
    		+ "PUBLIC.TYPE_MASTER TM ON U.USER_TYPE_ID = TM.ID LEFT JOIN PUBLIC.USERS_ROLES UR ON U.ID = UR.USER_ID LEFT "
    		+ "JOIN PUBLIC.ROLE_MASTER RM ON UR.ROLE_ID = RM.ID WHERE LOWER(TEXTCAT(TEXTCAT(U.FIRSTNAME, ' '), U.LASTNAME)) "
    		+ "like %:inputText% OR LOWER(U.USERNAME) like %:inputText% OR LOWER(U.EMAIL_ID) like %:inputText% OR "
    		+ "LOWER(U.CONTACT) like %:inputText% OR LOWER(RM.NAME) like %:inputText%",
            countQuery = "SELECT count(*) FROM USERS U LEFT JOIN "
            		+ "PUBLIC.TYPE_MASTER TM ON U.USER_TYPE_ID = TM.ID LEFT JOIN PUBLIC.USERS_ROLES UR ON U.ID = UR.USER_ID LEFT "
            		+ "JOIN PUBLIC.ROLE_MASTER RM ON UR.ROLE_ID = RM.ID WHERE LOWER(TEXTCAT(TEXTCAT(U.FIRSTNAME, ' '), U.LASTNAME)) "
            		+ "like %:inputText% OR LOWER(U.USERNAME) like %:inputText% OR LOWER(U.EMAIL_ID) like %:inputText% OR "
            		+ "LOWER(U.CONTACT) like %:inputText% OR LOWER(RM.NAME) like %:inputText%",
            nativeQuery = true)
    Page<UserDetailsVO> findByFilterWithSuperUser(Pageable pageable, @Param("inputText")String inputText);

    @Query(value="select distinct(tm.id)id,(tm.description)userType from users u \r\n"
    		+ "inner join type_master tm on u.user_type_id =tm.id \r\n"
    		+ "inner join address_master am on u.address_id =am.id \r\n"
    		+ "inner join pincode_master pm on am.pincode_id =pm.id\r\n"
    		+ "inner join state_master sm on pm.state_id=sm.id\r\n"
    		+ "where sm.id=?1 group by tm.id,tm.description ",nativeQuery = true)
	List<UserTypeListResponse> getUserTypeListBasedOnStateId(int stateId);

    @Query(value="select (u.id)id,(u.username)username from users u \r\n"
    		+ "inner join type_master tm on u.user_type_id =tm.id \r\n"
    		+ "inner join address_master am on u.address_id =am.id \r\n"
    		+ "inner join pincode_master pm on am.pincode_id =pm.id\r\n"
    		+ "inner join state_master sm on pm.state_id=sm.id\r\n"
    		+ "where sm.id=?1 and u.user_type_id=?2 and u.is_active = true",nativeQuery = true)
	List<UsernameListResponse> getUsernameListByStateIdAndUserTypeId(int stateId, int userTypeId);

    @Query(value="select (u.id)id,(u.firstname)firstName,(u.lastname)lastName,(u.email_id)emailId,(u.contact)phoneNo,(tm.description)userRole from users u \r\n"
    		+ "inner join type_master tm on u.user_type_id =tm.id \r\n"
    		+ "inner join address_master am on u.address_id =am.id \r\n"
    		+ "inner join pincode_master pm on am.pincode_id =pm.id\r\n"
    		+ "inner join state_master sm on pm.state_id=sm.id\r\n"
    		+ "where sm.id=?1 and u.user_type_id=?2 and u.id=?3",nativeQuery = true)
	List<UserDetailsReponse> getUserDetails(int stateId, int userTypeId, int userId);
    
    @Transactional
    @Modifying
    @Query(value = "update users set password = :newPassword, updated_date = cast(now() as timestamptz), updated_by = :loggedInUserId where id = :userId", nativeQuery = true)
    void updatePassword(@Param("loggedInUserId") long loggedInUserId, @Param("userId") int userId, @Param("newPassword") String newPassword);

	boolean existsByUsernameIgnoreCase(String name);

	boolean existsByEmailIgnoreCase(String email);
    
}
