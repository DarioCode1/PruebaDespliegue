package com.example.gonzalezdariomyikea.presentation;

import com.example.gonzalezdariomyikea.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByNombre(String nombre);
}
