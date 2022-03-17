package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.DealDto;
import com.byblos.evaluation.evaluationservice.mappers.DealMapper;
import com.byblos.evaluation.evaluationservice.models.Deal;
import com.byblos.evaluation.evaluationservice.repositories.DealRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DealServiceImpl implements DealService {
    @Autowired
    private DealRepo dealRepo;
    @Autowired
    private DealMapper dealMapper;

    @Override
    public DealDto findById(Long id) {
        
        Optional<Deal> p = dealRepo.findById(id);
        if(p.isPresent()){
        return dealMapper.toDealDto(p.get());
        }
        else { 
            return null;
        }
    }
//add deal
    @Override
    public Deal save(DealDto p) {
        return dealRepo.save(dealMapper.toDeal(p));

    }
    @Override
    public Deal findByDealName (String dealName){
        return  dealRepo.findByDealName(dealName);
    }

    @Override
    public void deleteDeal(Long id) {
        DealDto pr = new DealDto();
        pr.setId(id);
        dealRepo.delete(dealMapper.toDeal(pr));
    }

    @Override
    public List<DealDto> findAll() {

        return dealMapper.toDealDtos(dealRepo.findAll());
    }

    @Override
    public DealDto updateDeal(DealDto dealDto, Long id) {
        Optional<Deal> pr = dealRepo
                .findById(dealMapper.toDeal(dealDto).getId());
        if (!pr.isPresent())
            dealDto.setId(id);
        Deal p = dealRepo.save(dealMapper.toDeal(dealDto));
        return dealMapper.toDealDto(p);
    }
}
