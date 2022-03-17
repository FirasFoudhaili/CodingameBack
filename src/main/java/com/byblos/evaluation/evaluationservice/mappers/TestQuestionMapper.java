package com.byblos.evaluation.evaluationservice.mappers;

import com.byblos.evaluation.evaluationservice.dtos.TestQuestionDto;
import com.byblos.evaluation.evaluationservice.models.TestQuestion;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface TestQuestionMapper {

    TestQuestionDto toTestQuestionDto(TestQuestion testQuestion);

    List<TestQuestionDto> toTestQuestionDtos(List<TestQuestion> testsQuestions);

    TestQuestion toTestQuestion(TestQuestionDto testQuestionDto);
}
