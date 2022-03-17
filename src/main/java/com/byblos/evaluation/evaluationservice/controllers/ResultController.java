package com.byblos.evaluation.evaluationservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.byblos.evaluation.evaluationservice.dtos.ResultDto;
import com.byblos.evaluation.evaluationservice.dtos.TestDto;
import com.byblos.evaluation.evaluationservice.dtos.TestResponsesDto;
import com.byblos.evaluation.evaluationservice.models.Result;
import com.byblos.evaluation.evaluationservice.services.ResultService;

@RestController
@RequestMapping("/result")
public class ResultController {
	@Autowired
	ResultService resultService;

	public ResponseEntity<Result> findResultByTest(TestDto testdto) {

		return ResponseEntity.ok(resultService.findResultByTest(testdto));

	}

	@GetMapping()
	public ResponseEntity<Boolean> existsByTestAndResponse(@RequestParam(name = "test") Long test,
			@RequestParam(name = "response") Long response) {
		return ResponseEntity.ok(resultService.existsByTestAndResponse(test, response));
	}

	@GetMapping("/all")
	public ResponseEntity<List<ResultDto>> findAll() {
		return ResponseEntity.ok(resultService.findAll());
	}

	@DeleteMapping("/delete/{id}")
	public void deleteResult(@PathVariable(name = "id") Long id)

	{
		resultService.deleteResult(id);

	}

	@GetMapping("/find/{id}")
	public ResponseEntity<ResultDto> findById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(resultService.findById(id));
	}

	@PostMapping("/create")
	public TestResponsesDto create(@RequestBody TestResponsesDto resultDto) {
		resultService.save(resultDto);
		return resultDto;
	}

	@PutMapping("update/{id}")
	public ResponseEntity<ResultDto> update(@RequestBody ResultDto resultDto, @PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(resultService.updateResult(resultDto, id));
	}
}
