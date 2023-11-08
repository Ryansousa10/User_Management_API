package com.compassuol.sp.msusers.service;

import com.compassuol.sp.msusers.dto.UserDTO;
import com.compassuol.sp.msusers.exception.DuplicateCpfException;
import com.compassuol.sp.msusers.exception.DuplicateEmailException;
import com.compassuol.sp.msusers.exception.InvalidActiveValueException;
import com.compassuol.sp.msusers.model.User;
import com.compassuol.sp.msusers.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Validator validator;

    public UserDTO createUser(UserDTO userDTO) {
        Set<ConstraintViolation<UserDTO>> dtoViolations = validator.validate(userDTO);
        if (!dtoViolations.isEmpty()) {
        }

        Boolean active = userDTO.isActive();
        if (active != null && !active) {
            throw new InvalidActiveValueException("O campo 'active' deve conter somente valores 'true' ou 'false'.");
        }

        if (userRepository.existsByCpf(userDTO.getCpf())) {
            throw new DuplicateCpfException("CPF duplicado. Um usuário com o mesmo CPF já existe.");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DuplicateEmailException("Email duplicado. Já existe um usuário com o mesmo email.");

        }

        User user = userMapper.mapUserDTOToUser(userDTO);

        Set<ConstraintViolation<User>> entityViolations = validator.validate(user);
        if (!entityViolations.isEmpty()) {
        }

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        User savedUser = userRepository.save(user);

        return userMapper.mapUserToUserDTO(savedUser);
    }

    public UserDTO getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(userMapper::mapUserToUserDTO).orElse(null);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        Set<ConstraintViolation<UserDTO>> dtoViolations = validator.validate(userDTO);
        if (!dtoViolations.isEmpty()) {
        }

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();

            User updatedUser = userMapper.mapUserDTOToUser(userDTO);

            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setCpf(updatedUser.getCpf());
            existingUser.setBirthdate(updatedUser.getBirthdate());
            existingUser.setActive(updatedUser.isActive());

            Set<ConstraintViolation<User>> entityViolations = validator.validate(existingUser);
            if (!entityViolations.isEmpty()) {
            }

            User savedUser = userRepository.save(existingUser);

            return userMapper.mapUserToUserDTO(savedUser);
        } else {
            return null;
        }
    }

    public void updatePassword(Long id, String newPassword) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }
    }

    private boolean isValidBoolean(String value) {
        return "true".equals(value) || "false".equals(value);
    }

    public boolean isValidUser(String email, String password) {
        User user = (User) userRepository.findByEmail(email);

        if (user == null) {
            return false;
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            return true;
        }

        return false;
    }
}