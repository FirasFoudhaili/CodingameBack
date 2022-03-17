package com.byblos.evaluation.evaluationservice.services;


import com.byblos.evaluation.evaluationservice.dtos.*;
import com.byblos.evaluation.evaluationservice.mappers.ScoreParDifficultyMapper;
import com.byblos.evaluation.evaluationservice.models.ScoreParDifficulty;
import com.byblos.evaluation.evaluationservice.models.Test;
import com.byblos.evaluation.evaluationservice.repositories.ScoreParDifficultyRepo;
import com.byblos.evaluation.evaluationservice.repositories.TestQuestionRepo;
import com.byblos.evaluation.evaluationservice.repositories.TestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreParDifficultyServiceImpl implements ScoreParDifficultyService {
    
    @Autowired
    ScoreParDifficultyRepo scoreParDifficultyRepo;
    @Autowired
    ScoreParDifficultyMapper scoreParDifficultyMapper;
    @Autowired
    TestQuestionRepo testQuestionRepo;
    @Autowired
    TestRepo testRepo;

    @Override
    public List<ScorePerDifficultyDto> findByTest(Test test){
        List <ScoreParDifficultyDto> liste=scoreParDifficultyMapper.toScoreParDifficultyDtos(scoreParDifficultyRepo.findByTest(test));
        List<ScorePerDifficultyDto> listee=new ArrayList<>();
        for(int i=0;i<liste.size();i++){
            ScorePerDifficultyDto scorePerDifficultyDto=new ScorePerDifficultyDto();
            scorePerDifficultyDto.setDifficultyName(liste.get(i).getPrmDifficulty().getDifficultyName());
            scorePerDifficultyDto.setScoreDifficulty(liste.get(i).getScore());
            listee.add(scorePerDifficultyDto);
        }
        return listee;
}
    @Override
    public List<ScoreParDifficulty> calculScoreParDifficulty(Long idtest) {
        List<ScoreParDifficulty> spt=new ArrayList<>();
        List<MoyDeffCalcDto> mtc=scoreParDifficultyRepo.findGroupByDiffCorrect(idtest);
        mtc.forEach(e->{
            ScoreParDifficulty sp=new ScoreParDifficulty();
            sp.setPrmDifficulty(e.getDifficulty());
            double n=(double)e.getNb();
            double t=(double)e.getTotal();
            double score=n/t*100;
            sp.setScore(score);
            sp.setTest(testRepo.findById(idtest).get());
            sp=scoreParDifficultyRepo.save(sp);
            spt.add(sp);
        });
        return spt;
    }
}
