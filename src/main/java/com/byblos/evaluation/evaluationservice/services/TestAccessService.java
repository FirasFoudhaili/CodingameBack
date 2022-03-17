package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.CompteEvalDto;
import com.byblos.evaluation.evaluationservice.dtos.TestAccessDto;
import com.byblos.evaluation.evaluationservice.models.TestAccess;

import java.util.List;

public interface TestAccessService {

    TestAccess save(TestAccessDto p);
    CompteEvalDto findCompteEvalByEmail(String username);
    void deleteTestAccess(Long id);
    List<TestAccessDto> findAll();
    TestAccessDto findById(Long id);
    TestAccessDto updateTestAccess(TestAccessDto testAccessDto, Long id);
    public int calculUnusedAccess(Long idCompteEval);
    List<TestAccessDto> getTestAccessResponses(Long idCompteEval);

}
