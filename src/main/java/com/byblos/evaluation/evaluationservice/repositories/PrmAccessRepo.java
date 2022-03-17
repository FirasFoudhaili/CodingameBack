package com.byblos.evaluation.evaluationservice.repositories;

import com.byblos.evaluation.evaluationservice.models.PrmAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrmAccessRepo extends JpaRepository <PrmAccess,Long> {
}
