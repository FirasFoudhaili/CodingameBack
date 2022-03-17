package com.byblos.evaluation.evaluationservice.services;
import com.byblos.evaluation.evaluationservice.dtos.QuestionConditionDto;
import com.byblos.evaluation.evaluationservice.dtos.QuestionDto;
import com.byblos.evaluation.evaluationservice.models.Question;
import org.springframework.data.domain.Page;
import java.util.List;

public interface QuestionService {
    Question save(QuestionDto p);
    void deleteQuestion(Long id );
    List<QuestionDto> findAll();
    QuestionDto findById(Long id);
    QuestionDto updateQuestion(QuestionDto questionDto, Long id);
  
   List<Question> getQuestions(QuestionConditionDto c);

    
    Page<Question> paginatedquestions(int page ,int size,String statement,String level,String category,String difficulty,String technology);
    List<Question> getAllQuestions(List<QuestionConditionDto> tab, String username);
}
