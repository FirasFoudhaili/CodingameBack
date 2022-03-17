package com.byblos.evaluation.evaluationservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.byblos.evaluation.evaluationservice.dtos.ScorePerCategoryDto;
import com.byblos.evaluation.evaluationservice.dtos.TestDto;
import com.byblos.evaluation.evaluationservice.mappers.ScoreParCategoryMapper;
import com.byblos.evaluation.evaluationservice.mappers.TestMapper;
import com.byblos.evaluation.evaluationservice.services.ScoreParCategoryService;

@RestController
@RequestMapping("/scoreCateg")
public class ScoreParCategoryController {

	@Autowired
	ScoreParCategoryService scoreParCategoryService;
	@Autowired

	TestMapper testMapper;
	@Autowired
	ScoreParCategoryMapper scoreParCategoryMapper;

	@PostMapping("/findScorePerCategory")
	public ResponseEntity<List<ScorePerCategoryDto>> scorePerCategory(@RequestBody TestDto testDto) {
		return ResponseEntity.ok(scoreParCategoryService.findByTest(testMapper.toTest(testDto)));

	}

}
