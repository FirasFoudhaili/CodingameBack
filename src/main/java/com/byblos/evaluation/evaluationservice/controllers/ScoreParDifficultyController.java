package com.byblos.evaluation.evaluationservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.byblos.evaluation.evaluationservice.dtos.ScorePerDifficultyDto;
import com.byblos.evaluation.evaluationservice.dtos.TestDto;
import com.byblos.evaluation.evaluationservice.mappers.TestMapper;
import com.byblos.evaluation.evaluationservice.services.ScoreParDifficultyService;

@RestController
@RequestMapping("/scoreDifficulty")

public class ScoreParDifficultyController {
	@Autowired
	ScoreParDifficultyService scoreParDifficultyService;

	@Autowired
	TestMapper testMapper;

	@PostMapping("/findScorePerDifficulty")
	public ResponseEntity<List<ScorePerDifficultyDto>> scorePerDifficulty(@RequestBody TestDto testDto) {
		return ResponseEntity.ok(scoreParDifficultyService.findByTest(testMapper.toTest(testDto)));

	}

}
