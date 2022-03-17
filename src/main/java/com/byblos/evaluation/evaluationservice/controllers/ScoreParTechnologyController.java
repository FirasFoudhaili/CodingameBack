package com.byblos.evaluation.evaluationservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.byblos.evaluation.evaluationservice.dtos.ScorePerTechnologyDto;
import com.byblos.evaluation.evaluationservice.dtos.TestDto;
import com.byblos.evaluation.evaluationservice.mappers.TestMapper;
import com.byblos.evaluation.evaluationservice.services.ScoreParTechnologyService;

@RestController
@RequestMapping("/scoreTechnology")

public class ScoreParTechnologyController {
	@Autowired
	ScoreParTechnologyService scoreParTechnologyService;

	@Autowired
	TestMapper testMapper;

	@PostMapping("/findScorePerTechnology")
	public ResponseEntity<List<ScorePerTechnologyDto>> scorePerTechnology(@RequestBody TestDto testDto) {
		return ResponseEntity.ok(scoreParTechnologyService.findByTest(testMapper.toTest(testDto)));

	}

}
