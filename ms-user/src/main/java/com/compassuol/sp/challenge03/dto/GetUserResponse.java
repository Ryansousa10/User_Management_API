package com.compassuol.sp.challenge03.dto;

import lombok.Data;

@Data
public class GetUserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String cpf;
    private String birthdate;
    private String email;
    private boolean active;
}
