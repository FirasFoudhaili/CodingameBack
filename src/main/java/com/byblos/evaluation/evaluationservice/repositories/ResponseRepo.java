package com.byblos.evaluation.evaluationservice.repositories;

import com.byblos.evaluation.evaluationservice.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import com.byblos.evaluation.evaluationservice.models.Response;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ResponseRepo extends JpaRepository<Response, Long> {
    //get responses of question
    @Query(value = "SELECT * FROM public.response WHERE question_questionid = ?", nativeQuery = true)
    List<Response> getResponses(Long id);
    @Modifying
    @Transactional
    //Delete responses of a question 
    @Query(value = "DELETE FROM public.response WHERE question_questionid = ?", nativeQuery = true)
    void deleteResponseByQuestionId(Long id);

    List<Response> findByQuestion(Question question);
}
