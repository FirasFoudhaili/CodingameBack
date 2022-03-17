package com.byblos.evaluation.evaluationservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.byblos.evaluation.evaluationservice.models.PrmLevel;
import org.springframework.stereotype.Repository;

@Repository
public interface PrmLevelRepo extends JpaRepository<PrmLevel, Long> {
    PrmLevel findPrmLevelByLevelName(String levelName);
    

}
