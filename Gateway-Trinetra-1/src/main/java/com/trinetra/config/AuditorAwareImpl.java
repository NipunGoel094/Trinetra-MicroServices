package com.trinetra.config;

import com.trinetra.model.entity.User;
import com.trinetra.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.AuditorAware;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class AuditorAwareImpl implements AuditorAware<Long> {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public Optional<Long> getCurrentAuditor() {
        String  name = getUsernameOfAuthenticatedUser();

        String sql = "SELECT * FROM Users WHERE username=" +"'"+name+"'";
        try {
            User user = jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(User.class));
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            return Optional.of(user.getId());
        }catch (IncorrectResultSizeDataAccessException e){
            e.printStackTrace();
        }
        return Optional.of(-1l);
    }

    private String getUsernameOfAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return  authentication.getName();
    }

}
