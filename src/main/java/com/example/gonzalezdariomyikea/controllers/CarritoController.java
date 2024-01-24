package com.example.gonzalezdariomyikea.controllers;

import com.example.gonzalezdariomyikea.models.ProductofferEntity;
import com.example.gonzalezdariomyikea.models.Carrito;
import com.example.gonzalezdariomyikea.services.CarritoService;
import com.example.gonzalezdariomyikea.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@PreAuthorize("hasRole('USER')")
public class CarritoController {
    @Autowired
    private CarritoService carritoService;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/carrito")
    public String listarCarrito(Model model)
    {
        Carrito carrito = carritoService.getCarritoActivoDelUsuarioAutenticado();
        if (carrito != null) {
            model.addAttribute("carrito", carrito);
            return "carrito/index";
        } else {
            return "redirect:/productos";
        }
    }
    @PostMapping("/carrito")
    public String agregarProductoAlCarrito(@RequestParam int productoId) {
        int carritoId = carritoService.obtenerUltimoCarritoId();
        if(carritoId == 0)
        {
            carritoService.saveCarrito(new Carrito());
            carritoId++;
        }
        Optional<Carrito> carrito = carritoService.getCarritoById(carritoId);
        Carrito carrito2 = carrito.orElse(new Carrito());
        if(!carrito2.isActivo())
        {
            carritoService.saveCarrito(new Carrito());
        }
        carritoId = carritoService.obtenerUltimoCarritoId();
        try {
            Optional<ProductofferEntity> productoOpt = productoService.findById(productoId);
            if (productoOpt.isPresent()) {
                carritoService.agregarProductoAlCarrito(carritoId, productoOpt.get());
                return "redirect:/carrito";
            } else {
                return "redirect:/productos";
            }
        } catch (Exception e) {
            return "redirect:/productos";
        }
    }
    @PostMapping("/eliminarProductoCarrito")
    public String eliminarProductoDelCarrito(@RequestParam int productoId) {
        try {
            carritoService.eliminarProductoDelCarrito(carritoService.obtenerUltimoCarritoId(), productoId);
            return "redirect:/carrito";
        } catch (Exception e) {
            return "redirect:/carrito";
        }
    }
}
