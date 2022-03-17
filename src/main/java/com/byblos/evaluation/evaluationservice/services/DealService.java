package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.DealDto;
import com.byblos.evaluation.evaluationservice.models.Deal;

import java.util.List;

public interface DealService {

    Deal save(DealDto p);
    void deleteDeal(Long id );
    List<DealDto> findAll();
    DealDto findById(Long id);
    DealDto updateDeal (DealDto dealDto, Long id);
    Deal findByDealName(String dealName);
}
