package com.byblos.evaluation.evaluationservice.controllers;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.byblos.evaluation.evaluationservice.dtos.BoDto;
import com.byblos.evaluation.evaluationservice.dtos.CandidateDto;
import com.byblos.evaluation.evaluationservice.dtos.CompanyDto;
import com.byblos.evaluation.evaluationservice.dtos.CompteEvalDto;
import com.byblos.evaluation.evaluationservice.dtos.UserDto;
import com.byblos.evaluation.evaluationservice.mappers.UserMapper;
import com.byblos.evaluation.evaluationservice.models.Bo;
import com.byblos.evaluation.evaluationservice.models.RoleName;
import com.byblos.evaluation.evaluationservice.models.User;
import com.byblos.evaluation.evaluationservice.services.AuthService;
import com.byblos.evaluation.evaluationservice.services.BoService;
import com.byblos.evaluation.evaluationservice.services.CandidateService;
import com.byblos.evaluation.evaluationservice.services.CompanyService;
import com.byblos.evaluation.evaluationservice.services.CompteEvall;
import com.byblos.evaluation.evaluationservice.services.MailSending;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	@Autowired
	private BoService boService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private MailSending mailSending;
	@Autowired
	private CompteEvall compteEvallService;
	
	@Autowired
	private CandidateService candidateService;

	@PutMapping("updateCompanyPassword")
	// if company is not verified ==>update password
	public ResponseEntity<CompanyDto> update(@RequestBody CompanyDto companyDto) {
		// update user (table user)
		UserDto user = authService.updateUser(companyDto.getUser());
		if (user == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		} else {
			// set companyDto user
			companyDto.setUser(user);
			// updating company password
			CompanyDto company = companyService.updateCompanyPassword(companyDto);
			// creating a new compteEval for company
			CompteEvalDto compteEvalDto = new CompteEvalDto();
			// set CompteEvalDto Company
			compteEvalDto.setCompany(company);
			// save compteEval
			compteEvallService.save(compteEvalDto);
			if (company == null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			} else {

				return new ResponseEntity(company, HttpStatus.OK);
			}
		}
	}

	@PostMapping("/signupCompany")
	public ResponseEntity registerCompany(@Valid @RequestBody CompanyDto companyDto) {
		// create a new userDto object that contains email and password to get not
		// encoding password
		// used to send email
		UserDto userDto = new UserDto();
		userDto.setEmail(companyDto.getUser().getEmail());
		userDto.setPassword(companyDto.getUser().getPassword());
		// setting role for CompanyDto user
		companyDto.getUser().setRole(RoleName.ROLE_COMPANY);
		// save user
		User user = authService.registerUser(companyDto.getUser());
		if (user == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		} else {
			// set the rest of companyDto attributes
			companyDto.setIsVerified(false);
			companyDto.setUser(userMapper.toUserDto(user));
			// save companyDto
			CompanyDto company = companyService.save(companyDto);

			if (company == null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			} else {
				// sending email and password and login url to company
				String newligne = System.getProperty("line.separator");
				String url = "http://localhost:8080/#/evaluation/login";
				String body = "Welcom to our platform \n your email is : " + userDto.getEmail()
						+ " \n your password is : " + userDto.getPassword()
						+ " \n use this link to login to your account is :" + newligne + url;
				try {
					mailSending.send(company.getUser().getEmail(), "Welcome", body);
					return new ResponseEntity(company, HttpStatus.OK);

				} catch (MessagingException e) {
					return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
				}
			}
		}
	}

	@PostMapping("/signupCandidate")
	public ResponseEntity registerCandidate(@RequestBody CandidateDto candidateDto) {
//set role candidate for candidateDto user object
		candidateDto.getUser().setRole(RoleName.ROLE_CANDIDATE);
		// register user
		User user = authService.registerUser(candidateDto.getUser());
		if (user == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		} else {
			// find CandidateDto company By email Company passed in comapgnyDto Object
			CompanyDto company = companyService.findByEmail(candidateDto.getCompany().getUser().getEmail());

			// set CandidateDto user
			candidateDto.setUser(userMapper.toUserDto(user));
			// set CandidateDto Company
			candidateDto.setCompany(company);
			// saving candidateDto
			CandidateDto candidate = candidateService.save(candidateDto);
			if (candidate == null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity(candidate, HttpStatus.OK);

			}
		}
	}

	@PostMapping("/signupBo")
	public ResponseEntity registerBo(@Valid @RequestBody BoDto boDto) {
		// set role of boDto user as Role_BO
		boDto.getUser().setRole(RoleName.ROLE_BO);
		// saving user
		User user = authService.registerUser(boDto.getUser());
		if (user == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		} else {
			// set User of boDto
			boDto.setUser(userMapper.toUserDto(user));
			// save bo
			Bo bo = boService.save(boDto);
			if (bo == null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity(bo, HttpStatus.OK);
			}
		}
	}

	@PostMapping("/signupExaminateur")
	public ResponseEntity registerExaminateur(@Valid @RequestBody BoDto examinateurDto) {
		// set role of examinatorDto user
		examinateurDto.getUser().setRole(RoleName.ROLE_EXAMINATEUR);
		// save user
		User user = authService.registerUser(examinateurDto.getUser());
		if (user == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		} else {
			// set examinatorDto user
			examinateurDto.setUser(userMapper.toUserDto(user));
			// save examinator
			Bo bo = boService.save(examinateurDto);
			if (bo == null) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity(bo, HttpStatus.OK);
			}
		}
	}

//Authentication method
	@PostMapping("/signin")
	public ResponseEntity authenticateUser(@Valid @RequestBody UserDto user) {
		return ResponseEntity.ok().body(authService.authenticateUser(user));
	}
}