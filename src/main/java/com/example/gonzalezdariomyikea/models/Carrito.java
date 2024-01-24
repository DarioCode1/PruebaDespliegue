package com.example.gonzalezdariomyikea.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToMany
    @JoinTable(
            name = "carrito_producto",
            joinColumns = @JoinColumn(name = "carrito_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<ProductofferEntity> productos = new ArrayList<>();
    private double costeTotal;
    private boolean activo = true;
}
