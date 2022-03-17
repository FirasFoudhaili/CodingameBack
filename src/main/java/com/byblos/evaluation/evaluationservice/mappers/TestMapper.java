package com.byblos.evaluation.evaluationservice.mappers;

import com.byblos.evaluation.evaluationservice.dtos.TestDto;
import com.byblos.evaluation.evaluationservice.models.Test;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface TestMapper {

    TestDto toTestDto(Test test);

    List<TestDto> toTestDtos(List<Test> tests);

    Test toTest(TestDto testDto);
}


