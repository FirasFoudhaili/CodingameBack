package com.byblos.evaluation.evaluationservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.byblos.evaluation.evaluationservice.dtos.CompanyDto;
import com.byblos.evaluation.evaluationservice.dtos.QuestionConditionDto;
import com.byblos.evaluation.evaluationservice.dtos.QuestionDto;
import com.byblos.evaluation.evaluationservice.mappers.CompanyMapper;
import com.byblos.evaluation.evaluationservice.mappers.QuestionMapper;
import com.byblos.evaluation.evaluationservice.models.Company;
import com.byblos.evaluation.evaluationservice.models.QQuestion;
import com.byblos.evaluation.evaluationservice.models.Question;
import com.byblos.evaluation.evaluationservice.repositories.QuestionRepo;
import com.querydsl.core.BooleanBuilder;

@Service
public class QuestionServiceImpl implements QuestionService {
	@Autowired
	private QuestionRepo questionRepo;
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CompanyMapper companyMapper;

	@Override
	public QuestionDto findById(Long id) {
		Optional<Question> q = questionRepo.findById(id);
		if (q.isPresent()) {
			return questionMapper.toQuestionDto(q.get());
		} else {
			return null;
		}
	}

//create new question
	@Override
	public Question save(QuestionDto q) {
		return questionRepo.save(questionMapper.toQuestion(q));

	}

//delete question
	@Override
	public void deleteQuestion(Long id) {
		QuestionDto q = new QuestionDto();
		q.setQuestionid(id);
		questionRepo.delete(questionMapper.toQuestion(q));
	}

//find all questions
	@Override
	public List<QuestionDto> findAll() {

		return questionMapper.toQuestionDtos(questionRepo.findAll());
	}

//update question
	@Override
	public QuestionDto updateQuestion(QuestionDto questionDto, Long id) {
		Optional<Question> q = questionRepo.findById(questionMapper.toQuestion(questionDto).getQuestionid());
		if (!q.isPresent())
			questionDto.setQuestionid(id);
		Question qu = questionRepo.save(questionMapper.toQuestion(questionDto));
		return questionMapper.toQuestionDto(qu);
	}

//search question by parameters
	@Override
	public Page<Question> paginatedquestions(int page, int size, String statement, String level, String category,
			String difficulty, String technology) {

		BooleanBuilder booleanBuilder = new BooleanBuilder(QQuestion.question.isNotNull());

		// add parameter statement to booleanbuilder where statement=question.statement
		if (statement != null && !statement.equals("")) {
			booleanBuilder.and(QQuestion.question.statement.eq(statement));
		}
		// add parameter levelName to booleanbuilder where
		// levelName=question.prmLevel.levelName
		if (level != null && !level.equals("")) {
			booleanBuilder.and(QQuestion.question.prmLevel.levelName.eq(level));

		}
		// add parameter category to booleanbuilder where
		// category=question.prmCategory.categoryName
		if (category != null && !category.equals("")) {
			booleanBuilder.and(QQuestion.question.prmCategory.categoryName.eq(category));
		}
		if (difficulty != null && !difficulty.equals("")) {
			booleanBuilder.and(QQuestion.question.prmDifficulty.difficultyName.eq(difficulty));
		}

		if (technology != null && !technology.equals("")) {
			booleanBuilder.and(QQuestion.question.prmTechnology.technologyName.eq(technology));
		}

		// get all questions list that satisfied parameters sorted by questionid
		return questionRepo.findAll(booleanBuilder.getValue(),
				PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "questionid")));

	}

	@Override
	public List<Question> getAllQuestions(List<QuestionConditionDto> tab, String username) {
		CompanyDto c = companyService.findByEmail(username);
		List<Question> liste = new ArrayList<>();
		for (QuestionConditionDto conditionDto : tab) {
			liste.addAll(getQuestions(conditionDto));
		}
		Company cc = companyMapper.toCompany(c);
		companyService.updateTentativeness(cc);
		companyService.findByEmail(c.getUser().getEmail());
		return liste;

	}

	@Override
	public List<Question> getQuestions(QuestionConditionDto c) {
		return questionRepo.getAllQuestions(c.getPrmCategoryDto().getId(), c.getPrmDifficultyDto().getId(),
				c.getPrmTechnologyDto().getId(), c.getPrmLevelDto().getId(), c.getNbQuestions());
	}

}