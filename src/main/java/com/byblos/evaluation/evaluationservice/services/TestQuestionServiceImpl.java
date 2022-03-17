package com.byblos.evaluation.evaluationservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.byblos.evaluation.evaluationservice.dtos.TestQuestionDto;
import com.byblos.evaluation.evaluationservice.mappers.TestQuestionMapper;
import com.byblos.evaluation.evaluationservice.models.Question;
import com.byblos.evaluation.evaluationservice.models.Test;
import com.byblos.evaluation.evaluationservice.models.TestQuestion;
import com.byblos.evaluation.evaluationservice.repositories.TestQuestionRepo;
import com.byblos.evaluation.evaluationservice.repositories.TestRepo;

@Service
public class TestQuestionServiceImpl implements TestQuestionService {
	@Autowired
	private TestRepo testRepo;
	@Autowired
	private TestQuestionRepo testQuestionRepo;
	@Autowired
	private TestQuestionMapper testQuestionMapper;

	@Override
	public TestQuestion save(TestQuestionDto p) {
		TestQuestion testQuestion = new TestQuestion();
		TestQuestion t = testQuestionMapper.toTestQuestion(p);
		testQuestion.setQuestion(t.getQuestion());
		testQuestion.setId(t.getId());
		testQuestion.setTest(t.getTest());
		testQuestion.setResult(t.getResult());
		return testQuestionRepo.save(t);
	}

	@Override
	public void deleteTestQuestion(TestQuestion id) {
		TestQuestionDto pr = new TestQuestionDto();
		testQuestionRepo.delete(testQuestionMapper.toTestQuestion(pr));
	}

	@Override
	public List<TestQuestionDto> findAll() {

		return testQuestionMapper.toTestQuestionDtos(testQuestionRepo.findAll());
	}

	@Override
	public TestQuestionDto findById(TestQuestion id) {
		return null;
	}

	@Override
	public TestQuestionDto updateTestQuestion(TestQuestionDto testQuestionDto, TestQuestion id) {
		return null;
	}

	@Override
	public List<Optional<TestQuestion>> findByTestId(Long id) {
		return testQuestionRepo.findByTest(testRepo.findById(id));
	}

	@Override
	public TestQuestion findByQuestion(Question question) {
		return testQuestionRepo.findByQuestion(question);
	}

	@Override
	public List<TestQuestionDto> getTestQuestionResponses(Long idCompteEval) {
		return new ArrayList<>();
	}

	@Override
	public List<TestQuestionDto> findByTest(Long test) {
		Optional<Test> t = testRepo.findById(test);
		if (t.isPresent()) {
			return testQuestionMapper.toTestQuestionDtos(testQuestionRepo.findByTest(t.get()));
		} else {
			return new ArrayList<>();
		}
	}

}
