package com.example.gonzalezdariomyikea.services;

import com.example.gonzalezdariomyikea.models.MunicipiosEntity;
import com.example.gonzalezdariomyikea.presentation.MunicipiosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MunicipiosService {
    @Autowired
    private MunicipiosRepository municipiosRepository;

    public List<MunicipiosEntity> getAllMunicipios() {
        return municipiosRepository.findAll();
    }

    public Optional<MunicipiosEntity> getMunicipioById(Short id) {
        return municipiosRepository.findById(id);
    }

    public MunicipiosEntity saveMunicipio(MunicipiosEntity municipio) {
        return municipiosRepository.save(municipio);
    }

    public void deleteMunicipio(Short id) {
        municipiosRepository.deleteById(id);
    }

}
