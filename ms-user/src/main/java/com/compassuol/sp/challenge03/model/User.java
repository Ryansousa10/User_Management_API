package com.compassuol.sp.challenge03.model;

import com.compassuol.sp.challenge03.dto.CreateUserDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, message = "O campo firstName deve ter no mínimo 3 caracteres")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Size(min = 3, message = "O campo lastName deve ter no mínimo 3 caracteres")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "O campo cpf deve seguir o padrão xxx.xxx.xxx-xx")
    @Column(name = "cpf", unique = true, nullable = false)
    private String cpf;

    @NotNull(message = "O campo birthdate é obrigatório")
    @Column(name = "birthdate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthdate;

    @Email(message = "O campo email precisa estar no formato de um email válido")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Size(min = 6, message = "O campo password deve ter no mínimo 6 caracteres")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(@Valid CreateUserDTO createUser) {
        // Construtor vazio
    }

    public User(String firstName, String lastName, String cpf, Date birthdate, String email, String password, boolean active, UserRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
        this.active = active;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
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
        return active;
    }
}
