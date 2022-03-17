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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.byblos.evaluation.evaluationservice.dtos.CompteEvalDto;
import com.byblos.evaluation.evaluationservice.dtos.TestAccessDto;
import com.byblos.evaluation.evaluationservice.mappers.TestAccessMapper;
import com.byblos.evaluation.evaluationservice.models.TestAccess;
import com.byblos.evaluation.evaluationservice.repositories.TestAccessRepo;
import com.byblos.evaluation.evaluationservice.services.TestAccessService;

@RestController
@RequestMapping("/TestAccess")
public class TestAccessController {
	@Autowired
	private TestAccessService testAccessService;
	@Autowired
	private TestAccessMapper testAccessMapper;
	@Autowired
	private TestAccessRepo testAccessRepo;

	@GetMapping("/all")
	public ResponseEntity<List<TestAccessDto>> findAll() {
		return ResponseEntity.ok(testAccessService.findAll());
	}

	@DeleteMapping("/delete/{id}")
	public void deleteAccess(@PathVariable(name = "id") Long id) {
		testAccessService.deleteTestAccess(id);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<TestAccessDto> findById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(testAccessService.findById(id));
	}

	@GetMapping("/find/user/{username}")
	public ResponseEntity<CompteEvalDto> findByUsername(@PathVariable(name = "username") String username) {
		return ResponseEntity.ok(testAccessService.findCompteEvalByEmail(username));
	}

	@PostMapping("/create")
	public ResponseEntity<TestAccessDto> create(@RequestBody TestAccessDto testAccessDto) {

		TestAccess testAccess = testAccessService.save(testAccessDto);

		testAccessDto = testAccessMapper.toTestAccessDto(testAccess);
		return ResponseEntity.status(HttpStatus.CREATED).body(testAccessDto);
	}

	@PutMapping("update/{id}")
	public ResponseEntity<TestAccessDto> update(@RequestBody TestAccessDto testAccessDto,
			@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(testAccessService.updateTestAccess(testAccessDto, id));
	}

	@GetMapping("/allUnusedAccess/{idCompteEval}")
	public ResponseEntity<Integer> calculUnusedAccess(@PathVariable(name = "idCompteEval") Long idCompteEval) {
		return ResponseEntity.ok(testAccessService.calculUnusedAccess(idCompteEval));

	}

	@GetMapping("/incrementAccess")
	public ResponseEntity<Integer> incrementer(@RequestParam(name = "idcompany") Long idcompany) {
		return ResponseEntity.ok(testAccessRepo.incrTestAccess(idcompany));
	}
}
