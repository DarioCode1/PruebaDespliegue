package com.example.gonzalezdariomyikea.services;

import com.example.gonzalezdariomyikea.models.CustomerEntity;
import com.example.gonzalezdariomyikea.models.ProductofferEntity;
import com.example.gonzalezdariomyikea.presentation.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Primary
@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    public List<ProductofferEntity> findAll() {
        return productoRepository.findAll();
    }

    public ProductofferEntity save(ProductofferEntity producto) {
        return productoRepository.save(producto);
    }

    public Optional<ProductofferEntity> findById(int id) {
        return productoRepository.findById(id);
    }

    public void deleteById(int id) {
        productoRepository.deleteById(id);
    }
}
