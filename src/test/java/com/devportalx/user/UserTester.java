package com.devportalx.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserTester {

    private static String commonTestEmail;
    private static String commonTestPassword;

    @BeforeAll
    public static void setUp() {
        commonTestEmail = "james@gmail.com";
        commonTestPassword = "password";
    }

    /**
     * Tests the constructors, getters and setters. Ensuring that preconditions are
     * observed.
     * 
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void testUser() throws NoSuchAlgorithmException {
        /**
         * Checking that constructor is called correctly, password gets hashed, and
         * non-Null values for dateJoined and guid
         */
        User user = new User(commonTestEmail, commonTestPassword);
        assertEquals(commonTestEmail, user.getEmail());
        assertNotEquals(commonTestPassword, user.getPassword());
        assertTrue(MessageDigest.isEqual(User.getHashedDigest(commonTestPassword), user.getPassword()));
        assertNotNull(user.getDateJoined());
        assertNotNull(user.getGuid());

        /**
         * Checking that email gets updated
         */
        user.setEmail("test@example.com");
        assertNotEquals(commonTestEmail, user.getEmail());

        /**
         * Ensuring that invalid passwords don't get set
         */
        user.setPassword("");
        assertNotEquals("", user.getPassword());
        assertFalse(MessageDigest.isEqual(User.getHashedDigest(""), user.getPassword()));

        /**
         * Ensuring the valid passwords get hashed and set
         */
        user.setPassword("newPassword");
        assertNotEquals("newPassword", user.getPassword());
        assertTrue(MessageDigest.isEqual(User.getHashedDigest("newPassword"), user.getPassword()));
    }

}
