package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.TestQuestionDto;
import com.byblos.evaluation.evaluationservice.models.Question;
import com.byblos.evaluation.evaluationservice.models.TestQuestion;

import java.util.List;
import java.util.Optional;

public interface TestQuestionService {

    TestQuestion save(TestQuestionDto p);
   List<Optional<TestQuestion>> findByTestId(Long id);
    TestQuestion findByQuestion(Question question);
    void deleteTestQuestion(TestQuestion id);
    List<TestQuestionDto> findAll();
    TestQuestionDto findById(TestQuestion id);
    TestQuestionDto updateTestQuestion(TestQuestionDto testQuestionDto, TestQuestion id);
    List<TestQuestionDto> getTestQuestionResponses(Long idCompteEval);
    List<TestQuestionDto> findByTest(Long test);
}
