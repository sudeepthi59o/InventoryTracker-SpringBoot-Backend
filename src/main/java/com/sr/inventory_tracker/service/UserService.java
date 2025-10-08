package com.sr.inventory_tracker.service;

import com.sr.inventory_tracker.error.UsernameExistsException;
import com.sr.inventory_tracker.DTO.TokenDTO;
import com.sr.inventory_tracker.model.User;
import com.sr.inventory_tracker.DTO.UserDTO;
import com.sr.inventory_tracker.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public TokenDTO verify(UserDTO userDTO) {
        log.info("Inside login method");

        User user = fromDTOToEntity(userDTO);

        user.setRole(userRepository.findRoleByUserName(userDTO.getUserName()));

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return new TokenDTO(jwtService.generateToken(user), user.getRole(), "Authentication successful");
        }

        return new TokenDTO(null,null,"Authentication Failed");
    }

    public String register(UserDTO userDTO) throws UsernameExistsException {
        log.info("Inside register method");

        if (userRepository.existsByUserName(userDTO.getUserName())) {
            log.warn("Registration failed. Username {} already exists.", userDTO.getUserName());

            throw new UsernameExistsException("Username already exists");
        }

        User user = fromDTOToEntity(userDTO);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "User saved successfully";
    }

    public User fromDTOToEntity(UserDTO userDTO) {
        return User.builder()
                .userName(userDTO.getUserName())
                .password(userDTO.getPassword())
                .role(userDTO.getRole())
                .build();
    }
}
