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

import com.byblos.evaluation.evaluationservice.dtos.CandidateDto;
import com.byblos.evaluation.evaluationservice.services.CandidateService;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
	@Autowired
	private CandidateService candidateService;

	@GetMapping("/all")
	public ResponseEntity<List<CandidateDto>> findAll() {
		return ResponseEntity.ok(candidateService.findAll());
	}

	@DeleteMapping("/delete/{id}")
	public void deleteTechno(@PathVariable(name = "id") Long id) {
		candidateService.deleteCandidate(id);

	}

	@GetMapping("/findByCompany")
	public ResponseEntity<List<CandidateDto>> findCandidateesByCompny(
			@RequestParam(name = "username") String username) {
		return ResponseEntity.ok(candidateService.findByCompany(username));
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<CandidateDto> findById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(candidateService.findById(id));
	}

	@PostMapping("/create")
	public ResponseEntity<CandidateDto> create(@RequestBody CandidateDto candidateDto) {

		candidateDto = candidateService.save(candidateDto);

		if (candidateDto != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(candidateDto);
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@PutMapping("update")
	public ResponseEntity<CandidateDto> update(@RequestBody CandidateDto candidateDto) {
		return ResponseEntity.ok(candidateService.updateCandidatePassword(candidateDto));
	}
}
