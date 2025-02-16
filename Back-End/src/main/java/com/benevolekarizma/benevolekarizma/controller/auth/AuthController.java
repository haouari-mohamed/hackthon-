package com.benevolekarizma.benevolekarizma.controller.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.benevolekarizma.benevolekarizma.DTO.auth.AuthResponse;
import com.benevolekarizma.benevolekarizma.DTO.auth.LoginRequest;
import com.benevolekarizma.benevolekarizma.DTO.organization.OrganizationRequest;
import com.benevolekarizma.benevolekarizma.DTO.user.UserResponse;
import com.benevolekarizma.benevolekarizma.DTO.volunteer.VolunteerRequest;
import com.benevolekarizma.benevolekarizma.config.jwt.JWTGenerator;
import com.benevolekarizma.benevolekarizma.models.User;
import com.benevolekarizma.benevolekarizma.models.enums.Role;
import com.benevolekarizma.benevolekarizma.services.interfaces.OrganizationService;
import com.benevolekarizma.benevolekarizma.services.interfaces.UserServiceInterface;
import com.benevolekarizma.benevolekarizma.services.interfaces.VolunteerService;
import com.benevolekarizma.benevolekarizma.validation.UserValidationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing authentication routes.
 * Handles HTTP requests and routes them to the appropriate service methods.
 */
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
@Tag(name = "Authentication", description = "APIs for user authentication and account management")
public class AuthController {
    private final UserValidationService userValidationService;
    private final OrganizationService organizationService;
    private final UserServiceInterface userService;
    private final VolunteerService volunteerService;
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator tokenGenerator;
    private final HttpServletRequest request;

    @Operation(summary = "Login user", description = "Authenticates a user and generates a JWT token.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully authenticated"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Parameter(description = "Login credentials: username and password", required = true) @RequestBody @Valid LoginRequest loginRequest) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword());

        WebAuthenticationDetails details = new WebAuthenticationDetails(request);
        authenticationToken.setDetails(details);

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserResponse userResponse = null;
        String username = authentication.getName();
        User user = userService.getByUserName(username);
        if (user.getRole() == Role.VOLUNTEER) {
            userResponse = volunteerService.getByUserName(username);
        } else {
            userResponse = organizationService.getByUserName(username);
        }
        String token = tokenGenerator.generateToken(authentication);

        return new ResponseEntity<>(new AuthResponse(token, userResponse), HttpStatus.OK);
    }

    @Operation(summary = "Register a new organization user", description = "Registers a new user with the provided details.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully registered"),
            @ApiResponse(responseCode = "400", description = "Invalid user data or validation error")
    })
    @PostMapping("/register/organization")
    public ResponseEntity<String> register(
            @Parameter(description = "User registration details: username, email, password, and role", required = true) @RequestBody @Valid OrganizationRequest organizationRequest) {

        if (userValidationService.isUsernameTaken(organizationRequest.getUsername())) {
            log.info("Username is already taken");
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        if (userValidationService.isEmailTaken(organizationRequest.getEmail())) {
            log.info("Email is already taken");
            return ResponseEntity.badRequest().body("Email is already taken");
        }

        organizationService.addOrganization(organizationRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @Operation(summary = "Register a new volunteer", description = "Registers a new user with the provided details.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully registered"),
            @ApiResponse(responseCode = "400", description = "Invalid user data or validation error")
    })
    @PostMapping("/register/volunteer")
    public ResponseEntity<String> register(
            @Parameter(description = "User registration details: username, email, password, and role", required = true) @RequestBody @Valid VolunteerRequest volunteerRequest) {

        if (userValidationService.isUsernameTaken(volunteerRequest.getUsername())) {
            log.info("Username is already taken");
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        if (userValidationService.isEmailTaken(volunteerRequest.getEmail())) {
            log.info("Email is already taken");
            return ResponseEntity.badRequest().body("Email is already taken");
        }

        volunteerService.addVolunteer(volunteerRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

}
