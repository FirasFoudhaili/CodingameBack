package com.byblos.evaluation.evaluationservice.repositories;

import com.byblos.evaluation.evaluationservice.models.Candidate;
import com.byblos.evaluation.evaluationservice.models.Company;
import com.byblos.evaluation.evaluationservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepo extends JpaRepository<Candidate,Long> {

    Optional<Candidate> findByUser(User user);
    List<Candidate> findByCompany(Company company);
    
    
    
}
