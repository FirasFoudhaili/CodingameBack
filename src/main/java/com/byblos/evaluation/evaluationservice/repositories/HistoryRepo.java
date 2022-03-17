package com.byblos.evaluation.evaluationservice.repositories;

import com.byblos.evaluation.evaluationservice.models.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepo extends JpaRepository <History,Long> {
}
