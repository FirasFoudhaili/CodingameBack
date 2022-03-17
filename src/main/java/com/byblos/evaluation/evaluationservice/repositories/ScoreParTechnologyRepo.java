package com.byblos.evaluation.evaluationservice.repositories;

import com.byblos.evaluation.evaluationservice.dtos.MoyTechCalcDto;
import com.byblos.evaluation.evaluationservice.models.ScoreParTechnology;
import com.byblos.evaluation.evaluationservice.models.Test;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreParTechnologyRepo extends JpaRepository<ScoreParTechnology,Long> {
    List<ScoreParTechnology> findByTest(Test test);

    @Query(value="SELECT new com.byblos.evaluation.evaluationservice.dtos.MoyTechCalcDto(tc,SUM(tq.result),COUNT (tq.result)) \n" +
            "      FROM  PrmTechnology tc, Test t , TestQuestion  tq, Question q\n" +
            "         where tq.test.testid=t.testid and q.prmTechnology.id=tc.id and tq.question.questionid=q.questionid and t.testid=:idtest\n" +
            "                  group by tc.id ")
    List<MoyTechCalcDto> findGroupByTechCorrect(@Param("idPack") Long idtest);

    @Query(value="SELECT COUNT(tq.question.questionid) " +
            "      FROM  PrmTechnology tc, Test t , TestQuestion  tq, Question q\n" +
            "         where tq.test.testid=t.testid and q.prmTechnology.id=tc.id and tc.id=:idtech and " +
            " tq.question.questionid=q.questionid and t.testid=:idtest ")
    Long countNbTotalBytestAndTech(Long idtest,Long idtech);
}
