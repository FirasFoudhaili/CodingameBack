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

import com.byblos.evaluation.evaluationservice.dtos.PrmCategoryDto;
import com.byblos.evaluation.evaluationservice.mappers.PrmCategoryMapper;
import com.byblos.evaluation.evaluationservice.models.PrmCategory;
import com.byblos.evaluation.evaluationservice.services.PrmCategoryService;

@RestController
@RequestMapping("/category")
public class PrmCategoryController {
	@Autowired
	private PrmCategoryService prmCategoryService;
	@Autowired
	private PrmCategoryMapper prmCategoryMapper;

	@GetMapping("/all")
	public ResponseEntity<List<PrmCategoryDto>> findAll() {
		return ResponseEntity.ok(prmCategoryService.findAll());
	}

	@DeleteMapping("/delete/{id}")
	public void deleteUser(@PathVariable(name = "id") Long id)

	{
		prmCategoryService.deleteCategory(id);

	}

	@GetMapping("/find/{id}")
	public ResponseEntity<PrmCategoryDto> findById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(prmCategoryService.findById(id));
	}

	@PostMapping("/create")
	public ResponseEntity<PrmCategoryDto> create(@RequestBody PrmCategoryDto prmCategoryDto) {

		PrmCategory prmCategory = prmCategoryService.save(prmCategoryDto);
		prmCategoryDto = prmCategoryMapper.toPrmCategoryDto(prmCategory);

		return ResponseEntity.status(HttpStatus.CREATED).body(prmCategoryDto);
	}

	@PutMapping("update/{id}")
	public ResponseEntity<PrmCategoryDto> update(@RequestBody PrmCategoryDto prmCategoryDto,
			@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(prmCategoryService.updateCategory(prmCategoryDto, id));
	}
}
