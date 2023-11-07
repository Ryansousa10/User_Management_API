package com.compassuol.sp.msusers.controller;

import com.compassuol.sp.msusers.dto.LoginRequestDTO;
import com.compassuol.sp.msusers.dto.LoginResponseDTO;
import com.compassuol.sp.msusers.dto.PasswordDTO;
import com.compassuol.sp.msusers.dto.UserDTO;
import com.compassuol.sp.msusers.service.UserService;
import com.compassuol.sp.msusers.util.JwtTokenProvider;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO, @RequestBody PasswordDTO passwordDTO) {
        UserDTO createdUser = userService.createUser(userDTO, passwordDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        if (userService.isValidUser(loginRequest.getUsername(), loginRequest.getPassword())) {
            String token = jwtTokenProvider.generateToken(loginRequest.getUsername());
            String tokenType = "Bearer";
            String username = loginRequest.getUsername();

            LoginResponseDTO responseDTO = new LoginResponseDTO(token, tokenType, username);

            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        if (userDTO != null) {
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/users/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody PasswordDTO passwordDTO) {
        userService.updatePassword(id, passwordDTO.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

