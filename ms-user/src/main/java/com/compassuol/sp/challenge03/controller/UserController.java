package com.compassuol.sp.challenge03.controller;

import com.compassuol.sp.challenge03.dto.CreateUserDTO;
import com.compassuol.sp.challenge03.dto.LoginRequestDTO;
import com.compassuol.sp.challenge03.dto.PasswordChangeRequest;
import com.compassuol.sp.challenge03.model.User;
import com.compassuol.sp.challenge03.repository.UserRepository;
import com.compassuol.sp.challenge03.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserDTO createUser) {
        if (userRepository.findByEmail(createUser.getEmail()) != null) {
            return ResponseEntity.badRequest().build();
        }

        User newUser = new User(createUser);
        User savedUser = userService.createUser(newUser);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok("Usuário autenticado com sucesso.");
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/users/{id}/password")
    public ResponseEntity<User> changeUserPassword(@PathVariable Long id, @RequestBody @Valid PasswordChangeRequest passwordChangeRequest) {
        User updatedUser = userService.changeUserPassword(id, String.valueOf(passwordChangeRequest));
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
