package com.byblos.evaluation.evaluationservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.byblos.evaluation.evaluationservice.dtos.PrmCategoryDto;
import com.byblos.evaluation.evaluationservice.mappers.PrmCategoryMapper;
import com.byblos.evaluation.evaluationservice.models.PrmCategory;
import com.byblos.evaluation.evaluationservice.repositories.PrmCategoryRepo;

@Service
public class PrmCategoryServiceImpl implements PrmCategoryService {



	@Autowired
	private PrmCategoryRepo prmCategoryRepo;
	@Autowired
	private PrmCategoryMapper prmCategoryMapper;

	@Override
	public PrmCategoryDto findById(Long id) {
		Optional<PrmCategory> p = prmCategoryRepo.findById(id);
		if(p.isPresent()){
		return prmCategoryMapper.toPrmCategoryDto(p.get());
		}else{ 
			return null;
		}
	}
	//create a new category
	@Override
	public PrmCategory save(PrmCategoryDto p) {
		return prmCategoryRepo.save(prmCategoryMapper.toPrmCategory(p));

	}
//delete category
	@Override
	public void deleteCategory(Long id) {
		PrmCategoryDto pr = new PrmCategoryDto();
		pr.setId(id);
		prmCategoryRepo.delete(prmCategoryMapper.toPrmCategory(pr));
	}
//get all categories
	@Override
	public List<PrmCategoryDto> findAll() {

		return prmCategoryMapper.toPrmCategoryDtos(prmCategoryRepo.findAll());
	}
//update category
	@Override
	public PrmCategoryDto updateCategory(PrmCategoryDto prmCategoryDto, Long id) {
		Optional<PrmCategory> pr = prmCategoryRepo
				.findById(prmCategoryMapper.toPrmCategory(prmCategoryDto).getId());
		if (!pr.isPresent())
			prmCategoryDto.setId(id);
		PrmCategory p = prmCategoryRepo.save(prmCategoryMapper.toPrmCategory(prmCategoryDto));
		return prmCategoryMapper.toPrmCategoryDto(p);
	}
	
	
}
