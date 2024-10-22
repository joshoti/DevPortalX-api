package com.devportalx.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * UserService is the Service layer for the user package, which has the helper
 * methods for operations performed on the User. It is injected into the
 * Controller layer.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Checks if email is already present in the db and creates user or fails.
     * 
     * @param newUser map containing keys "email" and "password"
     * @return JSON message indicating success or failure with the corresponding
     *         status code
     * @throws NoSuchAlgorithmException
     */
    public ResponseEntity<Map<String, String>> createUser(Map<String, String> newUser) throws NoSuchAlgorithmException {
        Optional<User> userByEmail = userRepository.findUserByEmail(newUser.get("email"));
        Map<String, String> message = new HashMap<>();

        if (userByEmail.isPresent()) {
            message.put("message", UserMessage.USER_ALREADY_EXISTS);
            return new ResponseEntity<Map<String, String>>(message, HttpStatus.BAD_REQUEST);
        }
        if (newUser.get("password").length() == 0) {
            message.put("message", UserMessage.INVALID_PASSWORD);
            return new ResponseEntity<Map<String, String>>(message, HttpStatus.BAD_REQUEST);
        }
        User user = new User(newUser.get("email"), newUser.get("password"));
        userRepository.save(user);

        message.put("message", UserMessage.USER_CREATED_SUCCESS);
        return new ResponseEntity<Map<String, String>>(message, HttpStatus.CREATED);
    }

    /**
     * Gets a user using the guid.
     * 
     * @param userGuid of type UUID
     * @return User
     */
    public User getUserByGuid(UUID userGuid) {
        User userByGuid = userRepository.findUserByGuid(userGuid)
                .orElseThrow(() -> new IllegalStateException(UserMessage.USER_NOT_FOUND));

        return userByGuid;
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    /**
     * Logs in a user and returns user info.
     * 
     * @param loginData consisting of email and password
     * @return User
     */
    public User login(Map<String, String> loginData) throws NoSuchAlgorithmException {
        User userByEmail = userRepository.findUserByEmail(loginData.get("email"))
                .orElseThrow(() -> new IllegalStateException(UserMessage.INCORRECT_USERNAME_OR_PASSWORD));

        if (!MessageDigest.isEqual(userByEmail.getUserPassword(), User.getHashedDigest(loginData.get("password")))) {
            throw new IllegalStateException(UserMessage.INCORRECT_USERNAME_OR_PASSWORD);
        }

        return userByEmail;
    }

    public void logout(String refreshToken) {
        //Delete tokens from DB
    }
}
