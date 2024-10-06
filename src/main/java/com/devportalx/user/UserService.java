package com.devportalx.user;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
     * @throws NoSuchAlgorithmException 
     */
    public ResponseEntity<Map<String, String>> createUser(Map<String, String> newUser) throws NoSuchAlgorithmException {
        Optional<User> userByEmail = userRepository.findUserByEmail(newUser.get("email"));
        Map<String, String> message = new HashMap<>();

        if (userByEmail.isPresent()) {
            message.put("message", "User already exists.");
            return new ResponseEntity<Map<String, String>>(message, HttpStatus.BAD_REQUEST);
        }
        if (newUser.get("password").length() == 0) {
            message.put("message", "User passed an empty password.");
            return new ResponseEntity<Map<String, String>>(message, HttpStatus.BAD_REQUEST);
        }
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedPassword = digest.digest(newUser.get("password").getBytes(StandardCharsets.UTF_8));

        User user = new User(newUser.get("email"), hashedPassword);
        userRepository.save(user);

        message.put("message", "User created successfully.");
        return new ResponseEntity<Map<String, String>>(message, HttpStatus.CREATED);
    }

    /**
     * Gets a user using the guid.
     * @param userGuid
     * @return User
     */
    public User getUserByGuid(UUID userGuid) {
        User userByGuid = userRepository.findUserByGuid(userGuid)
            .orElseThrow(() -> new IllegalStateException("User not found"));

        return userByGuid;
    }

    public void getAllUser() {
        System.out.println(userRepository.findAll());
    }

    /**
     * Logs in a user and returns info for dashboard.
     * @param loginData consisting of email and password
     * @return User
     */
    public User login(Map<String, String> loginData) throws NoSuchAlgorithmException {
        User userByEmail = userRepository.findUserByEmail(loginData.get("email"))
            .orElseThrow(() -> new IllegalStateException("Username or password is incorrect"));
        
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedPassword = digest.digest(loginData.get("password").getBytes(StandardCharsets.UTF_8));

        if (!MessageDigest.isEqual(userByEmail.getPassword(), hashedPassword)) {
            throw new IllegalStateException("Username or password is incorrect");
        }

        return userByEmail;
    }
}
