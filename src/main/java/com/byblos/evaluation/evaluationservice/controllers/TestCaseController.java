package com.byblos.evaluation.evaluationservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.byblos.evaluation.evaluationservice.dtos.TestCaseDto;
import com.byblos.evaluation.evaluationservice.mappers.TestCaseMapper;
import com.byblos.evaluation.evaluationservice.models.TestCase;
import com.byblos.evaluation.evaluationservice.services.TestCaseService;

@RestController
@RequestMapping("/testcase")
public class TestCaseController {
	@Autowired
	private TestCaseService testCaseService;
	@Autowired
	private TestCaseMapper testCaseMapper;
	
	
	@GetMapping("/all")
	public ResponseEntity<List<TestCaseDto>> findAll() {
		return ResponseEntity.ok(testCaseService.findAll());
	}

	@DeleteMapping("/delete/{id}")
	public void deleteTestCase(@PathVariable(name = "id") Long id){
		testCaseService.deleteTestCase(id);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<TestCaseDto> findById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(testCaseService.findById(id));
	}
	
		@PostMapping("/create")
		public ResponseEntity<TestCaseDto> create(@RequestBody TestCaseDto testCaseDto) {

			TestCase tc = testCaseService.save(testCaseDto);

			testCaseDto = testCaseMapper.toTestCaseDto(tc);
			if (testCaseDto != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(testCaseDto);
			} else
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

		@PutMapping("update/{id}")
		public ResponseEntity<TestCaseDto> update(@RequestBody TestCaseDto testCaseDto,
				@PathVariable(name = "id") Long id) {
			return ResponseEntity.ok(testCaseService.updateTestCase(testCaseDto, id));
		}
	
	
}
