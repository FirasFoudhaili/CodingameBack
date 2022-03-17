package com.byblos.evaluation.evaluationservice.repositories;

import com.byblos.evaluation.evaluationservice.models.Response;
import com.byblos.evaluation.evaluationservice.models.Result;
import com.byblos.evaluation.evaluationservice.models.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultRepo extends JpaRepository <Result,Long> {
    Result findResultByTest(Test test);

    Optional<Result> findByTestAndResponse(Test t, Response r);
}
