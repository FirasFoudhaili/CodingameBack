package com.byblos.evaluation.evaluationservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.byblos.evaluation.evaluationservice.models.TestCase;

@Repository
public interface TestCaseRepo extends JpaRepository<TestCase, Long> {

}
