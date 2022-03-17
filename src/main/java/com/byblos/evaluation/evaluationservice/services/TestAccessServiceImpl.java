package com.byblos.evaluation.evaluationservice.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.byblos.evaluation.evaluationservice.dtos.CompteEvalDto;
import com.byblos.evaluation.evaluationservice.dtos.TestAccessDto;
import com.byblos.evaluation.evaluationservice.mappers.CompteEvalMapper;
import com.byblos.evaluation.evaluationservice.mappers.TestAccessMapper;
import com.byblos.evaluation.evaluationservice.models.Company;
import com.byblos.evaluation.evaluationservice.models.CompteEval;
import com.byblos.evaluation.evaluationservice.models.TestAccess;
import com.byblos.evaluation.evaluationservice.models.User;
import com.byblos.evaluation.evaluationservice.repositories.CompanyRepo;
import com.byblos.evaluation.evaluationservice.repositories.CompteEvalRepo;
import com.byblos.evaluation.evaluationservice.repositories.TestAccessRepo;
import com.byblos.evaluation.evaluationservice.repositories.UserRepo;

@Service
public class TestAccessServiceImpl implements TestAccessService {
	@Autowired
	private CompanyRepo companyRepo;
	@Autowired
	private CompteEvalRepo compteEvalRepo;
	@Autowired
	private CompteEvalMapper compteEvalMapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private TestAccessRepo testAccessRepo;
	@Autowired
	private TestAccessMapper testAccessMapper;

	@Override
	public TestAccessDto findById(Long id) {
		Optional<TestAccess> p = testAccessRepo.findById(id);
		if (p.isPresent()) {
			return testAccessMapper.toTestAccessDto(p.get());
		} else {
			return null;
		}
	}

	@Override
	public TestAccess save(TestAccessDto p) {
		Calendar cal = Calendar.getInstance();
		p.setPurchaseDate(cal.getTime());
		return testAccessRepo.save(testAccessMapper.toTestAccess(p));

	}

	@Override
	public void deleteTestAccess(Long id) {
		TestAccessDto pr = new TestAccessDto();
		pr.setId(id);
		testAccessRepo.delete(testAccessMapper.toTestAccess(pr));
	}

	@Override
	public CompteEvalDto findCompteEvalByEmail(String username) {
		Optional<User> user = userRepo.findByEmail(username);
		if (user.isPresent()) {
			Optional<Company> company = companyRepo.findByUser(user.get());
			if (company.isPresent()) {
				Optional<CompteEval> compteEval = compteEvalRepo.findByCompany(company.get());
				if (compteEval.isPresent()) {
					return compteEvalMapper.toCompteEvalDto(compteEval.get());
				}
			}
		}
		return null;
	}

	@Override
	public List<TestAccessDto> findAll() {

		return testAccessMapper.toTestAccessDtos(testAccessRepo.findAll());
	}

	@Override
	public TestAccessDto updateTestAccess(TestAccessDto testAccessDto, Long id) {
		Optional<TestAccess> pr = testAccessRepo.findById(testAccessMapper.toTestAccess(testAccessDto).getId());
		if (!pr.isPresent())
			testAccessDto.setId(id);
		TestAccess p = testAccessRepo.save(testAccessMapper.toTestAccess(testAccessDto));
		return testAccessMapper.toTestAccessDto(p);
	}

	@Override
	public List<TestAccessDto> getTestAccessResponses(Long idCompteEval) {
		return testAccessMapper.toTestAccessDtos(testAccessRepo.getTestAccessResponses(idCompteEval));
	}

	@Override
	public int calculUnusedAccess(Long idCompteEval) {

		int totalAccess = 0;
		int totalused = 0;
		List<TestAccessDto> liste = getTestAccessResponses(idCompteEval);
		ArrayList<TestAccessDto> list = new ArrayList<>();
		list.addAll(liste);
		for (int i = 0; i < list.size(); i++) {
			totalAccess += list.get(i).getPrmAccess().getValue();
			totalused += list.get(i).getUsedAccessNumber();
		}
		return totalAccess - totalused;
	}

}
