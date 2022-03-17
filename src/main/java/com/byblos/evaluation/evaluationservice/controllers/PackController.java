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

import com.byblos.evaluation.evaluationservice.dtos.PackDto;
import com.byblos.evaluation.evaluationservice.mappers.PackMapper;
import com.byblos.evaluation.evaluationservice.models.Pack;
import com.byblos.evaluation.evaluationservice.services.PackService;

@RestController
@RequestMapping("/pack")
public class PackController {
	@Autowired
	private PackService packService;
	@Autowired
	private PackMapper packMapper;

	@GetMapping("/all")
	public ResponseEntity<List<PackDto>> findAll() {
		return ResponseEntity.ok(packService.findAll());
	}

	@GetMapping("/all/{idCompany}")
	public ResponseEntity<List<Pack>> getAllPacks(@PathVariable(name = "idCompany") Long idCompany) {
		return ResponseEntity.ok(packService.getAllPacks(idCompany));
	}

	@GetMapping("/findByName")
	public ResponseEntity<List<Pack>> findByName(@RequestParam String name) {
		return ResponseEntity.ok(packService.findByName(name));
	}

	@DeleteMapping("/delete/{id}")
	public void deleteAccess(@PathVariable(name = "id") Long id)

	{
		packService.deletePack(id);

	}

	@GetMapping("/find/{id}")
	public ResponseEntity<PackDto> findById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(packService.findById(id));
	}

	@GetMapping("/find/user/{username}")
	public ResponseEntity<PackDto> findByUsername(@PathVariable(name = "username") String username) {
		return ResponseEntity.ok(packService.findPackByEmail(username));
	}

	@PostMapping("/create")
	public ResponseEntity<PackDto> create(@RequestBody PackDto packDto) {

		Pack pack = packService.save(packDto);

		packDto = packMapper.toPackDto(pack);

		return ResponseEntity.status(HttpStatus.CREATED).body(packDto);
	}

	@PutMapping("update/{id}")
	public ResponseEntity<PackDto> update(@RequestBody PackDto packDto, @PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(packService.updatePack(packDto, id));
	}
}
