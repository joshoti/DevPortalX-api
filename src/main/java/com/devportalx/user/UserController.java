package com.devportalx.user;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path = "api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "{userGuid}")
    public User getUserByGuid(@PathVariable(name = "userGuid") UUID userGuid) {
        return userService.getUserByGuid(userGuid);
    }
    
    @GetMapping
    public void getAllUsers() {
        userService.getAllUser();
    }

    @PostMapping(path = "register")
    public ResponseEntity<Map<String, String>> createUser(@RequestBody Map<String, String> newUser)
        throws NoSuchAlgorithmException {
        return userService.createUser(newUser);
    }
    
    @PostMapping(path = "login")
    public User login(@RequestBody Map<String, String> loginData)
        throws NoSuchAlgorithmException {
        return userService.login(loginData);
    }
    
}
