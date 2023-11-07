package com.compassuol.sp.challenge03.dto;

import com.compassuol.sp.challenge03.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDTO {
private String firstName;
private String lastName;
private String cpf;
private String birthdate;
private String email;
private String password;
private boolean active;

    public User toUser() {
        return new User(this);
    }

}

