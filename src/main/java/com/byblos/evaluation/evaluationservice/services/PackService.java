package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.PackDto;
import com.byblos.evaluation.evaluationservice.models.Pack;

import java.util.List;

public interface PackService {

    Pack save(PackDto p);
    void deletePack(Long id );
    List<PackDto> findAll();
    PackDto findById(Long id);
    PackDto updatePack(PackDto packDto, Long id);
    PackDto findPackByEmail(String username);
    List<Pack>getAllPacks(Long idCompany);
    List<Pack> findByName(String name);
    
}
