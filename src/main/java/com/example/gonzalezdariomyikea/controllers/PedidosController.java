package com.example.gonzalezdariomyikea.controllers;

import com.example.gonzalezdariomyikea.models.Carrito;
import com.example.gonzalezdariomyikea.models.Pedido;
import com.example.gonzalezdariomyikea.models.ProductofferEntity;
import com.example.gonzalezdariomyikea.models.User;
import com.example.gonzalezdariomyikea.services.CarritoService;
import com.example.gonzalezdariomyikea.services.PedidoService;
import com.example.gonzalezdariomyikea.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@PreAuthorize("hasRole('USER')")
public class PedidosController {
    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private CarritoService carritoService;
    @Autowired
    private UserService userService;
    @GetMapping("/pedidos")
    public String listarPedidos(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return"/";
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        User user = userService.getUserById(email);
        model.addAttribute("pedidos", user.getPedidos());
        return "pedidos/index";
    }
    @PostMapping("/agregarPedido")
    public String AgregarPedido(@RequestParam int carritoId)
    {
        Optional<Carrito> carritoPedido = carritoService.getCarritoById(carritoId);
        Carrito carrito2 = carritoPedido.orElse(new Carrito());
        if (carritoPedido.isPresent()) {
            if(carrito2.getCosteTotal() == 0)
            {
                return "redirect:/carrito";
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
                return"/";
            }
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            User user = userService.getUserById(email);
            carrito2.setActivo(false);
            user.setCarrito(new Carrito());
            pedidoService.guardarPedidoParaUsuario(carrito2);
            return "redirect:/pedidos";
        } else {
            return "redirect:/carrito";
        }
    }
    @GetMapping("/pedidos/details/{id}")
    public String DetallesPedido(@PathVariable int id, Model model)
    {
        Pedido pedido = pedidoService.getPedidoById(id);
        if(pedido == null)
        {
            return "redirect:/pedidos";
        }
        model.addAttribute("pedido",pedido);
        return "pedidos/details";
    }
}
