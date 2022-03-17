package com.byblos.evaluation.evaluationservice.repositories;

import com.byblos.evaluation.evaluationservice.dtos.*;
import com.byblos.evaluation.evaluationservice.models.PrmCategory;
import com.byblos.evaluation.evaluationservice.models.ScoreParCategory;
import com.byblos.evaluation.evaluationservice.models.Test;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreParCategoryRepo extends JpaRepository<ScoreParCategory,Long> {
           List<ScoreParCategory> findByTest(Test test);
           ScoreParCategoryDto findByTest(Long idTest);


    @Query(value="SELECT new com.byblos.evaluation.evaluationservice.dtos.MoyCatCalcDto(tc,SUM(tq.result),COUNT(tq.result)) \n" +
            "      FROM  PrmCategory tc, Test t , TestQuestion  tq, Question q\n" +
            "         where tq.test.testid=t.testid and q.prmCategory.id=tc.id and tq.question.questionid=q.questionid and t.testid=:idtest\n" +
            "                  group by tc.id ")
    List<MoyCatCalcDto> findGroupByCatCorrect(@Param("idPack") Long idtest);
    ScoreParCategory findByTestAndPrmCategory(Test test, PrmCategory prmCategory);


    @Query(value="SELECT COUNT(tq.question.questionid) " +
            "      FROM  PrmCategory tc, Test t , TestQuestion  tq, Question q\n" +
            "         where tq.test.testid=t.testid and q.prmCategory.id=tc.id and tc.id=:idcat and " +
            " tq.question.questionid=q.questionid and t.testid=:idtest ")
    Long countNbTotalBytestAndCat(Long idtest,Long idcat);
}
