package com.example.gonzalezdariomyikea.services;

import com.example.gonzalezdariomyikea.models.*;
import com.example.gonzalezdariomyikea.presentation.CarritoRepository;
import com.example.gonzalezdariomyikea.presentation.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarritoService {
    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private UserRepository userRepository;

    public Optional<Carrito> getCarritoById(int id) {
        return carritoRepository.findById(id);
    }
    public Carrito saveCarrito(Carrito carrito) {
        return carritoRepository.save(carrito);
    }
    public void agregarProductoAlCarrito(int carritoId, ProductofferEntity producto) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseGet(() -> {
                    Carrito nuevoCarrito = new Carrito();
                    nuevoCarrito.getProductos().add(producto);
                    nuevoCarrito.setCosteTotal(nuevoCarrito.getCosteTotal()+producto.getProductPrice());
                    return carritoRepository.save(nuevoCarrito);
                });
        carrito.getProductos().add(producto);
        carrito.setCosteTotal(carrito.getCosteTotal()+producto.getProductPrice());
        carritoRepository.save(carrito);
    }

    public void eliminarProductoDelCarrito(int carritoId, int productoId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        ProductofferEntity productoAEliminar = carrito.getProductos()
                .stream()
                .filter(producto -> producto.getProductId() == productoId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Producto no encontrado en el carrito"));

        carrito.setCosteTotal(carrito.getCosteTotal() - productoAEliminar.getProductPrice());

        carrito.getProductos().remove(productoAEliminar);
        carritoRepository.save(carrito);
    }
    public Integer obtenerUltimoCarritoId() {
        return carritoRepository.findMaxId().orElse(0);
    }
    public Carrito getCarritoActivoDelUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return null;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email);
        if (user != null && user.getCarrito() != null && user.getCarrito().isActivo()) {
            return user.getCarrito();
        }
        return null;
    }

}
