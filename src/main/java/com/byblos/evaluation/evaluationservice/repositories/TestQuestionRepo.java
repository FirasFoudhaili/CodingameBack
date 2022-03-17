package com.byblos.evaluation.evaluationservice.repositories;

import com.byblos.evaluation.evaluationservice.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestQuestionRepo extends JpaRepository<TestQuestion,TestQuestionKeys> {
	List<TestQuestion> findByTest(Test t);
    TestQuestion findByQuestion(Question question);
	List<Optional<TestQuestion>> findByTest(Optional<Test> findById);



}
