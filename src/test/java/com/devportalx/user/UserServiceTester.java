package com.devportalx.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
public class UserServiceTester {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private static User commonTestUser;
    private static String commonTestEmail;
    private static String commonTestPassword;
    private static byte[] hashedPassword;

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

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        hashedPassword = digest.digest(commonTestPassword.getBytes(StandardCharsets.UTF_8));

        commonTestUser = new User(commonTestEmail, hashedPassword);
    }

    @Test
    public void testCreateUser() throws NoSuchAlgorithmException {
        /**
         * Testing user creation with an empty password
         * Response should contain BAD_REQUEST with the appropriate failed message.
         */
        var response = userService.createUser(badTestUserMap);

        Map<String, String> emptyPasswordMessage = new HashMap<>();
        emptyPasswordMessage.put("message", "User passed an empty password.");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(emptyPasswordMessage, response.getBody());

        /**
         * Testing good user creation
         * Response should contain CREATED with the appropriate success message.
         */
        response = userService.createUser(commonTestUserMap);

        Map<String, String> successMessage = new HashMap<>();
        successMessage.put("message", "User created successfully.");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(successMessage, response.getBody());

        /**
         * Testing user creation when the email already exists
         * Response should contain BAD_REQUEST with the appropriate failed message.
         */
        response = userService.createUser(commonTestUserMap);
        Map<String, String> alreadyExistsMessage = new HashMap<>();
        alreadyExistsMessage.put("message", "User created successfully.");
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
            assertEquals("Username or password is incorrect", e.getMessage());
            loginSucceeded = false;
        } finally {
            assertEquals(false, loginSucceeded);
        }

        /**
         * Testing login when user exists in db
         * TO BE FIXED: db not persisting. user0 is null
         */
        User user0 = userRepository.save(commonTestUser);
        User userLogin = userService.login(commonTestUserMap);

        assertEquals(commonTestEmail, userLogin.getEmail());
        assertEquals(LocalDate.now(), userLogin.getDateJoined());
        assertNotEquals(null, userLogin.getGuid());

        /**
         * Testing login with correct email and wrong password
         */
        loginSucceeded = true;

        try {
            userService.login(badTestUserMap);
        } catch (Exception e) {
            assertEquals("Username or password is incorrect", e.getMessage());
            loginSucceeded = false;
        } finally {
            assertEquals(false, loginSucceeded);
        }
    }

    @Test
    public void testGetUserByGuid() {
        // User user = new User(1L, "John Doe");
        // when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // User foundUser = userService.getUserById(1L);

        // assertEquals("John Doe", foundUser.getName());
        // verify(userRepository, times(1)).findById(1L);
    }

}
