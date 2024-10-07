package com.devportalx.user;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

/**
 * Tests that the routing works and checks the HTTP status code because the
 * UserServiceTester's integration tests already check that the correct response
 * is returned from each of the methods.
 * 
 * TO BE CONTINUED: when DB persists data
 */
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTester {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    private static String commonTestEmail;
    private static String commonTestPassword;
    private static User testUser;
    private static Map<String, String> testUserMap;

    @BeforeAll
    public static void setUp() throws NoSuchAlgorithmException {
        commonTestEmail = "john.doe@gmail.com";
        commonTestPassword = "password";

        testUserMap = new HashMap<String, String>();
        testUserMap.put("email", commonTestEmail);
        testUserMap.put("password", commonTestPassword);

        testUser = new User(commonTestEmail, commonTestPassword);
    }

    @Test
    public void testGetUserById() throws Exception {
        userRepository.save(testUser);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/" + testUser.getGuid()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testUserMap.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testLogin() throws Exception {
        testUser = userRepository.save(testUser);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testUserMap.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}