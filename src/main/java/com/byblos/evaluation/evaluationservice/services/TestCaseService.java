package com.byblos.evaluation.evaluationservice.services;

import java.util.List;
import com.byblos.evaluation.evaluationservice.dtos.TestCaseDto;
import com.byblos.evaluation.evaluationservice.models.TestCase;


public interface TestCaseService {
	
    TestCase save(TestCaseDto tc);
    void deleteTestCase(Long id );
    List<TestCaseDto> findAll();
    TestCaseDto findById(Long id);
    TestCaseDto updateTestCase(TestCaseDto testCaseDto, Long id);
}
