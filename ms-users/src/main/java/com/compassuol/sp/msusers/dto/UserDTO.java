package com.compassuol.sp.msusers.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String cpf;
    private String birthdate; // Representação em formato "dd/mm/aaaa"
    private boolean active;
}

