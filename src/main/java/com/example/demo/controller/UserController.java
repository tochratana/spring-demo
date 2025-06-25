package com.example.demo.controller;

import com.example.demo.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    // In-memory storage (for demo purposes)
    private List<User> users = new ArrayList<>();
    private Long nextId = 1L;

    // Initialize with some sample data
    @PostConstruct
    public void init() {
        users.add(new User(nextId++, "John Doe", "john@example.com"));
        users.add(new User(nextId++, "Jane Smith", "jane@example.com"));
        users.add(new User(nextId++, "Bob Johnson", "bob@example.com"));
    }

    // TODO GET all users
    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved users",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)))
    })
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(users);
    }

    // TODO GET user by id
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve a specific user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> getUserById(
            @Parameter(description = "User ID", required = true) @PathVariable Long id) {
        Optional<User> user = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    // TODO CREATE new user
    @PostMapping
    @Operation(summary = "Create new user", description = "Create a new user with provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<User> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User object to be created", required = true,
                    content = @Content(schema = @Schema(implementation = User.class)))
            @RequestBody User user) {
        user.setId(nextId++);
        users.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }


    // TODO UPDATE user
    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Update an existing user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> updateUser(
            @Parameter(description = "User ID", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated user object", required = true,
                    content = @Content(schema = @Schema(implementation = User.class)))
            @RequestBody User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                updatedUser.setId(id);
                users.set(i, updatedUser);
                return ResponseEntity.ok(updatedUser);
            }
        }
        return ResponseEntity.notFound().build();
    }


    // TODO DELETE user
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Delete a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "User ID", required = true) @PathVariable Long id) {
        boolean removed = users.removeIf(user -> user.getId().equals(id));
        if (removed) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    // TODO SEARCH user
    @GetMapping("/search")
    @Operation(summary = "Search users by name", description = "Find users by name (case-insensitive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)))
    })
    public ResponseEntity<List<User>> searchUsersByName(
            @Parameter(description = "Name to search for", required = true)
            @RequestParam String name) {
        List<User> foundUsers = users.stream()
                .filter(user -> user.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(foundUsers);
    }

    @GetMapping("/count")
    @Operation(summary = "Get user count", description = "Get the total number of users")
    public ResponseEntity<Map<String, Integer>> getUserCount() {
        Map<String, Integer> response = new HashMap<>();
        response.put("count", users.size());
        return ResponseEntity.ok(response);
    }
}

