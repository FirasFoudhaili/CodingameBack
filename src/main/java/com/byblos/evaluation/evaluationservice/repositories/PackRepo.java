package com.byblos.evaluation.evaluationservice.repositories;

import com.byblos.evaluation.evaluationservice.models.Company;
import com.byblos.evaluation.evaluationservice.models.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackRepo extends JpaRepository<Pack,Long> {
    Optional<Pack> findByCompany (Company company);
    @Query(value="SELECT *  FROM  public.pack where company_id=? ",nativeQuery = true)
    List<Pack> getAllPacks(Long idcompany);
    List <Pack> findByName(String name );
}
