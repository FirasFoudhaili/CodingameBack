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

import com.byblos.evaluation.evaluationservice.dtos.PrmTechnologyDto;
import com.byblos.evaluation.evaluationservice.mappers.PrmTechnologyMapper;
import com.byblos.evaluation.evaluationservice.models.PrmTechnology;
import com.byblos.evaluation.evaluationservice.services.PrmTechnoService;

@RestController
@RequestMapping("/techno")
public class PrmTechnoController {
	@Autowired
	private PrmTechnoService prmTechnoService;

	@Autowired
	private PrmTechnologyMapper prmTechnologyMapper;

	// get all technologies
	@GetMapping("/all")
	public ResponseEntity<List<PrmTechnologyDto>> findAll() {
		return ResponseEntity.ok(prmTechnoService.findAll());
	}

	// delete technology
	@DeleteMapping("/delete/{id}")
	public void deleteTechno(@PathVariable(name = "id") Long id) {
		prmTechnoService.deleteTechno(id);

	}

	@GetMapping("/find/{id}")
	public ResponseEntity<PrmTechnologyDto> findById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(prmTechnoService.findById(id));
	}

	// add a new technology
	@PostMapping("/create")
	public ResponseEntity<PrmTechnologyDto> create(@RequestBody PrmTechnologyDto prmTechnologyDto) {

		PrmTechnology prmTechnology = prmTechnoService.save(prmTechnologyDto);

		prmTechnologyDto = prmTechnologyMapper.toPrmTechnologyDto(prmTechnology);
		if (prmTechnologyDto != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(prmTechnologyDto);
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	// update technology
	@PutMapping("update/{id}")
	public ResponseEntity<PrmTechnologyDto> update(@RequestBody PrmTechnologyDto prmTechnologyDto,
			@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(prmTechnoService.updateTechno(prmTechnologyDto, id));
	}
}
