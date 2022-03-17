package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.CompteEvalDto;
import  com.byblos.evaluation.evaluationservice.models.CompteEval;


import java.util.List;

public interface CompteEvall {
    CompteEval save(CompteEvalDto compteEvalDto);
    void deleteCompteEval(Long id );
    List<CompteEvalDto> findAll();
    CompteEvalDto findById(Long id);
    CompteEvalDto updateCompteEval (CompteEvalDto compteEvalDto, Long id);
    CompteEvalDto  findCompteEval(String email);
}
