package com.compassuol.sp.challenge03.repository;

import com.compassuol.sp.challenge03.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByCpf(String cpf);
}



