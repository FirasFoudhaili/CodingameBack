package com.byblos.evaluation.evaluationservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.byblos.evaluation.evaluationservice.models.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long>, QuerydslPredicateExecutor<Question> {
    @Query(value="SELECT *  FROM  public.question where prm_category_id=? and prm_difficulty_id= ? and prm_technology_id=? and prm_level_id= ? ORDER BY random() LIMIT ?",nativeQuery = true)
    List<Question> getAllQuestions(Long idPrmCategory,Long idPrmDifficulty,Long idPrmTechnology,Long idPrmLevel, Long nbQuestions);

}
