package com.byblos.evaluation.evaluationservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.byblos.evaluation.evaluationservice.dtos.PrmDifficultyDto;
import com.byblos.evaluation.evaluationservice.mappers.PrmDifficultyMapper;
import com.byblos.evaluation.evaluationservice.models.PrmDifficulty;
import com.byblos.evaluation.evaluationservice.repositories.PrmDifficultyRepo;

@Service
public class PrmDifficultyServiceImpl implements PrmDifficultyService {

	@Autowired
	private PrmDifficultyRepo prmDifficultyRepo;
	@Autowired
	private PrmDifficultyMapper prmDifficultyMapper;

	@Override
	public PrmDifficultyDto findById(Long id) {
		Optional<PrmDifficulty> p = prmDifficultyRepo.findById(id);
		if(p.isPresent()){
		return prmDifficultyMapper.toPrmDifficultyDto(p.get());
		}else{ 
			return null;
		}
	}


//create a new difficulty
	@Override
	public PrmDifficulty save(PrmDifficultyDto p) {
		return prmDifficultyRepo.save(prmDifficultyMapper.toPrmDifficulty(p));

	}
//delete difficulty
	@Override
	public void deleteDifficulty(Long id) {
		PrmDifficultyDto pr = new PrmDifficultyDto();
		pr.setId(id);
		prmDifficultyRepo.delete(prmDifficultyMapper.toPrmDifficulty(pr));
	}
//gt all difficulties
	@Override
	public List<PrmDifficultyDto> findAll() {

		return prmDifficultyMapper.toPrmDifficultyDtos(prmDifficultyRepo.findAll());
	}
//update difficulty
	@Override
	public PrmDifficultyDto updateDifficulty(PrmDifficultyDto prmDifficultyDto, Long id) {
		//		searching if prmDifficultyDto exist (getting its id)
		Optional<PrmDifficulty> pr = prmDifficultyRepo
				.findById(prmDifficultyMapper.toPrmDifficulty(prmDifficultyDto).getId());
		//if  it does not exist set a new id to this object to save it as a new object 
		if (!pr.isPresent())
			prmDifficultyDto.setId(id);
		//saving  (updating or creating the prmDifficultyDto)
		PrmDifficulty p = prmDifficultyRepo.save(prmDifficultyMapper.toPrmDifficulty(prmDifficultyDto));
		return prmDifficultyMapper.toPrmDifficultyDto(p);
	}
	@Override
	public PrmDifficulty getPrmDifficulty(String prmDifficultyName){
		return prmDifficultyRepo.findPrmDifficultyByDifficultyName(prmDifficultyName);
	}
	
	
}
