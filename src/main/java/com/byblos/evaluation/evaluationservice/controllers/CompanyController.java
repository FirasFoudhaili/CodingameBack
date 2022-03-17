package com.byblos.evaluation.evaluationservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.byblos.evaluation.evaluationservice.dtos.CompanyDto;
import com.byblos.evaluation.evaluationservice.models.Company;
import com.byblos.evaluation.evaluationservice.services.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {
	@Autowired
	private CompanyService companyService;

	@GetMapping("/all")
	public ResponseEntity<List<CompanyDto>> findAll() {
		return ResponseEntity.ok(companyService.findAll());
	}

	@DeleteMapping("/delete/{id}")
	public void deleteTechno(@PathVariable(name = "id") Long id) {
		companyService.deleteCompany(id);

	}

	@GetMapping("findname/{email}")
	public ResponseEntity<String> findname(@PathVariable(name = "email") String email) {
		return ResponseEntity.ok(companyService.findname(email));
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<CompanyDto> findById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(companyService.findById(id));
	}

	@GetMapping("/findCompany")
	public ResponseEntity<CompanyDto> findCompany(@RequestParam String email) {
		return ResponseEntity.ok(companyService.findByEmail(email));
	}

	@GetMapping("/finduser/{user}")
	public ResponseEntity<CompanyDto> findByEmail(@PathVariable(name = "user") String user) {
		return ResponseEntity.ok(companyService.findByEmail(user));
	}

	@PostMapping("/create")
	public ResponseEntity<CompanyDto> create(@RequestBody CompanyDto companyDto) {

		companyDto = companyService.save(companyDto);

		if (companyDto != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(companyDto);
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@PutMapping("update")
	public ResponseEntity<CompanyDto> update(@RequestBody CompanyDto companyDto) {
		return ResponseEntity.ok(companyService.updateCompanyPassword(companyDto));
	}

//test if company is verified 
	@PostMapping("isverified")
	public ResponseEntity<Boolean> isVerified(@RequestBody CompanyDto companyDto) {
		return ResponseEntity.ok(companyService.isVerified(companyDto.getUser().getEmail()));
	}

//get companies per domain
	@GetMapping("/paginationCompany")
	public Page<Company> paginatedCompanies(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "500") int size,
			@RequestParam(name = "domain", required = false) String domain) {

		return companyService.paginatedcompanies(page, size, domain);

	}
}
