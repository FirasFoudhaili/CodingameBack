package com.byblos.evaluation.evaluationservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.byblos.evaluation.evaluationservice.dtos.MoyDeffCalcDto;
import com.byblos.evaluation.evaluationservice.models.ScoreParDifficulty;
import com.byblos.evaluation.evaluationservice.models.Test;

import feign.Param;

@Repository
public interface ScoreParDifficultyRepo extends JpaRepository<ScoreParDifficulty, Long> {

	List<ScoreParDifficulty> findByTest(Test test);

	@Query(value = "SELECT new com.byblos.evaluation.evaluationservice.dtos.MoyDeffCalcDto(tc,SUM(tq.result),COUNT(tq.result)) \n"
			+ "      FROM  PrmDifficulty tc, Test t , TestQuestion  tq, Question q\n"
			+ "         where tq.test.testid=t.testid and q.prmDifficulty.id=tc.id and tq.question.questionid=q.questionid and t.testid=:idtest\n"
			+ "                  group by tc.id ")
	List<MoyDeffCalcDto> findGroupByDiffCorrect(@Param("idPack") Long idtest);

	@Query(value = "SELECT COUNT(tq.question.questionid) "
			+ "      FROM  PrmDifficulty tc, Test t , TestQuestion  tq, Question q\n"
			+ "         where tq.test.testid=t.testid and q.prmDifficulty.id=tc.id and tc.id=:iddiff and "
			+ " tq.question.questionid=q.questionid and t.testid=:idtest ")
	Long countNbTotalBytestAndDiff(Long idtest, Long iddiff);
}
