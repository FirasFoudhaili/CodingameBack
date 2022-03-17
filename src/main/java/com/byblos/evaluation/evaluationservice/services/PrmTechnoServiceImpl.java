package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.PrmTechnologyDto;
import com.byblos.evaluation.evaluationservice.mappers.PrmTechnologyMapper;
import com.byblos.evaluation.evaluationservice.models.PrmTechnology;
import com.byblos.evaluation.evaluationservice.repositories.PrmTechnologyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class PrmTechnoServiceImpl implements PrmTechnoService{
    @Autowired
    PrmTechnologyRepo prmtechrepo;
    @Autowired
    private PrmTechnologyMapper prmTechnologyMapper;
//create technology
    @Override
    public PrmTechnology save(PrmTechnologyDto p){
        
        return prmtechrepo.save(prmTechnologyMapper.toPrmTechnology(p));

    }
    //delete technology
      @Override
      public void deleteTechno(Long id) {
          PrmTechnology prmTechnology = prmtechrepo.getOne(id);
          prmtechrepo.delete(prmTechnology);
      }
//get All technologies
    @Override
    public List<PrmTechnologyDto> findAll(){
        
        return prmTechnologyMapper.toPrmTechnologyDtos( prmtechrepo.findAll());

    }

    @Override
    public PrmTechnologyDto findById(Long id){
        Optional<PrmTechnology> p= prmtechrepo.findById(id);
        if(p.isPresent()){
       return prmTechnologyMapper.toPrmTechnologyDto(p.get());
        }else{ 
            return null;
        }

    }
//update technology
    @Override
    public PrmTechnologyDto updateTechno (PrmTechnologyDto prmTechnologyDto, Long id)
    {
        //		searching if prmTechnologyDto exist (getting its id)
        Optional<PrmTechnology> pr= prmtechrepo.findById(prmTechnologyMapper.toPrmTechnology(prmTechnologyDto).getId());
        //if  it does not exist set a new id to this object to save it as a new object 
        if (!pr.isPresent())
            prmTechnologyDto.setId(id);
        //saving  (updating or creating the prmTechnologylDto)
        PrmTechnology p=prmtechrepo.save(prmTechnologyMapper.toPrmTechnology(prmTechnologyDto));
        return prmTechnologyMapper.toPrmTechnologyDto(p);

    }
    @Override
    public PrmTechnology getPrmTechnology(String prmTechnologyName){
        return prmtechrepo.findPrmTechnologyByTechnologyName(prmTechnologyName);
    }

    }


