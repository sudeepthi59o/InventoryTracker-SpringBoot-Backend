package com.sr.inventory_tracker.service;

import com.sr.inventory_tracker.error.UsernameExistsException;
import com.sr.inventory_tracker.model.User;
import com.sr.inventory_tracker.model.UserDTO;
import com.sr.inventory_tracker.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public String verify(UserDTO userDTO) {
        log.info("Inside login method");

        User user = fromDTOToEntity(userDTO);

        if (userRepository.existsByUserName(userDTO.getUserName())) {
            return "success";
        }

        return "failure";
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
