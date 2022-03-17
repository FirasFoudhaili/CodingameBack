package com.byblos.evaluation.evaluationservice.mappers;

import com.byblos.evaluation.evaluationservice.dtos.CandidateDto;
import com.byblos.evaluation.evaluationservice.models.Candidate;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel="spring")
public interface CandidateMapper {

    CandidateDto toCandidateDto(Candidate candidate);
    List<CandidateDto> toCandidateDtos(List<Candidate> candidates);

    Candidate toCandidate(CandidateDto candidatedto);
}
