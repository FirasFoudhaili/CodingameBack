package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.BoDto;
import com.byblos.evaluation.evaluationservice.mappers.BoMapper;
import com.byblos.evaluation.evaluationservice.models.Bo;
import com.byblos.evaluation.evaluationservice.repositories.BoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoServiceImpl  implements BoService{
    @Autowired
    private BoRepo boRepo;
    @Autowired
    private BoMapper boMapper;

    @Override
    public Bo save(BoDto p){
        return boRepo.save(boMapper.toBo(p));

    }

    @Override
    public void deleteBo(Long id ){
        BoDto pr=new BoDto();
        pr.setId(id);
        boRepo.delete(boMapper.toBo(pr));
    }

    @Override
    public List<BoDto> findAll(){

        return  boMapper.toBoDtos(boRepo.findAll());
    }

    @Override
    public BoDto findById(Long id){
        Optional<Bo> p= boRepo.findById(id);
        if(p.isPresent()){
        return  boMapper.toBoDto(p.get());
        }
        else{
            return null;
            }
        }

    @Override
    public BoDto updateBo (BoDto boDto, Long id)
    {
        Optional<Bo> pr= boRepo.findById(boMapper.toBo(boDto).getId());
        if (!pr.isPresent())
            boDto.setId(id);
        Bo p=boRepo.save(boMapper.toBo(boDto));
        return boMapper.toBoDto(p);
    }
}
