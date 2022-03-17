package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.*;
import com.byblos.evaluation.evaluationservice.mappers.ScoreParTechnologyMapper;
import com.byblos.evaluation.evaluationservice.models.ScoreParTechnology;
import com.byblos.evaluation.evaluationservice.models.Test;
import com.byblos.evaluation.evaluationservice.repositories.ScoreParTechnologyRepo;
import com.byblos.evaluation.evaluationservice.repositories.TestQuestionRepo;
import com.byblos.evaluation.evaluationservice.repositories.TestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ScoreParTechnologyServiceImpl implements ScoreParTechnologyService{

    @Autowired
    ScoreParTechnologyRepo scoreParTechnologyRepo;
    @Autowired
    ScoreParTechnologyMapper scoreParTechnologyMapper;
    @Autowired
    TestQuestionRepo testQuestionRepo;
    @Autowired
    TestRepo testRepo;

    @Override
    public List<ScorePerTechnologyDto> findByTest(Test test){
        List <ScoreParTechnologyDto> liste=scoreParTechnologyMapper.toScoreParTechnologyDtos(scoreParTechnologyRepo.findByTest(test));
        List<ScorePerTechnologyDto> listee=new ArrayList<>();
        for(int i=0;i<liste.size();i++){
            ScorePerTechnologyDto scorePerTechnologyDto=new ScorePerTechnologyDto();
            scorePerTechnologyDto.setTechnologyName(liste.get(i).getPrmTechnology().getTechnologyName());
            scorePerTechnologyDto.setScoreTechnology(liste.get(i).getScore());
            listee.add(scorePerTechnologyDto);
        }
        return listee;
    }

    @Override
    public List<ScoreParTechnology> calculScoreParTechnology(Long idtest) {
        List<ScoreParTechnology> spt=new ArrayList<>();
        List<MoyTechCalcDto> mtc=scoreParTechnologyRepo.findGroupByTechCorrect(idtest);
        mtc.forEach(e->{
            ScoreParTechnology sp=new ScoreParTechnology();
            sp.setPrmTechnology(e.getTech());
            double n=(double)e.getNb();
            double t=(double)e.getTotal();
            double score=n/t*100;
            sp.setScore(score);
            sp.setTest(testRepo.findById(idtest).get());
            sp=scoreParTechnologyRepo.save(sp);
            spt.add(sp);
        });
        return spt;
    }
}
