package com.example.gonzalezdariomyikea.services;

import com.example.gonzalezdariomyikea.models.ProvinciasEntity;
import com.example.gonzalezdariomyikea.presentation.ProvinciasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class ProvinciasService {
    @Autowired
    private ProvinciasRepository provinciasRepository;

    public List<ProvinciasEntity> getAllProvincias() {
        return provinciasRepository.findAll();
    }

    public Optional<ProvinciasEntity> getProvinciaById(Short id) {
        return provinciasRepository.findById(id);
    }

    public ProvinciasEntity saveProvincia(ProvinciasEntity provincia) {
        return provinciasRepository.save(provincia);
    }

    public void deleteProvincia(Short id) {
        provinciasRepository.deleteById(id);
    }
}
