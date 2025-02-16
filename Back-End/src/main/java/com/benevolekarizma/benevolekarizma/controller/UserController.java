package com.benevolekarizma.benevolekarizma.controller;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.benevolekarizma.benevolekarizma.DTO.event.EventResponse;
import com.benevolekarizma.benevolekarizma.DTO.user.UpdateUserRequest;
import com.benevolekarizma.benevolekarizma.DTO.user.UserResponse;
import com.benevolekarizma.benevolekarizma.models.User;
import com.benevolekarizma.benevolekarizma.models.enums.Role;
import com.benevolekarizma.benevolekarizma.services.interfaces.EventServiceInterface;
import com.benevolekarizma.benevolekarizma.services.interfaces.OrganizationService;
import com.benevolekarizma.benevolekarizma.services.interfaces.UserServiceInterface;
import com.benevolekarizma.benevolekarizma.services.interfaces.VolunteerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing User entities.
 * Handles HTTP requests and routes them to the appropriate service methods.
 */
@RestController // Marks this class as a RESTful controller.
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Users", description = "APIs for managing user accounts and roles")
public class UserController {

    private final EventServiceInterface eventService;
    private final UserServiceInterface userService;
    private final VolunteerService volunteerService;
    private final OrganizationService organizationService;

    @Operation(summary = "Get current user info", description = "Retrieves information about the currently authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User information retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, user not authenticated")
    })
    @GetMapping("/@me")
    public ResponseEntity<UserResponse> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getByUserName(username);
        if (user.getRole() == Role.VOLUNTEER) {
            return new ResponseEntity<>(volunteerService.getByUserName(username), HttpStatus.OK);
        }
        return new ResponseEntity<>(organizationService.getByUserName(username), HttpStatus.OK);
    }

    @Operation(summary = "Get current volunteer events info", description = "Retrieves information about the currently subscribed events by the user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User information retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, user not authenticated")
    })
    @GetMapping("/@me/events")
    public ResponseEntity<List<EventResponse>> getVolunteerEvents() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getByUserName(username);
        if (user.getRole() != Role.VOLUNTEER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Access Denied: Only volunteers can access this endpoint.");
        }
        return new ResponseEntity<>(eventService.getVolunteerEvents(user), HttpStatus.OK);
    }

    @Operation(summary = "Update user information", description = "Updates the user information by their ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User role updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid role data provided"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUserRole(
            @Parameter(description = "Details of the updated role", required = true) @RequestBody @Valid UpdateUserRequest updateUserRequest,
            @Parameter(description = "ID of the user whose role is to be updated", required = true, in = ParameterIn.PATH) @PathVariable("id") Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getByUserName(username);

        if (user.getRole() == Role.VOLUNTEER) {
            return new ResponseEntity<>(volunteerService.updateVolunteer(userId, updateUserRequest), HttpStatus.OK);
        }
        return new ResponseEntity<>(organizationService.updateOrganization(userId, updateUserRequest), HttpStatus.OK);
    }

    @Operation(summary = "Delete user", description = "Deletes a user by their ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User role updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid role data provided"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserRole(
            @Parameter(description = "ID of the user whose role is to be updated", required = true, in = ParameterIn.PATH) @PathVariable("id") Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getByUserName(username);

        if (!username.equals(user.getUsername())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
