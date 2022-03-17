package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.PackDto;
import com.byblos.evaluation.evaluationservice.mappers.PackMapper;
import com.byblos.evaluation.evaluationservice.mappers.UserMapper;
import com.byblos.evaluation.evaluationservice.models.Company;
import com.byblos.evaluation.evaluationservice.models.Pack;
import com.byblos.evaluation.evaluationservice.models.User;
import com.byblos.evaluation.evaluationservice.repositories.CompanyRepo;
import com.byblos.evaluation.evaluationservice.repositories.PackRepo;
import com.byblos.evaluation.evaluationservice.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PackServiceImpl  implements PackService{
    @Autowired
    private PackRepo packRepo;
    @Autowired
    private PackMapper packMapper;

    @Autowired
    UserRepo userRepo;
    @Autowired
    CompanyRepo companyRepo;
    @Autowired
    UserMapper userMapper;

    @Override
    public PackDto findById(Long id) {
        
        Optional<Pack >p = packRepo.findById(id);
        if(p.isPresent())
        {
            return packMapper.toPackDto(p.get());
        }
        else{ 
            return null;
        }
    }

    @Override
    public Pack save(PackDto p) {
        return packRepo.save(packMapper.toPack(p));

    }

    @Override
    public void deletePack(Long id) {
        PackDto pr = new PackDto();
        pr.setId(id);
        packRepo.delete(packMapper.toPack(pr));
    }

    @Override
    public List<PackDto> findAll() {

        return packMapper.toPackDtos(packRepo.findAll());
    }

    @Override
    public PackDto findPackByEmail(String username){
        Optional<User> user=userRepo.findByEmail(username);
        if(user.isPresent()){
        Optional<Company> company=companyRepo.findByUser(user.get());
        if(company.isPresent()){
        Optional<Pack> pack=packRepo.findByCompany(company.get());
        if(pack.isPresent())
        {
            return  packMapper.toPackDto(pack.get());
        } }
        }
        return null;
    }

    @Override
    public PackDto updatePack(PackDto packDto, Long id) {
        Optional<Pack> pr = packRepo
                .findById(packMapper.toPack(packDto).getId());
        if (!pr.isPresent())
            packDto.setId(id);
        Pack p = packRepo.save(packMapper.toPack(packDto));
        return packMapper.toPackDto(p);
    }
    @Override
    public List<Pack>getAllPacks(Long idCompany){
        return packRepo.getAllPacks(idCompany);
    }
    @Override
    public List<Pack> findByName(String name){
        return packRepo.findByName(name);
    }
    
}
