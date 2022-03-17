package com.byblos.evaluation.evaluationservice.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.byblos.evaluation.evaluationservice.models.PrmCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface PrmCategoryRepo extends JpaRepository<PrmCategory, Long> {
    PrmCategory findByCategoryName(String categoryName);
  

}
