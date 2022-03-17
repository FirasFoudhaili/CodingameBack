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

import com.byblos.evaluation.evaluationservice.dtos.PrmAccessDto;
import com.byblos.evaluation.evaluationservice.mappers.PrmAccessMapper;
import com.byblos.evaluation.evaluationservice.models.PrmAccess;
import com.byblos.evaluation.evaluationservice.services.PrmAccessService;

@RestController
@RequestMapping("/access")
public class PrmAccessController {
	@Autowired
	private PrmAccessService prmAccessService;
	@Autowired
	private PrmAccessMapper prmAccessMapper;

//get list access
	@GetMapping("/all")
	public ResponseEntity<List<PrmAccessDto>> findAll() {
		return ResponseEntity.ok(prmAccessService.findAll());
	}

//delete access
	@DeleteMapping("/delete/{id}")
	public void deleteAccess(@PathVariable(name = "id") Long id)

	{
		prmAccessService.deleteAccess(id);

	}

	@GetMapping("/find/{id}")
	public ResponseEntity<PrmAccessDto> findById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(prmAccessService.findById(id));
	}

	// add a new access
	@PostMapping("/create")
	public ResponseEntity<PrmAccessDto> create(@RequestBody PrmAccessDto prmAccessDto) {
		PrmAccess prmAccess = prmAccessService.save(prmAccessDto);
		prmAccessDto = prmAccessMapper.toPrmAccessDto(prmAccess);
		return ResponseEntity.status(HttpStatus.CREATED).body(prmAccessDto);
	}

	// upadte access
	@PutMapping("update/{id}")
	public ResponseEntity<PrmAccessDto> update(@RequestBody PrmAccessDto prmAccessDto,
			@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(prmAccessService.updateAccess(prmAccessDto, id));
	}
}
