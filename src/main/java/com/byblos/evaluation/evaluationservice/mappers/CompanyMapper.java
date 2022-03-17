package com.byblos.evaluation.evaluationservice.mappers;

import com.byblos.evaluation.evaluationservice.dtos.CompanyDto;
import com.byblos.evaluation.evaluationservice.models.Company;
import org.mapstruct.*;


import java.util.List;

@Mapper(componentModel="spring")
public interface CompanyMapper {

    CompanyDto toCompanyDto(Company company);


    List<CompanyDto> toCompanyDtos(List<Company> companies);

    Company toCompany(CompanyDto companyDto);
}
