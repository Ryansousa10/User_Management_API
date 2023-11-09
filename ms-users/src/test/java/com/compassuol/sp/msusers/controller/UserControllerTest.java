package com.compassuol.sp.msusers.controller;

import com.compassuol.sp.msusers.dto.LoginRequestDTO;
import com.compassuol.sp.msusers.dto.LoginResponseDTO;
import com.compassuol.sp.msusers.dto.UserDTO;
import com.compassuol.sp.msusers.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        UserDTO userDTO = new UserDTO();
        when(userService.createUser(userDTO)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.createUser(userDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    public void testLogin() {
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        LoginResponseDTO loginResponse = new LoginResponseDTO("token", "Bearer", "username");
        when(userService.login(loginRequest)).thenReturn(new ResponseEntity<>(loginResponse, HttpStatus.OK));

        ResponseEntity<LoginResponseDTO> response = userController.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loginResponse, response.getBody());
    }

    @Test
    public void testGetUser() {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        when(userService.getUserById(userId)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.getUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    public void testUpdateUser() {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        when(userService.updateUser(userId, userDTO)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.updateUser(userId, userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
    }

    @Test
    public void testUpdatePassword() {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        doNothing().when(userService).updatePassword(userId, userDTO.getPassword());

        ResponseEntity<Void> response = userController.updatePassword(userId, userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
