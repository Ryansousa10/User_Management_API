package com.compassuol.sp.challenge03.service;

import com.compassuol.sp.challenge03.exception.DuplicateEntityException;
import com.compassuol.sp.challenge03.model.User;
import com.compassuol.sp.challenge03.repository.UserRepository;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        validateUser(user);

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new DuplicateEntityException("Email já está em uso");
        }
        if (userRepository.findByCpf(user.getCpf()) != null) {
            throw new DuplicateEntityException("CPF já está em uso");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long userId, User updatedUser) {
        User existingUser = findUserById(userId);
        validateUser(updatedUser);
        return userRepository.save(updatedUser);
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User changeUserPassword(Long userId, String newPassword) {
        User existingUser = findUserById(userId);
        validatePassword(newPassword);
        existingUser.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(existingUser);
    }

    private void validateUser(User user) {
        if (user.getFirstName() == null || user.getFirstName().length() < 3) {
            throw new ValidationException("O campo first_name deve ter no mínimo 3 caracteres");
        }

        if (user.getLastName() == null || user.getLastName().length() < 3) {
            throw new ValidationException("O campo last_name deve ter no mínimo 3 caracteres");
        }

        if (user.getCpf() == null || !user.getCpf().matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            throw new ValidationException("O campo cpf deve seguir o padrão xxx.xxx.xxx-xx");
        }

        if (user.getBirthdate() == null) {
            throw new ValidationException("O campo birthdate é obrigatório");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String iso8601Birthdate = dateFormat.format(user.getBirthdate());

        String iso8601Pattern = "\\d{4}-\\d{2}-\\d{2}";
        if (!iso8601Birthdate.matches(iso8601Pattern)) {
            throw new ValidationException("O campo birthdate deve estar no formato ISO-8601 (AAAA-MM-DD)");
        }

        if (user.getEmail() == null || !user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidationException("O campo email precisa estar no formato de um email válido");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new ValidationException("O campo password deve ter no mínimo 6 caracteres");
        }

        if (user.isActive() != true && user.isActive() != false) {
            throw new ValidationException("O campo active deve ser um valor booleano");
        }
    }

    private void validatePassword(String newPassword) {
        if (newPassword == null || newPassword.length() < 6) {
            throw new ValidationException("O campo password deve ter no mínimo 6 caracteres");
        }
    }
}
