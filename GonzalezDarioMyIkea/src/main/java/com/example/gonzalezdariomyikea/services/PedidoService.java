package com.example.gonzalezdariomyikea.services;

import com.example.gonzalezdariomyikea.models.Carrito;
import com.example.gonzalezdariomyikea.models.Pedido;
import com.example.gonzalezdariomyikea.models.ProvinciasEntity;
import com.example.gonzalezdariomyikea.models.User;
import com.example.gonzalezdariomyikea.presentation.PedidoRepository;
import com.example.gonzalezdariomyikea.presentation.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private UserRepository userRepository;
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }
    public Pedido agregarPedido(Carrito carrito)
    {
        Pedido pedido = new Pedido();
        pedido.setCarrito(carrito);
        pedido.setFecha(new Date());
        return pedidoRepository.save(pedido);
    }
    public Pedido getPedidoById(int id) {
        return pedidoRepository.findById(id).orElse(null);
    }
    public void guardarPedidoParaUsuario(Carrito carrito) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email);
        Pedido pedido = new Pedido();
        pedido.setCarrito(carrito);
        pedido.setFecha(new Date());
        user.getPedidos().add(pedido);
        pedidoRepository.save(pedido);
        userRepository.save(user);
    }
}
