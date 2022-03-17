package com.byblos.evaluation.evaluationservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.byblos.evaluation.evaluationservice.models.PrmDifficulty;
import org.springframework.stereotype.Repository;

@Repository
public interface PrmDifficultyRepo extends JpaRepository<PrmDifficulty, Long> {
    PrmDifficulty findPrmDifficultyByDifficultyName(String difficultyName);

}
