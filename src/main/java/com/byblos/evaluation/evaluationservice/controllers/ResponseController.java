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

import com.byblos.evaluation.evaluationservice.dtos.ResponseDto;
import com.byblos.evaluation.evaluationservice.mappers.ResponseMapper;
import com.byblos.evaluation.evaluationservice.models.Response;
import com.byblos.evaluation.evaluationservice.services.ResponseService;

@RestController
@RequestMapping("/response")
public class ResponseController {
	@Autowired
	private ResponseService responseService;

	@Autowired
	private ResponseMapper responseMapper;

	@GetMapping("/all")
	public ResponseEntity<List<ResponseDto>> findAll() {
		return ResponseEntity.ok(responseService.findAll());
	}

	@DeleteMapping("/delete/{id}")
	public void deleteResponse(@PathVariable(name = "id") Long id)

	{
		responseService.deleteResponse(id);

	}

	// delete question's responses
	@DeleteMapping("/delete/question/{id}")
	public void deleteResponseByQuestionId(@PathVariable(name = "id") Long id)

	{
		responseService.deleteResponseByQuestionId(id);

	}

	@GetMapping("/find/{id}")
	public ResponseEntity<ResponseDto> findById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(responseService.findById(id));
	}

	@GetMapping("/find/question/{id}")
	public ResponseEntity<List<ResponseDto>> findByQuestionId(@PathVariable(name = "id") Long id) {

		List<ResponseDto> responses = responseService.getResponses(id);

		return ResponseEntity.ok(responses);
	}

	@PostMapping("/create")
	public ResponseEntity<ResponseDto> create(@RequestBody ResponseDto responseDto) {

		Response res = responseService.save(responseDto);

		responseDto = responseMapper.toResponseDto(res);
		if (responseDto != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@PutMapping("update/{id}")
	public ResponseEntity<ResponseDto> update(@RequestBody ResponseDto responseDto,
			@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(responseService.updateResponse(responseDto, id));
	}
}
