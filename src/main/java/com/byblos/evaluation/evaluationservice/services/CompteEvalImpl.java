package com.byblos.evaluation.evaluationservice.services;
import com.byblos.evaluation.evaluationservice.dtos.CompteEvalDto;
import com.byblos.evaluation.evaluationservice.mappers.*;
import com.byblos.evaluation.evaluationservice.models.Company;
import com.byblos.evaluation.evaluationservice.models.CompteEval;
import com.byblos.evaluation.evaluationservice.models.Deal;
import com.byblos.evaluation.evaluationservice.models.User;
import com.byblos.evaluation.evaluationservice.repositories.CompanyRepo;
import com.byblos.evaluation.evaluationservice.repositories.CompteEvalRepo;
import com.byblos.evaluation.evaluationservice.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CompteEvalImpl  implements CompteEvall {
    @Autowired
    CompteEvalRepo compteEvalRepo;
    @Autowired
    CompteEvalMapper compteEvalMapper;
    @Autowired
    DealService dealService;
    @Autowired
    DealMapper dealMapper;
    @Autowired
    UserRepo userRepo;
    @Autowired
    CompanyRepo companyRepo;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CompanyMapper companyMapper;

    @Override
    public CompteEval save(CompteEvalDto compteEvalDto) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        Date nextYear = cal.getTime();
        compteEvalDto.setExpirationDate(nextYear);
        Deal d= dealService.findByDealName("free");
        compteEvalDto.setDeal(dealMapper.toDealDto(d));
        return compteEvalRepo.save(compteEvalMapper.toCompteEval(compteEvalDto));
    }

    @Override
    public void deleteCompteEval(Long id) {
        CompteEvalDto compteEvalDto = new CompteEvalDto();
        compteEvalDto.setId(id);
        compteEvalRepo.delete(compteEvalMapper.toCompteEval(compteEvalDto));

    }

    @Override
    public List<CompteEvalDto> findAll() {
        return compteEvalMapper.toCompteEvalDtos(compteEvalRepo.findAll());

    }

    @Override
    public CompteEvalDto findById(Long id) {
       Optional<CompteEval> ev=compteEvalRepo.findById(id);
       if(ev.isPresent()){
        return compteEvalMapper.toCompteEvalDto(ev.get());
       }
       else
       {
           return null;
       }
    }

    @Override
    public CompteEvalDto updateCompteEval(CompteEvalDto compteEvalDto, Long id) {
        Optional<CompteEval> compteEval = compteEvalRepo.findById(compteEvalMapper.toCompteEval(compteEvalDto).getId());
        if (!compteEval.isPresent())
            compteEvalDto.setId(id);
        CompteEval compteEval1 = compteEvalRepo.save(compteEvalMapper.toCompteEval(compteEvalDto));
        return compteEvalMapper.toCompteEvalDto(compteEval1);
    }
        
    @Override
        public CompteEvalDto findCompteEval(String email){
        
       Optional<User> user  = userRepo.findByEmail(email);
       if(user.isPresent()){
        User  user1=user.get();
        Optional<Company> company= companyRepo.findByUser(user1);
        if(company.isPresent()){
        Company company1= company.get();
        Optional<CompteEval> compteEval =compteEvalRepo.findByCompany(company1);
        if(compteEval.isPresent()){
        CompteEval compteEval1 =compteEval.get();
         return  compteEvalMapper.toCompteEvalDto(compteEval1);
        } } }
      
            return null;
        }

}

