package com.byblos.evaluation.evaluationservice.repositories;

import com.byblos.evaluation.evaluationservice.models.PrmTechnology;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface PrmTechnologyRepo extends JpaRepository<PrmTechnology, Long> {
    public void deleteById(Long id );
    PrmTechnology findPrmTechnologyByTechnologyName(String technologyName);

}
