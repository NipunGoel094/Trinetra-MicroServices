package com.trinetra.service;


import com.trinetra.auth.UserDetailsImpl;
import com.trinetra.model.entity.User;
import com.trinetra.repo.UserRepository;

import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsService implements ReactiveUserDetailsService {

    private final UserRepository userRepository;
    
    @Autowired
    public UserDetailsService(UserRepository userRepository) {
    	this.userRepository = userRepository;
    }
    
    @Override
	public Mono<UserDetails> findByUsername(String username) throws UsernameNotFoundException{
    	User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    	return user == null ? Mono.empty() : Mono.just(UserDetailsImpl.build(user));
	}

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }
}
