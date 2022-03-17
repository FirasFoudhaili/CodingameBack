package com.byblos.evaluation.evaluationservice.services;
import java.util.List;

import com.byblos.evaluation.evaluationservice.dtos.PrmCategoryDto;
import com.byblos.evaluation.evaluationservice.models.PrmCategory;

public interface PrmCategoryService {
	   PrmCategory save(PrmCategoryDto p);
	    void deleteCategory(Long id );
	    List<PrmCategoryDto> findAll();
	    PrmCategoryDto findById(Long id);
	    PrmCategoryDto updateCategory (PrmCategoryDto prmCategoryDto, Long id);
	    
}
