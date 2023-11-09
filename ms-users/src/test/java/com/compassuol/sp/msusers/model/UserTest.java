package com.compassuol.sp.msusers.model;

import com.compassuol.sp.msusers.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserTest {

    @Test
    public void testGettersAndSetters() {
        User user = new User();

        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        user.setCpf("123.456.789-00");
        user.setBirthdate(LocalDate.of(1990, 1, 1));
        user.setPassword("password123");
        user.setActive(true);

        assertEquals(1L, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("johndoe@example.com", user.getEmail());
        assertEquals("123.456.789-00", user.getCpf());
        assertEquals(LocalDate.of(1990, 1, 1), user.getBirthdate());
        assertEquals("password123", user.getPassword());
        assertEquals(true, user.isActive());
    }

    @Test
    public void testUserDetailsMethods() {
        User user = new User();
        user.setEmail("johndoe@example.com");

        assertEquals("johndoe@example.com", user.getUsername());
        assertEquals(true, user.isAccountNonExpired());
        assertEquals(true, user.isAccountNonLocked());
        assertEquals(true, user.isCredentialsNonExpired());
        assertEquals(true, user.isEnabled());
    }
}

