package com.compassuol.sp.msusers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import com.compassuol.sp.msusers.dto.UserDTO;
import com.compassuol.sp.msusers.model.User;
import org.junit.jupiter.api.Test;

public class UserMapperTest {

    @Test
    public void testMapUserToUserDTO() {
        // Criar um usuário para teste
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setCpf("12345678901");
        user.setBirthdate(LocalDate.of(1990, 1, 15));
        user.setActive(true);

        UserMapper mapper = new UserMapper();
        UserDTO userDTO = mapper.mapUserToUserDTO(user);

        assertEquals(user.getId(), ((UserDTO) userDTO).getId());
        assertEquals(user.getFirstName(), userDTO.getFirstName());
        assertEquals(user.getLastName(), userDTO.getLastName());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getCpf(), userDTO.getCpf());
        assertEquals(user.getBirthdate(), userDTO.getBirthdate());
        assertEquals(user.isActive(), userDTO.isActive());
    }
}

