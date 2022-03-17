package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.*;

import com.byblos.evaluation.evaluationservice.dtos.CandidateDto;
import com.byblos.evaluation.evaluationservice.dtos.QuestionDto;
import com.byblos.evaluation.evaluationservice.dtos.TestDto;
import com.byblos.evaluation.evaluationservice.models.Test;
import com.byblos.evaluation.evaluationservice.models.TestQuestion;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface TestService {
    Boolean rappelerTest(CandidateDto candidateDto, TestDto test);

    Boolean sendTest(CandidateDto candidateDto, TestDto testDto);

    TestQuestion findQuestionResponseById(Long id);

    Optional<Test> findById(Long id);

    List<TestQuestion> findByTestId(Long id);

    List<TestDto> findAll();

    List<TestDto> saveTest(List<CandidateDto> candidateDtos, List<QuestionDto> questionDtos, TestDto testDto);

    List<TestDto> createTests(List<CandidateDto> candidateDtos, List<QuestionDto> questionDtos, TestDto testDto);

    List<TestDto> getAllTests(Long idPack);

    Test save(TestDto t);

    List<TestDto> findDoneTests(Long pack);

    int getSendNumber(Long idPack);

    List<MoyenTechnologyDto> getAvgScoreParTechnologie(Long pack);
    List <TopCandidateDto> getRang (Long pack);
    ByteArrayInputStream testPDF(Long idPack);
    
    

    ByteArrayInputStream doneTestToExcel(Long pack) throws IOException;

    ByteArrayInputStream testToExcel(Long idPack) throws IOException;
    
    List<TopCandidateDto> getTopCandidates(Long idPack, Long number) throws CloneNotSupportedException;

    List<IntervalDurationDto> getDurationInterval(Long idPack);

    List<IntervalDoneDto> percentageDone(Long idPack);

    ByteArrayInputStream doneTestPDF(Long pack);

    List<ScoresDto> getScores(Long testid);

    List<IntervalStatDto> getIntervalStat(Long pack);

    List<ResultTestQuestionDto> getResultDetail(Long idtest);

    public void updateExpiration();

}
