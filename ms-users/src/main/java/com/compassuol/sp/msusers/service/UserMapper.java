package com.compassuol.sp.msusers.service;

import com.compassuol.sp.msusers.dto.UserDTO;
import com.compassuol.sp.msusers.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
@Component
public class UserMapper {

    public UserDTO mapUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setCpf(user.getCpf());
        userDTO.setBirthdate(user.getBirthdate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        userDTO.setActive(user.isActive());
        return userDTO;
    }

    public static User mapUserDTOToUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setCpf(userDTO.getCpf());
        user.setBirthdate(LocalDate.parse(userDTO.getBirthdate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        user.setActive(userDTO.isActive());
        return user;
    }
}