package com.example.gonzalezdariomyikea.presentation;

import com.example.gonzalezdariomyikea.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
