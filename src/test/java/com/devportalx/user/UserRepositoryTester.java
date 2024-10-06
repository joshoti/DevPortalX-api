package com.devportalx.user;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryTester {

    @Mock
    private UserRepository userRepository;

    private static String commonTestEmail;
    private static UUID commonTestGuid;

    @BeforeAll
    public static void setUp() throws NoSuchAlgorithmException {
        commonTestEmail = "nonexistentuser@gmail.com";
        commonTestGuid = UUID.randomUUID();
    }

    /**
     * Testing that the query runs and returns empty. Getting users stored in DB is
     * checked in integration test in UserServiceTester
     */
    @Test
    public void testFindUserByEmail() {
        Optional<User> response = userRepository.findUserByEmail(commonTestEmail);
        assertTrue(response.isEmpty());
    }

    /**
     * Testing that the query runs and returns empty. Getting users stored in DB is
     * checked in integration test in UserServiceTester
     */
    @Test
    public void testFindUserByGuid() {
        Optional<User> response = userRepository.findUserByGuid(commonTestGuid);
        assertTrue(response.isEmpty());
    }
}
