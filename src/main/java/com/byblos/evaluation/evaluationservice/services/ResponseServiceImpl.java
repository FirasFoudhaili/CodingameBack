package com.byblos.evaluation.evaluationservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.byblos.evaluation.evaluationservice.dtos.ResponseDto;
import com.byblos.evaluation.evaluationservice.mappers.ResponseMapper;
import com.byblos.evaluation.evaluationservice.models.Response;
import com.byblos.evaluation.evaluationservice.repositories.ResponseRepo;

@Service
public class ResponseServiceImpl implements ResponseService {
	@Autowired
	private ResponseRepo responseRepo;
	@Autowired
	private ResponseMapper responseMapper;

	@Override
	public ResponseDto findById(Long id) {

		Optional<Response> res = responseRepo.findById(id);
		if (res.isPresent()) {
			return responseMapper.toResponseDto(res.get());
		} else {
			return null;
		}
	}

	@Override
	public Response save(ResponseDto res) {
		return responseRepo.save(responseMapper.toResponse(res));

	}

	@Override
	public void deleteResponse(Long id) {
		ResponseDto pr = new ResponseDto();
		pr.setId(id);
		responseRepo.delete(responseMapper.toResponse(pr));
	}

//delete question's responses 
	@Override
	public void deleteResponseByQuestionId(Long id) {

		responseRepo.deleteResponseByQuestionId(id);
	}

	@Override
	public List<ResponseDto> findAll() {

		return responseMapper.toResponseDtos(responseRepo.findAll());
	}

//getAllResponses 
	@Override
	public List<ResponseDto> getResponses(Long id) {

		return responseMapper.toResponseDtos(responseRepo.getResponses(id));
	}

	// update Response
	@Override
	public ResponseDto updateResponse(ResponseDto responseDto, Long id) {
		Optional<Response> pr = responseRepo.findById(responseMapper.toResponse(responseDto).getId());
		if (!pr.isPresent())
			responseDto.setId(id);
		Response p = responseRepo.save(responseMapper.toResponse(responseDto));
		return responseMapper.toResponseDto(p);
	}

}
