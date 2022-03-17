package com.byblos.evaluation.evaluationservice.repositories;


import com.byblos.evaluation.evaluationservice.models.CompteEval;
import com.byblos.evaluation.evaluationservice.models.TestAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


import java.util.Optional;

@Repository
public interface TestAccessRepo extends JpaRepository <TestAccess,Long> {
    @Query(value = "SELECT * FROM public.test_access WHERE compte_eval_id = ?", nativeQuery = true)
    List<TestAccess> getTestAccessResponses(Long idCompteEval);
    Optional<TestAccess> findByCompteEval(CompteEval compteEval);

    @Transactional
    @Modifying
    @Query(value = "Update TestAccess ta set ta.usedAccessNumber = ta.usedAccessNumber+1" +
            "where ta.id = (Select min(ta.id) from TestAccess ta , PrmAccess pa, CompteEval ce " +
            "where ce.company.id=:idCompany and (ta.usedAccessNumber < pa.value)" +
            "and ta.compteEval.id=ce.id )")
    int incrTestAccess(Long idCompany);
}
