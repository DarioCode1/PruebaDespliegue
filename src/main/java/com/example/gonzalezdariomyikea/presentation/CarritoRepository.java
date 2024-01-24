package com.example.gonzalezdariomyikea.presentation;

import com.example.gonzalezdariomyikea.models.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {
    @Query("SELECT MAX(c.id) FROM Carrito c")
    Optional<Integer> findMaxId();

}
