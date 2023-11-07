package com.compassuol.sp.msusers.service;

import com.compassuol.sp.msusers.dto.PasswordDTO;
import com.compassuol.sp.msusers.dto.UserDTO;
import com.compassuol.sp.msusers.model.User;
import com.compassuol.sp.msusers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    public UserDTO createUser(UserDTO userDTO, PasswordDTO passwordDTO) {

        User user = userMapper.mapUserDTOToUser(userDTO);

        user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));

        User savedUser = userRepository.save(user);

        return userMapper.mapUserToUserDTO(savedUser);
    }

    public UserDTO getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(userMapper::mapUserToUserDTO).orElse(null);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
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

    public boolean isValidUser(String username, String password) {
        User user = userRepository.findByEmail(username);

        if (user == null) {
            return false;
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            return true;
        }

        return false;
    }
}