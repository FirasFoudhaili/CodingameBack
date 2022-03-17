package com.byblos.evaluation.evaluationservice.mappers;


import com.byblos.evaluation.evaluationservice.dtos.TestAccessDto;
import com.byblos.evaluation.evaluationservice.models.TestAccess;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface TestAccessMapper {

    TestAccessDto toTestAccessDto(TestAccess testAccess);

    List<TestAccessDto> toTestAccessDtos(List<TestAccess> testsAccess);

    TestAccess toTestAccess(TestAccessDto testAccessDto);
}


