package com.byblos.evaluation.evaluationservice.services;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.byblos.evaluation.evaluationservice.dtos.ResultDto;
import com.byblos.evaluation.evaluationservice.dtos.TestDto;
import com.byblos.evaluation.evaluationservice.dtos.TestResponsesDto;
import com.byblos.evaluation.evaluationservice.mappers.ResultMapper;
import com.byblos.evaluation.evaluationservice.mappers.TestMapper;
import com.byblos.evaluation.evaluationservice.models.Question;
import com.byblos.evaluation.evaluationservice.models.Response;
import com.byblos.evaluation.evaluationservice.models.Result;
import com.byblos.evaluation.evaluationservice.models.Test;
import com.byblos.evaluation.evaluationservice.models.TestQuestion;
import com.byblos.evaluation.evaluationservice.models.TestQuestionKeys;
import com.byblos.evaluation.evaluationservice.repositories.ResponseRepo;
import com.byblos.evaluation.evaluationservice.repositories.ResultRepo;
import com.byblos.evaluation.evaluationservice.repositories.TestQuestionRepo;
import com.byblos.evaluation.evaluationservice.repositories.TestRepo;

@Service
public class ResultServiceImpl implements ResultService {
	@Autowired
	private ResultRepo resultRepo;
	@Autowired
	TestMapper testMapper;
	@Autowired
	private TestService testService;
	@Autowired
	private ResponseRepo responseRepo;
	@Autowired
	private TestRepo testRepo;
	@Autowired
	private TestQuestionRepo testQuestionRepo;
	@Autowired
	private ResultMapper resultMapper;
	@Autowired
	private ScoreParDifficultyService scoreParDifficultyService;
	@Autowired
	private ScoreParCategoryService scoreParCategoryService;
	@Autowired
	private ScoreParTechnologyService scoreParTechnologyService;

	@Override
	public Result findResultByTest(TestDto testdto) {
		return resultRepo.findResultByTest(testMapper.toTest(testdto));
	}

	@Override
	public Boolean existsByTestAndResponse(Long test, Long response) {
		Optional<Test> t = testRepo.findById(test);
		Optional<Response> r = responseRepo.findById(response);
		if (t.isPresent() && r.isPresent()) {
			return resultRepo.findByTestAndResponse(t.get(), r.get()).isPresent();
		}
		return false;
	}

	@Override
	public ResultDto findById(Long id) {
		Optional<Result> res = resultRepo.findById(id);
		if (res.isPresent()) {
			return resultMapper.toResultDto(res.get());
		} else {
			return null;
		}
	}

	@Override
	public TestResponsesDto save(TestResponsesDto res) {
		int length = testService.findByTestId(res.getTest().getTestid()).size();
		Double score;
		TestQuestionKeys testQuestionKeys = new TestQuestionKeys();
		testQuestionKeys.setQuestionid(res.getQuestion().getQuestionid());
		testQuestionKeys.setTestid(res.getTest().getTestid());
		TestQuestion testQuestion = testQuestionRepo.findById(testQuestionKeys).get();
		List<Response> response = res.getResponses();
		Question question = res.getQuestion();
		response.forEach(resp -> {
			Result result = new Result();
			result.setQuestion(res.getQuestion());
			result.setTest(res.getTest());
			result.setResponse(resp);
			resultRepo.save(result);
		});
		List<Response> response2 = responseRepo.getResponses(question.getQuestionid());
		testQuestion.setResult(compareResponses(response, response2));
		testQuestionRepo.save(testQuestion);
		boolean l = length == res.getIndex();
		boolean to = res.getTimeOver();
		if (l || to) {
			AtomicInteger counter = new AtomicInteger();
			testService.findByTestId(res.getTest().getTestid()).forEach(testQuestion1 -> {
				if (testQuestion1.getResult() == 1L) {
					counter.getAndIncrement();
				}

			});
			score = Double.valueOf(counter.get()) * 100 / length;

			res.getTest().setTotalScore(score);
			res.getTest().setDone(true);
			res.getTest().setPassageDuration(res.getTestDuration());
			Test test = testRepo.save(res.getTest());
			scoreParCategoryService.calculScoreParDifficulty(test.getTestid());
			scoreParDifficultyService.calculScoreParDifficulty(test.getTestid());
			scoreParTechnologyService.calculScoreParTechnology(test.getTestid());
		}
		return res;
	}

	private Long compareResponses(List<Response> resp1, List<Response> resp2) {
		Long resp;
		for (int i = resp2.size() - 1; i >= 0; i--) {
			if (resp2.get(i).getCorrect() == null || !resp2.get(i).getCorrect())
				resp2.remove(i);
		}
		if (resp2.size() != resp1.size())
			resp = 0L;
		else {
			resp = 1L;
			for (int i = 0; i < resp2.size(); i++) {
				if (!resp2.get(i).getId().equals(resp1.get(i).getId()))
					resp = 0L;
			}
		}
		return resp;
	}

	@Override
	public void deleteResult(Long id) {
		ResultDto pr = new ResultDto();
		pr.setId(id);
		resultRepo.delete(resultMapper.toResult(pr));
	}

	@Override
	public List<ResultDto> findAll() {
		return resultMapper.toResultDtos(resultRepo.findAll());
	}

	@Override
	public ResultDto updateResult(ResultDto resultDto, Long id) {
		Optional<Result> pr = resultRepo.findById(resultMapper.toResult(resultDto).getId());
		if (!pr.isPresent())
			resultDto.setId(id);
		Result p = resultRepo.save(resultMapper.toResult(resultDto));
		return resultMapper.toResultDto(p);
	}

}
