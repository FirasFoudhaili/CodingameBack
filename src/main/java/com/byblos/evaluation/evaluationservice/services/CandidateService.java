package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.CandidateDto;


import java.util.List;

public interface CandidateService {
    CandidateDto save(CandidateDto p);
    void deleteCandidate(Long id );
    List<CandidateDto> findAll();
    CandidateDto findById(Long id);
    CandidateDto updateCandidate (CandidateDto candidateDto, Long id);

    CandidateDto updateCandidatePassword(CandidateDto candidateDto);
    CandidateDto signupCandidate( CandidateDto candidateDto);
    List<CandidateDto> findByCompany(String username);
   
}
