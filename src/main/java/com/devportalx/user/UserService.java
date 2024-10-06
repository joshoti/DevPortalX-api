package com.devportalx.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Checks if email is already present in the db and creates user or fails.
     * @param newUser
     * @return JSON message indicating success or failure with the corresponding status code
     */
    public ResponseEntity<Map<String, String>> createUser(User newUser) {
        Optional<User> userByEmail = userRepository.findUserByEmail(newUser.getEmail());
        Map<String, String> message = new HashMap<>();

        if (userByEmail.isPresent()) {
            message.put("message", "User already exists.");
            return new ResponseEntity<Map<String, String>>(message, HttpStatus.BAD_REQUEST);
        }
        userRepository.save(newUser);

        message.put("message", "User created successfully.");
        return new ResponseEntity<Map<String, String>>(message, HttpStatus.CREATED);
    }
}
