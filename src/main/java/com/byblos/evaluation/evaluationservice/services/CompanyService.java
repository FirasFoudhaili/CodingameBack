package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.CompanyDto;
import com.byblos.evaluation.evaluationservice.models.Company;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CompanyService {
    CompanyDto save(CompanyDto p);
    void deleteCompany(Long id );
    List<CompanyDto> findAll();
    CompanyDto findById(Long id);
    CompanyDto findByEmail(String username);
    CompanyDto updateCompanyPassword (CompanyDto companyDto);
    Page<Company> paginatedcompanies(int page, int size, String domain);
    Boolean isVerified(String username);
    String findname(String email);
    void  updateTentativeness(Company c);
}
