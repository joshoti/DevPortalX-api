package com.devportalx.user;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * UserController is the Controller layer for the user package, which is
 * responsible for routing requests. It is injected into the Controller layer.
 */
@RestController
@RequestMapping(path = "api/users")
@Tag(name = "User")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
        description = "Gets a developer using the GUID.",
        summary = "Gets a developer using the GUID.",
        responses = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", content = { @Content(mediaType = "application/json", examples = @ExampleObject(value = "User not found."))}),
        }
    )
    @GetMapping(path = "{userGuid}")
    public User getUserByGuid(@PathVariable(name = "userGuid") UUID userGuid) {
        return userService.getUserByGuid(userGuid);
    }

    @GetMapping
    @Hidden
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }

    @Operation(
        description = "Registers a developer.",
        summary = "Registers a developer.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(examples = {
                @ExampleObject(value="{\"email\": \"someEmail\", \"password\": \"somePassword\", \"confirmPassword\": \"somePassword\" }")}
            )
        ),
        responses = {
            @ApiResponse(responseCode = "201", content = { @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"User created successfully.\"}"))}),
            @ApiResponse(responseCode = "400", content = { @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"message\": \"User already exists.\"}"))}),
        }
    )
    @PostMapping(path = "register")
    public ResponseEntity<Map<String, String>> createUser(@RequestBody Map<String, String> newUser)
            throws NoSuchAlgorithmException {
        return userService.createUser(newUser);
    }

    @Operation(
        description = "Login a developer.",
        summary = "Login a developer.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(examples = {
                @ExampleObject(value="{\"email\": \"someEmail\", \"password\": \"somePassword\"}")}
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
        }
    )
    @PostMapping(path = "login")
    public User login(@RequestBody Map<String, String> loginData)
            throws NoSuchAlgorithmException {
        return userService.login(loginData);
    }

    @Operation(
        description = "Logout developer.",
        summary = "Logout developer.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(examples = {
                @ExampleObject(value="{}")}
            )
        ),
        responses = {
            @ApiResponse(responseCode = "204"),
        }
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(path = "logout")
    public void logout(@RequestBody String refreshToken) {
        // Take in the refresh token
        userService.logout(refreshToken);
    }

}
