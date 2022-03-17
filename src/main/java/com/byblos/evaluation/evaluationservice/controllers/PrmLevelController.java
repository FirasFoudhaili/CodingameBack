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
import org.springframework.web.bind.annotation.RestController;

import com.byblos.evaluation.evaluationservice.dtos.PrmLevelDto;
import com.byblos.evaluation.evaluationservice.mappers.PrmLevelMapper;
import com.byblos.evaluation.evaluationservice.models.PrmLevel;
import com.byblos.evaluation.evaluationservice.services.PrmLevelService;

@RestController
@RequestMapping("/level")
public class PrmLevelController {
	@Autowired
	private PrmLevelService prmLevelService;
	@Autowired
	private PrmLevelMapper prmLevelMapper;

	// display all levels
	@GetMapping("/all")
	public ResponseEntity<List<PrmLevelDto>> findAll() {
		return ResponseEntity.ok(prmLevelService.findAll());
	}

	// delete prmLevel
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<PrmLevelDto> deleteUser(@PathVariable(name = "id") Long id)

	{
		PrmLevelDto l = prmLevelService.findById(id);
		prmLevelService.deleteLevel(id);
		return ResponseEntity.ok(l);

	}

	@GetMapping("/find/{id}")
	public ResponseEntity<PrmLevelDto> findById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(prmLevelService.findById(id));
	}

	// add a new prmLevel
	@PostMapping("/create")
	public ResponseEntity<PrmLevelDto> create(@RequestBody PrmLevelDto prmLevelDto) {

		PrmLevel prmLevel = prmLevelService.save(prmLevelDto);
		return ResponseEntity.ok().body(prmLevelMapper.toPrmLevelDto(prmLevel));
	}

	// update level
	@PutMapping("update/{id}")
	public ResponseEntity<PrmLevelDto> update(@RequestBody PrmLevelDto prmLevelDto,
			@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(prmLevelService.updateLevel(prmLevelDto, id));
	}
}
