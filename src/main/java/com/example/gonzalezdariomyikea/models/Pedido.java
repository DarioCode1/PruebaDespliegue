package com.example.gonzalezdariomyikea.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "carrito_id", referencedColumnName = "id")
    private Carrito carrito;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
}
