package com.byblos.evaluation.evaluationservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.byblos.evaluation.evaluationservice.dtos.QuestionConditionDto;
import com.byblos.evaluation.evaluationservice.dtos.QuestionDto;
import com.byblos.evaluation.evaluationservice.mappers.QuestionMapper;
import com.byblos.evaluation.evaluationservice.models.Question;
import com.byblos.evaluation.evaluationservice.services.QuestionService;

@RestController
@RequestMapping("/question")
public class QuestionController {
	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuestionMapper questionMapper;

//get all questions
	@GetMapping("/all")
	public ResponseEntity<List<QuestionDto>> findAll() {
		return ResponseEntity.ok(questionService.findAll());
	}

//delete question
	@DeleteMapping("/delete/{id}")
	public void deleteUser(@PathVariable(name = "id") Long id)

	{
		questionService.deleteQuestion(id);

	}

	@GetMapping("/find/{id}")
	public ResponseEntity<QuestionDto> findById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(questionService.findById(id));
	}

	// save question
	@PostMapping("/create")
	public ResponseEntity<QuestionDto> create(@RequestBody QuestionDto questionDto) {

		Question qu = questionService.save(questionDto);

		questionDto = questionMapper.toQuestionDto(qu);
		if (questionDto != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(questionDto);
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	// update question
	@PutMapping("update/{id}")
	public ResponseEntity<QuestionDto> update(@RequestBody QuestionDto questionDto,
			@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(questionService.updateQuestion(questionDto, id));
	}

	// search questions by (difficulty,technology,difficulty,category)
	@GetMapping("/pagination")
	public Page<Question> paginatedQuestions(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "500") int size,
			@RequestParam(name = "statement", required = false) String statement,
			@RequestParam(name = "level", required = false) String level,
			@RequestParam(name = "category", required = false) String category,

			@RequestParam(name = "difficulty", required = false) String difficulty, String technology) {
		return questionService.paginatedquestions(page, size, statement, level, category, difficulty, technology);

	}

	@PostMapping("/getAllQuestions")
	public ResponseEntity<List<Question>> getAllQuestions(@RequestBody List<QuestionConditionDto> tab,
			@RequestParam String username) {

		return ResponseEntity.ok(questionService.getAllQuestions(tab, username));
	}
}
