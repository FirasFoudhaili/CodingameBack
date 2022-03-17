package com.byblos.evaluation.evaluationservice.mappers;

import com.byblos.evaluation.evaluationservice.dtos.QuestionDto;
import com.byblos.evaluation.evaluationservice.models.Question;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface QuestionMapper {

	QuestionDto toQuestionDto(Question question);
	List<QuestionDto> toQuestionDtos(List<Question> prmTechnologies);

	Question toQuestion(QuestionDto questionDto);
}
