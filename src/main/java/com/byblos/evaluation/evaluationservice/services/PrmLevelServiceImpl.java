package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.PrmLevelDto;
import com.byblos.evaluation.evaluationservice.mappers.PrmLevelMapper;
import com.byblos.evaluation.evaluationservice.models.PrmLevel;
import com.byblos.evaluation.evaluationservice.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrmLevelServiceImpl implements PrmLevelService{


    @Autowired
    private PrmLevelRepo prmLevelRepo;

    @Autowired
    private PrmLevelMapper prmLevelMapper;

    @Override
    public PrmLevelDto findById(Long id){
        Optional<PrmLevel> p= prmLevelRepo.findById(id);
        if(p.isPresent()){
        return  prmLevelMapper.toPrmLevelDto(p.get());
        }else{ 
            return null;
        }
    }

//create a new level 
    @Override
    public PrmLevel save(PrmLevelDto p){
        return prmLevelRepo.save(prmLevelMapper.toPrmLevel(p));

    }
//delete level 
    @Override
    public void deleteLevel(Long id ){
        PrmLevelDto pr=new PrmLevelDto();
        pr.setId(id);
        prmLevelRepo.delete(prmLevelMapper.toPrmLevel(pr));
    }
//get all levels 
    @Override
    public List<PrmLevelDto> findAll(){

        return  prmLevelMapper.toPrmLevelDtos(prmLevelRepo.findAll());
    }
//update levels 
    @Override
    public PrmLevelDto updateLevel (PrmLevelDto prmLevelDto, Long id)
    {
        //		searching if prmLevelDto exist (getting its id)
        Optional<PrmLevel> pr= prmLevelRepo.findById(prmLevelMapper.toPrmLevel(prmLevelDto).getId());
        //if  it does not exist set a new id to this object to save it as a new object 
        if (!pr.isPresent())
            prmLevelDto.setId(id);
        //saving  (updating or creating the prmLevelDto)
        PrmLevel p=prmLevelRepo.save(prmLevelMapper.toPrmLevel(prmLevelDto));
        return prmLevelMapper.toPrmLevelDto(p);
    }
    @Override
    public PrmLevel getLevel(String levelName){
        return prmLevelRepo.findPrmLevelByLevelName(levelName);
        
    }
    
}
