package com.compassuol.sp.msusers.service;

import com.compassuol.sp.msusers.dto.LoginRequestDTO;
import com.compassuol.sp.msusers.dto.LoginResponseDTO;
import com.compassuol.sp.msusers.dto.UserDTO;
import com.compassuol.sp.msusers.exception.*;
import com.compassuol.sp.msusers.model.User;
import com.compassuol.sp.msusers.repository.UserRepository;
import com.compassuol.sp.msusers.util.JwtTokenProvider;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @Mock
    private Validator validator;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUserWithValidData() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setPassword("password");
        userDTO.setCpf("123.456.789-01");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setActive(true);

        User user = new User();
        when(userRepository.save(any(User.class))).thenReturn(user);

        when(userMapper.mapUserToUserDTO(user)).thenReturn(userDTO);

        UserDTO createdUser = userService.createUser(userDTO);

        assertNotNull(createdUser);
        assertEquals(userDTO, createdUser);
    }

    @Test
    public void testGetUserById() {
        Long userId = 1L;
        User user = new User();
        UserDTO userDTO = new UserDTO();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.mapUserToUserDTO(user)).thenReturn(userDTO);

        UserDTO retrievedUser = userService.getUserById(userId);

        assertNotNull(retrievedUser);
        assertEquals(userDTO, retrievedUser);
    }

    @org.junit.Test(expected = InvalidCpfFormatException.class)
    public void testCreateUserWithNullCpf() {
        UserDTO userDTO = new UserDTO();
        // Set other valid fields
        userDTO.setCpf(null); // Simulate a null CPF

        userService.createUser(userDTO);
    }

    @Test
    public void testUpdatePassword() {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("newHashedPassword");

        userService.updatePassword(userId, userDTO.getPassword());

        verify(userRepository, times(1)).save(user);
        assertEquals("newHashedPassword", user.getPassword());
    }

    @Test
    public void testLogin() {
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        User user = new User();
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtTokenProvider.generateToken(loginRequest.getEmail())).thenReturn("token");

        ResponseEntity<LoginResponseDTO> response = userService.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testIsValidUserValidCredentials() {
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

        UserDetails userDetails = userService.isValidUser(email, password);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
    }

    @Test
    public void testIsValidUserInvalidCredentials() {
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode("differentPassword"));
        when(userRepository.findByEmail(email)).thenReturn(user);

        assertThrows(UsernameNotFoundException.class, () -> userService.isValidUser(email, password));
    }

    @Test
    public void testValidateUserDTOValidDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setPassword("password");
        when(validator.validate(userDTO)).thenReturn(Set.of());

        assertDoesNotThrow(() -> userService.validateUserDTO(userDTO));
    }

    @Test
    public void testValidateUserDTOInvalidDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("J");
        when(validator.validate(userDTO)).thenReturn(Set.of(Mockito.mock(ConstraintViolation.class)));

        assertThrows(InvalidUserDTOException.class, () -> userService.validateUserDTO(userDTO));
    }

    @Test
    public void testValidateNameLengthValid() {
        String validName = "John";
        assertDoesNotThrow(() -> userService.validateNameLength(validName, "fieldName"));
    }

    @Test
    public void testValidateNameLengthInvalid() {
        String invalidName = "J";
        assertThrows(InvalidNameLengthException.class, () -> userService.validateNameLength(invalidName, "fieldName"));
    }

    @Test
    public void testValidatePasswordLengthValid() {
        String validPassword = "123456";
        assertDoesNotThrow(() -> userService.validatePasswordLength(validPassword));
    }

    @Test
    public void testValidatePasswordLengthInvalid() {
        String invalidPassword = "12345";
        assertThrows(InvalidPasswordLengthException.class, () -> userService.validatePasswordLength(invalidPassword));
    }

    @Test
    public void testValidateUniqueCpfValid() {
        String cpf = "123.456.789-09";
        when(userRepository.existsByCpf(cpf)).thenReturn(false);
        assertDoesNotThrow(() -> userService.validateUniqueCpf(cpf));
    }

    @Test
    public void testValidateUniqueCpfInvalid() {
        String cpf = "123.456.789-09";
        when(userRepository.existsByCpf(cpf)).thenReturn(true);
        assertThrows(DuplicateCpfException.class, () -> userService.validateUniqueCpf(cpf));
    }

    @Test
    public void testValidateCpfFormatValid() {
        String validCpf = "123.456.789-09";
        assertDoesNotThrow(() -> userService.validateCpfFormat(validCpf));
    }

    @Test
    public void testValidateCpfFormatInvalid() {
        String invalidCpf = "12345678909"; // Formato inválido
        assertThrows(InvalidCpfFormatException.class, () -> userService.validateCpfFormat(invalidCpf));
    }

    @Test
    public void testValidateUniqueEmailValid() {
        String email = "test@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(false);
        assertDoesNotThrow(() -> userService.validateUniqueEmail(email));
    }

    @Test
    public void testValidateUniqueEmailInvalid() {
        String email = "test@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);
        assertThrows(DuplicateEmailException.class, () -> userService.validateUniqueEmail(email));
    }

    @Test
    public void testValidateActiveValueValid() {
        Boolean validActive = true;
        assertDoesNotThrow(() -> userService.validateActiveValue(validActive));
    }

    @Test
    public void testValidateActiveValueInvalid() {
        Boolean invalidActive = null; // Valor inválido
        assertThrows(InvalidActiveValueException.class, () -> userService.validateActiveValue(invalidActive));
    }

    @Test
    public void testIsCPFInFormatValid() {
        String validCpf = "123.456.789-09";
        assertTrue(UserService.isCPFInFormat(validCpf));
    }

    @Test
    public void testIsCPFInFormatInvalid() {
        String invalidCpf = "12345678909"; // Formato inválido
        assertFalse(UserService.isCPFInFormat(invalidCpf));
    }
}