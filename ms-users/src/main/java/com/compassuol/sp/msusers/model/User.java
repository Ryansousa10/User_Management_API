package com.compassuol.sp.msusers.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, message = "Os campos firstName e lastName devem ter pelo menos 3 caracteres.")
    @Column(nullable = false, length = 50)
    private String firstName;

    @Size(min = 3, message = "Os campos firstName e lastName devem ter pelo menos 3 caracteres.")
    @Column(nullable = false, length = 50)
    private String lastName;

    @Email(message = "O email deve ter um formato válido.")
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @CPF(message = "O CPF deve ser válido")
    @Column(unique = true, nullable = false, length = 14)
    private String cpf;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean active;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

