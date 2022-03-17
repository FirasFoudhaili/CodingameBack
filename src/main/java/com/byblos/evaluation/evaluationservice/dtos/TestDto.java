package com.byblos.evaluation.evaluationservice.dtos;

import com.byblos.evaluation.evaluationservice.models.Pack;
import lombok.Data;
import java.util.Date;

@Data
public class TestDto {

    private Long testid;
    private java.sql.Date creationDate;
    private Long testDuration;
    private Pack pack;
    private Boolean done;
    private Boolean expired;
    private Long passageDuration;
    private Double totalScore;
    private CandidateDto candidate;
    private Boolean send;
    private Date expirationDate;
}
