package com.example.gonzalezdariomyikea.presentation;

import com.example.gonzalezdariomyikea.models.ProductofferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoRepository extends JpaRepository<ProductofferEntity, Integer> {
}
