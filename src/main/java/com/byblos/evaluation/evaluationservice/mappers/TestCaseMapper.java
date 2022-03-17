package com.byblos.evaluation.evaluationservice.mappers;

import java.util.List;
import org.mapstruct.Mapper;

import com.byblos.evaluation.evaluationservice.dtos.TestCaseDto;
import com.byblos.evaluation.evaluationservice.models.TestCase;


@Mapper(componentModel="spring")
public interface TestCaseMapper {
	

	TestCaseDto toTestCaseDto(TestCase tc);
	List<TestCaseDto> toTestCaseDtos(List<TestCase> tests);

	TestCase toTestCase(TestCaseDto testCaseDto);
}
