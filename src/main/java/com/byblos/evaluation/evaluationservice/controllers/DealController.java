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

import com.byblos.evaluation.evaluationservice.dtos.DealDto;
import com.byblos.evaluation.evaluationservice.mappers.DealMapper;
import com.byblos.evaluation.evaluationservice.models.Deal;
import com.byblos.evaluation.evaluationservice.services.DealService;

@RestController
@RequestMapping("/deal")
public class DealController {
	@Autowired
	private DealService dealService;
	@Autowired
	private DealMapper dealMapper;

//get all deals 
	@GetMapping("/all")
	public ResponseEntity<List<DealDto>> findAll() {
		return ResponseEntity.ok(dealService.findAll());
	}

//delete deal
	@DeleteMapping("/delete/{id}")
	public void deleteAccess(@PathVariable(name = "id") Long id)

	{
		dealService.deleteDeal(id);

	}

	@GetMapping("/find/{id}")
	public ResponseEntity<DealDto> findById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(dealService.findById(id));
	}

	// add a new deal
	@PostMapping("/create")
	public ResponseEntity<DealDto> create(@RequestBody DealDto dealDto) {

		Deal deal = dealService.save(dealDto);
		dealDto = dealMapper.toDealDto(deal);
		return ResponseEntity.status(HttpStatus.CREATED).body(dealDto);
	}

//update deal
	@PutMapping("update/{id}")
	public ResponseEntity<DealDto> update(@RequestBody DealDto dealDto, @PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(dealService.updateDeal(dealDto, id));
	}
}
