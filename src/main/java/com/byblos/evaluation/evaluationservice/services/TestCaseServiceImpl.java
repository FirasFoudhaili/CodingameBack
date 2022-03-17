package com.byblos.evaluation.evaluationservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.byblos.evaluation.evaluationservice.dtos.TestCaseDto;
import com.byblos.evaluation.evaluationservice.mappers.TestCaseMapper;
import com.byblos.evaluation.evaluationservice.models.TestCase;
import com.byblos.evaluation.evaluationservice.repositories.TestCaseRepo;

@Service
public class TestCaseServiceImpl implements TestCaseService {

	@Autowired
	private TestCaseRepo testCaseRepo;
	
	@Autowired
	private TestCaseMapper testCaseMapper;
	
	@Override
	public TestCaseDto findById(Long id) {
		Optional<TestCase> tc = testCaseRepo.findById(id);
		if (tc.isPresent()) {
			return testCaseMapper.toTestCaseDto(tc.get());
		} else {
			return null;
		}
	}
	
	@Override
	public TestCase save(TestCaseDto tc) {
		return testCaseRepo.save(testCaseMapper.toTestCase(tc));

	}
	
	@Override
	public void deleteTestCase(Long id) {
		TestCaseDto tc = new TestCaseDto();
		tc.setTestid(id);
		testCaseRepo.delete(testCaseMapper.toTestCase(tc));
	}
	
	@Override
	public List<TestCaseDto> findAll() {

		return testCaseMapper.toTestCaseDtos(testCaseRepo.findAll());
	}
	
	@Override
	public TestCaseDto updateTestCase(TestCaseDto testCaseDto, Long id) {
		Optional<TestCase> tc = testCaseRepo.findById(testCaseMapper.toTestCase(testCaseDto).getTestid());
		if (!tc.isPresent())
			testCaseDto.setTestid(id);
		TestCase t = testCaseRepo.save(testCaseMapper.toTestCase(testCaseDto));
		return testCaseMapper.toTestCaseDto(t);
	}
	
	
	
	
	
	

	
	
	
	
	
	
	
}
