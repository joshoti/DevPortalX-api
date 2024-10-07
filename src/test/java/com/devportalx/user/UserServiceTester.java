package com.devportalx.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class UserServiceTester {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private static User commonTestUser;
    private static String commonTestEmail;
    private static String commonTestPassword;

    private static Map<String, String> badTestUserMap;
    private static Map<String, String> commonTestUserMap;

    @BeforeAll
    public static void setUp() throws NoSuchAlgorithmException {
        // Create the user object once before all tests
        commonTestEmail = "james@gmail.com";
        commonTestPassword = "password";

        commonTestUserMap = new HashMap<>();
        commonTestUserMap.put("email", commonTestEmail);
        commonTestUserMap.put("password", commonTestPassword);

        badTestUserMap = new HashMap<>();
        badTestUserMap.put("email", commonTestEmail);
        badTestUserMap.put("password", "");

        commonTestUser = new User(commonTestEmail, commonTestPassword);
    }

    @Test
    public void testCreateUser() throws NoSuchAlgorithmException {
        /**
         * Testing user creation with an empty password
         * Response should contain BAD_REQUEST with the appropriate failed message.
         */
        var response = userService.createUser(badTestUserMap);

        Map<String, String> emptyPasswordMessage = new HashMap<>();
        emptyPasswordMessage.put("message", UserMessage.INVALID_PASSWORD);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(emptyPasswordMessage, response.getBody());

        /**
         * Testing good user creation
         * Response should contain CREATED with the appropriate success message.
         */
        response = userService.createUser(commonTestUserMap);

        Map<String, String> successMessage = new HashMap<>();
        successMessage.put("message", UserMessage.USER_CREATED_SUCCESS);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(successMessage, response.getBody());

        /**
         * Testing user creation when the email already exists
         * Response should contain BAD_REQUEST with the appropriate failed message.
         * 
         * TO BE FIXED: DB NOT PERSISTING
         */
        response = userService.createUser(commonTestUserMap);
        Map<String, String> alreadyExistsMessage = new HashMap<>();
        alreadyExistsMessage.put("message", UserMessage.USER_ALREADY_EXISTS);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(alreadyExistsMessage, response.getBody());
    }

    @Test
    public void testLogin() throws NoSuchAlgorithmException {
        /**
         * Testing login when user doesn't exist in db
         */
        boolean loginSucceeded = true;
        try {
            userService.login(commonTestUserMap);
        } catch (Exception e) {
            assertEquals(UserMessage.INCORRECT_USERNAME_OR_PASSWORD, e.getMessage());
            loginSucceeded = false;
        } finally {
            assertEquals(false, loginSucceeded);
        }

        /**
         * Testing login when user exists in db
         * 
         * TO BE FIXED: db not persisting. savedUser is null
         */
        User savedUser = userRepository.save(commonTestUser);
        assertNotNull(savedUser);
        User userLogin = userService.login(commonTestUserMap);

        assertEquals(commonTestEmail, userLogin.getEmail());
        assertEquals(LocalDate.now(), userLogin.getDateJoined());
        assertNotNull(userLogin.getGuid());

        /**
         * Testing login with correct email and wrong password
         */
        loginSucceeded = true;

        try {
            userService.login(badTestUserMap);
        } catch (Exception e) {
            assertEquals(UserMessage.INCORRECT_USERNAME_OR_PASSWORD, e.getMessage());
            loginSucceeded = false;
        } finally {
            assertEquals(false, loginSucceeded);
        }
    }

    @Test
    public void testGetUserByGuid() throws NoSuchAlgorithmException {
        /**
         * Getting a user that is not in DB. Search result should be empty
         */
        Optional<User> userByGuid = userRepository.findUserByGuid(UUID.randomUUID());
        assertTrue(userByGuid.isEmpty());
        
        /**
         * Testing getting a user in DB
         * 
         * TO BE FIXED: db not persisting. savedUser is null
         */
        User savedUser = userRepository.save(commonTestUser);
        assertNotNull(savedUser);
        
        userByGuid = userRepository.findUserByGuid(savedUser.getGuid());
        assertTrue(userByGuid.isPresent());
    }

}
