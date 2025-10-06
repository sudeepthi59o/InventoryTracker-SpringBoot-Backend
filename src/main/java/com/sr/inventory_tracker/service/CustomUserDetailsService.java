package com.sr.inventory_tracker.service;

import com.sr.inventory_tracker.CustomUserDetails;
import com.sr.inventory_tracker.model.User;
import com.sr.inventory_tracker.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(Objects.isNull(user))
        {
            throw new UsernameNotFoundException("Username not found");
        }
        return new CustomUserDetails(user);
    }
}
