package com.example.gonzalezdariomyikea.presentation;

import com.example.gonzalezdariomyikea.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
}
