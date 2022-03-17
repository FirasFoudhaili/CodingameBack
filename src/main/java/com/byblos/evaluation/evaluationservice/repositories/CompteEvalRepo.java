package com.byblos.evaluation.evaluationservice.repositories;


import com.byblos.evaluation.evaluationservice.models.Company;
import com.byblos.evaluation.evaluationservice.models.CompteEval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompteEvalRepo extends JpaRepository <CompteEval,Long> {
    Optional<CompteEval> findByCompany (Company company);
}
