package com.byblos.evaluation.evaluationservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.byblos.evaluation.evaluationservice.dtos.TestQuestionDto;
import com.byblos.evaluation.evaluationservice.models.ScoreParTechnology;
import com.byblos.evaluation.evaluationservice.models.TestQuestion;
import com.byblos.evaluation.evaluationservice.services.ScoreParCategoryService;
import com.byblos.evaluation.evaluationservice.services.ScoreParDifficultyService;
import com.byblos.evaluation.evaluationservice.services.ScoreParTechnologyService;
import com.byblos.evaluation.evaluationservice.services.TestQuestionService;
import com.byblos.evaluation.evaluationservice.services.TestService;

@RestController
@RequestMapping("/testQuestion")
public class TestQuestionController {

	@Autowired
	private TestService testService;
	@Autowired
	private TestQuestionService testQuestionService;
	@Autowired
	private ScoreParTechnologyService scoreParTechnologyService;
	@Autowired
	private ScoreParCategoryService scoreParCategoryService;
	@Autowired
	private ScoreParDifficultyService scoreParDifficultyService;

	@GetMapping("/test/{id}")
	public ResponseEntity<List<TestQuestionDto>> findByTest2(@PathVariable(name = "id") Long test) {
		return ResponseEntity.ok(testQuestionService.findByTest(test));
	}

	@GetMapping("/all")
	public ResponseEntity<List<TestQuestionDto>> findAll() {
		return ResponseEntity.ok(testQuestionService.findAll());
	}

	@PostMapping("/create")
	public ResponseEntity<TestQuestion> create(@RequestBody TestQuestionDto testQuestionDto) {
		return ResponseEntity.ok(testQuestionService.save(testQuestionDto));
	}

	@PostMapping("/find/{testId}")
	public ResponseEntity<List<TestQuestion>> findByTest(@PathVariable(name = "testId") Long id) {
		return ResponseEntity.ok(testService.findByTestId(id));
	}

	@GetMapping("/calcul/{testId}")
	public ResponseEntity<List<ScoreParTechnology>> calcul(@PathVariable(name = "testId") Long idtest) {
		scoreParCategoryService.calculScoreParDifficulty(idtest);
		scoreParDifficultyService.calculScoreParDifficulty(idtest);
		return ResponseEntity.ok(scoreParTechnologyService.calculScoreParTechnology(idtest));
	}

}
