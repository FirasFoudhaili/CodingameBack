package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.PrmAccessDto;
import com.byblos.evaluation.evaluationservice.mappers.PrmAccessMapper;
import com.byblos.evaluation.evaluationservice.models.PrmAccess;
import com.byblos.evaluation.evaluationservice.repositories.PrmAccessRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PrmAccessServiceImpl implements PrmAccessService {

    @Autowired
    private PrmAccessRepo prmAccessRepo;
    @Autowired
    private PrmAccessMapper prmAccessMapper;

    @Override
    public PrmAccessDto findById(Long id) {
        Optional<PrmAccess> p = prmAccessRepo.findById(id);
        if(p.isPresent()){
        return prmAccessMapper.toPrmAccessDto(p.get());
        }
        else{ 
            return null;
        }
    }
    //save a new access
    @Override
    public PrmAccess save(PrmAccessDto p) {
        return prmAccessRepo.save(prmAccessMapper.toPrmAccess(p));

    }
//Delete access 
    @Override
    public void deleteAccess(Long id) {
        PrmAccessDto pr = new PrmAccessDto();
        pr.setId(id);
        prmAccessRepo.delete(prmAccessMapper.toPrmAccess(pr));
    }
//get list access
    @Override
    public List<PrmAccessDto> findAll() {

        return prmAccessMapper.toPrmAccessDtos(prmAccessRepo.findAll());
    }
//update access
    @Override
    public PrmAccessDto updateAccess(PrmAccessDto prmAccessDto, Long id) {
        Optional<PrmAccess> pr = prmAccessRepo
                .findById(prmAccessMapper.toPrmAccess(prmAccessDto).getId());
        if (!pr.isPresent())
            prmAccessDto.setId(id);
        PrmAccess p = prmAccessRepo.save(prmAccessMapper.toPrmAccess(prmAccessDto));
        return prmAccessMapper.toPrmAccessDto(p);
    }
}
