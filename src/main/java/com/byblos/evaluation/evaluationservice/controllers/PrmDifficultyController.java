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

import com.byblos.evaluation.evaluationservice.dtos.PrmDifficultyDto;
import com.byblos.evaluation.evaluationservice.mappers.PrmDifficultyMapper;
import com.byblos.evaluation.evaluationservice.models.PrmDifficulty;
import com.byblos.evaluation.evaluationservice.services.PrmDifficultyService;

@RestController
@RequestMapping("difficulty")
public class PrmDifficultyController {
	@Autowired
	private PrmDifficultyService prmDifficultyService;
	@Autowired
	private PrmDifficultyMapper prmDifficultyMapper;

	// get all difficulties
	@GetMapping("/all")
	public ResponseEntity<List<PrmDifficultyDto>> findAll() {
		return ResponseEntity.ok(prmDifficultyService.findAll());
	}

	// delete difficulty
	@DeleteMapping("/delete/{id}")
	public void deleteDifficulty(@PathVariable(name = "id") Long id)

	{
		prmDifficultyService.deleteDifficulty(id);

	}

	@GetMapping("/find/{id}")
	public ResponseEntity<PrmDifficultyDto> findById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(prmDifficultyService.findById(id));
	}

	// add a new difficulty
	@PostMapping("/create")
	public ResponseEntity<PrmDifficultyDto> create(@RequestBody PrmDifficultyDto prmDifficultyDto) {

		PrmDifficulty prmDifficulty = prmDifficultyService.save(prmDifficultyDto);

		prmDifficultyDto = prmDifficultyMapper.toPrmDifficultyDto(prmDifficulty);
		if (prmDifficultyDto != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(prmDifficultyDto);
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	// update difficulty
	@PutMapping("update/{id}")
	public ResponseEntity<PrmDifficultyDto> update(@RequestBody PrmDifficultyDto prmDifficultyDto,
			@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(prmDifficultyService.updateDifficulty(prmDifficultyDto, id));
	}
}
