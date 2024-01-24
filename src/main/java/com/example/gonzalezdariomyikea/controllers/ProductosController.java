package com.example.gonzalezdariomyikea.controllers;

import com.example.gonzalezdariomyikea.models.CustomerEntity;
import com.example.gonzalezdariomyikea.models.MunicipiosEntity;
import com.example.gonzalezdariomyikea.models.ProductofferEntity;
import com.example.gonzalezdariomyikea.models.ProvinciasEntity;
import com.example.gonzalezdariomyikea.services.MunicipiosService;
import com.example.gonzalezdariomyikea.services.ProductoService;
import com.example.gonzalezdariomyikea.services.ProvinciasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@PreAuthorize("isAuthenticated()")
public class ProductosController {
    @Autowired
    private ProductoService productoService;
    @Autowired
    private MunicipiosService municipiosService;
    @Autowired
    private ProvinciasService provinciasService;
    @GetMapping("/productos")
    public String listarProducto(Model model) {
        List<ProductofferEntity> producto = productoService.findAll();
        model.addAttribute("productos", producto);
        return "producto/index";
    }
    @GetMapping("/productos/details/{id}")
    public String details(@PathVariable int id, Model model) {
        Optional<ProductofferEntity> producto = productoService.findById(id);
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            return "producto/details";
        } else {
            return "redirect:/productos";
        }
    }
    @GetMapping("/productos/create")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MAGANER')")
    public String mostrarFormularioCreacion(Model model)
    {
        List<MunicipiosEntity> municipios = municipiosService.getAllMunicipios();
        List<ProvinciasEntity> provincias = provinciasService.getAllProvincias();
        model.addAttribute("producto", new ProductofferEntity());
        model.addAttribute("municipios",municipios);
        model.addAttribute("provincias", provincias);
        return "producto/create";
    }
    @PostMapping("/productos/create")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MAGANER')")
    public String crearProducto(@ModelAttribute("producto") ProductofferEntity producto,
                                @RequestParam("imagen")MultipartFile imagen)
    {
        if (!imagen.isEmpty()) {
            try {
                String uniqueFileName = imagen.getOriginalFilename();
                String uploadDir = "src/main/resources/static/images/";
                Path destPath = Path.of(uploadDir + uniqueFileName);
                Files.copy(imagen.getInputStream(), destPath, StandardCopyOption.REPLACE_EXISTING);
                producto.setProductPicture(uniqueFileName);
                productoService.save(producto);
                return "redirect:/productos";

            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/productos";
            }
        } else {
            return "redirect:/productos";
        }
    }
    @GetMapping("/productos/editar/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MAGANER')")
    public String mostrarFormularioDeEdicion(@PathVariable int id, Model model) {
        List<MunicipiosEntity> municipios = municipiosService.getAllMunicipios();
        List<ProvinciasEntity> provincias = provinciasService.getAllProvincias();
        Optional<ProductofferEntity> producto = productoService.findById(id);
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            model.addAttribute("municipios",municipios);
            model.addAttribute("provincias", provincias);
            return "producto/edit";
        } else {
            return "redirect:/producto";
        }
    }
    @PostMapping("/productos/editar/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MAGANER')")
    public String actualizarAnimal(@PathVariable int id, @ModelAttribute("producto") ProductofferEntity producto, @RequestParam("imagen")MultipartFile imagen) {
        producto.setProductId(id);
        if (!imagen.isEmpty()) {
            try {
                String uniqueFileName = imagen.getOriginalFilename();
                String uploadDir = "src/main/resources/static/images/";
                Path destPath = Path.of(uploadDir + uniqueFileName);
                Files.copy(imagen.getInputStream(), destPath, StandardCopyOption.REPLACE_EXISTING);
                producto.setProductPicture(uniqueFileName);
                productoService.save(producto);
                return "redirect:/productos";

            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/productos";
            }
        } else {
            productoService.save(producto);
            return "redirect:/productos";
        }
    }
}
