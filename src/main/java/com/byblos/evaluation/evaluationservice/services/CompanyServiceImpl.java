package com.byblos.evaluation.evaluationservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.byblos.evaluation.evaluationservice.dtos.CompanyDto;
import com.byblos.evaluation.evaluationservice.mappers.CompanyMapper;
import com.byblos.evaluation.evaluationservice.mappers.UserMapper;
import com.byblos.evaluation.evaluationservice.models.Company;
import com.byblos.evaluation.evaluationservice.models.QCompany;
import com.byblos.evaluation.evaluationservice.models.User;
import com.byblos.evaluation.evaluationservice.repositories.CompanyRepo;
import com.byblos.evaluation.evaluationservice.repositories.UserRepo;
import com.querydsl.core.BooleanBuilder;

@Service
public class CompanyServiceImpl implements CompanyService {
	@Autowired
	private CompanyRepo companyRepo;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserRepo userRepo;

	@Override
	public CompanyDto save(CompanyDto p) {

		return companyMapper.toCompanyDto(companyRepo.save(companyMapper.toCompany(p)));

	}

	@Override
//search companies by domain
	public Page<Company> paginatedcompanies(int page, int size, String domain) {
		// create a new object boolean builder
		BooleanBuilder booleanBuilder = new BooleanBuilder(QCompany.company.isNotNull());

//check if domain not null add to boolean builder the company domain where company.domain equals to
		// domain
		if (domain != null && !domain.equals("")) {
			booleanBuilder.and(QCompany.company.domain.eq(domain));
		}
//return the list of companies where comany domain=domain soted by id Asc
		return companyRepo.findAll(booleanBuilder.getValue(),
				PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id")));
	}

	@Override
	public void deleteCompany(Long id) {
		// search company by id
		Optional<Company> company = companyRepo.findById(id);
		if (company.isPresent()) {
			// if company is present get user object
			User user = company.get().getUser();
			// delete company
			companyRepo.delete(company.get());
			// delete user
			userRepo.delete(user);
		}
	}

	@Override
	public List<CompanyDto> findAll() {

		return companyMapper.toCompanyDtos(companyRepo.findAll());
	}

	@Override
	public CompanyDto findById(Long id) {

		Optional<Company> p = companyRepo.findById(id);
		if (p.isPresent()) {
			return companyMapper.toCompanyDto(p.get());
		} else {
			return null;
		}
	}

	@Override
	public CompanyDto updateCompanyPassword(CompanyDto companyDto) { // get company By user
		Optional<Company> pr = companyRepo.findByUser(companyMapper.toCompany(companyDto).getUser());
		if (pr.isPresent()) {
			// set isVerfied=true
			pr.get().setIsVerified(true);
			// set company User
			pr.get().setUser(userMapper.toUser(companyDto.getUser()));
			// save company
			Company p = companyRepo.save(pr.get());
			return companyMapper.toCompanyDto(p);
		} else {
			return null;
		}
	}

	@Override
	public CompanyDto findByEmail(String email) {

		Optional<User> user = userRepo.findByEmail(email);
		if (user.isPresent()) {
			User user1 = user.get();
			Optional<Company> company = companyRepo.findByUser(user1);
			if (company.isPresent()) {
				Company company1 = company.get();
				return companyMapper.toCompanyDto(company1);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	// check if the company is verified
	public Boolean isVerified(String username) {
		// get user by email
		Optional<User> user = userRepo.findByEmail(username);
		if (user.isPresent()) {
			// get company by user
			Optional<Company> company = companyRepo.findByUser(user.get());
			if (company.isPresent()) {
				return company.get().getIsVerified();
			}
		}

		return false;
	}

//find name of company 
	@Override
	public String findname(String email) {
		// find user by email
		Optional<User> user = userRepo.findByEmail(email);
		if (user.isPresent()) {
			// find company by user
			Optional<Company> company = companyRepo.findByUser(user.get());
			if (company.isPresent()) {
				// get name of company
				return company.get().getName();
			}
		}
		return null;
	}

	// update value of tentativeness (+1)
	@Override
	public void updateTentativeness(Company c) {
		c.setTentativeness(c.getTentativeness() + 1);
		companyRepo.save(c);
	}
}
