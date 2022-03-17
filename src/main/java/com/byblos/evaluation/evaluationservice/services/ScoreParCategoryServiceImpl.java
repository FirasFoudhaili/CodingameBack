package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.MoyCatCalcDto;
import com.byblos.evaluation.evaluationservice.dtos.ScoreParCategoryDto;
import com.byblos.evaluation.evaluationservice.dtos.ScorePerCategoryDto;
import com.byblos.evaluation.evaluationservice.mappers.ScoreParCategoryMapper;
import com.byblos.evaluation.evaluationservice.models.ScoreParCategory;

import com.byblos.evaluation.evaluationservice.models.Test;
import com.byblos.evaluation.evaluationservice.repositories.ScoreParCategoryRepo;
import com.byblos.evaluation.evaluationservice.repositories.TestQuestionRepo;
import com.byblos.evaluation.evaluationservice.repositories.TestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ScoreParCategoryServiceImpl implements ScoreParCategoryService {
    
    @Autowired
    ScoreParCategoryRepo scoreParCategoryRepo;
    @Autowired
    ScoreParCategoryMapper scoreParCategoryMapper;
    @Autowired
    TestQuestionRepo testQuestionRepo;
    @Autowired
    TestRepo testRepo;


    @Override
    public List<ScorePerCategoryDto> findByTest(Test test){
        List <ScoreParCategoryDto> liste=scoreParCategoryMapper.toScoreParCategoryDtos(scoreParCategoryRepo.findByTest(test));
        List<ScorePerCategoryDto> listee=new ArrayList<>();
        for(int i=0;i<liste.size();i++){
            ScorePerCategoryDto scorePerCategoryDto=new ScorePerCategoryDto();
            scorePerCategoryDto.setCategoryName(liste.get(i).getPrmCategory().getCategoryName());
            scorePerCategoryDto.setScoreCategory(liste.get(i).getScore());
            listee.add(scorePerCategoryDto);
        }
        return listee;
    }

 

    @Override
    public List<ScoreParCategory> calculScoreParDifficulty(Long idtest) {
        List<ScoreParCategory> spt=new ArrayList<>();
        List<MoyCatCalcDto> mtc=scoreParCategoryRepo.findGroupByCatCorrect(idtest);
        mtc.forEach(e->{
            ScoreParCategory sp=new ScoreParCategory();
            sp.setPrmCategory(e.getCategory());
            double n=(double)e.getNb();
            double t=(double)e.getTotal();
            double score=n/t*100;
            sp.setScore(score);
            sp.setTest(testRepo.findById(idtest).get());
            sp=scoreParCategoryRepo.save(sp);
            spt.add(sp);
        });
        return spt;
    }
    
    
    
    
    
    
    
}
