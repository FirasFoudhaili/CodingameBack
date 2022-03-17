package com.byblos.evaluation.evaluationservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.byblos.evaluation.evaluationservice.dtos.CandidateDto;
import com.byblos.evaluation.evaluationservice.dtos.CompanyDto;
import com.byblos.evaluation.evaluationservice.mappers.CandidateMapper;
import com.byblos.evaluation.evaluationservice.mappers.CompanyMapper;
import com.byblos.evaluation.evaluationservice.mappers.UserMapper;
import com.byblos.evaluation.evaluationservice.models.Candidate;
import com.byblos.evaluation.evaluationservice.models.Company;
import com.byblos.evaluation.evaluationservice.models.User;
import com.byblos.evaluation.evaluationservice.repositories.CandidateRepo;
import com.byblos.evaluation.evaluationservice.repositories.UserRepo;

@Service
public class CandidateServiceImpl implements CandidateService {
	@Autowired
	private CandidateRepo candidateRepo;
	@Autowired
	private CandidateMapper candidateMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	CompanyService companyService;
	@Autowired
	CompanyMapper companyMapper;

	@Override
	public CandidateDto save(CandidateDto p) {
		Candidate candidate1 = candidateMapper.toCandidate(p);
		Candidate candidate = candidateRepo.save(candidate1);
		return candidateMapper.toCandidateDto(candidate);
	}

	@Override
	public void deleteCandidate(Long id) {
		CandidateDto pr = new CandidateDto();
		pr.setId(id);
		Optional<Candidate> candidate = candidateRepo.findById(id);
		if (candidate.isPresent()) {
			User user = candidate.get().getUser();
			candidateRepo.delete(candidateMapper.toCandidate(pr));
			userRepo.delete(user);
		}
	}

	@Override
	public List<CandidateDto> findAll() {

		return candidateMapper.toCandidateDtos(candidateRepo.findAll());
	}

	@Override
	public CandidateDto findById(Long id) {
		Optional<Candidate> p = candidateRepo.findById(id);
		if (p.isPresent()) {
			return candidateMapper.toCandidateDto(p.get());
		} else {
			return null;
		}
	}

	@Override
	public CandidateDto updateCandidate(CandidateDto candidateDto, Long id) {
		Optional<Candidate> pr = candidateRepo.findById(candidateMapper.toCandidate(candidateDto).getId());
		if (!pr.isPresent())
			candidateDto.setId(id);
		Candidate p = candidateRepo.save(candidateMapper.toCandidate(candidateDto));
		return candidateMapper.toCandidateDto(p);
	}

	@Override
	public CandidateDto signupCandidate(@RequestBody CandidateDto candidateDto) {

		CompanyDto company = companyService.findByEmail(candidateDto.getCompany().getUser().getEmail());
		candidateDto.setCompany(company);
		return this.save(candidateDto);

	}

	@Override
	public CandidateDto updateCandidatePassword(CandidateDto candidateDto) {
		Optional<Candidate> pr = candidateRepo.findByUser(candidateMapper.toCandidate(candidateDto).getUser());
		if (pr.isPresent()) {
			pr.get().setUser(userMapper.toUser(candidateDto.getUser()));
			Candidate p = candidateRepo.save(pr.get());
			return candidateMapper.toCandidateDto(p);
		} else {
			return null;
		}

	}

	@Override
	public List<CandidateDto> findByCompany(String username) {
		List<CandidateDto> listeCandidates;
		Company c = companyMapper.toCompany(companyService.findByEmail(username));
		listeCandidates = candidateMapper.toCandidateDtos(candidateRepo.findByCompany(c));
		return listeCandidates;

	}

}
