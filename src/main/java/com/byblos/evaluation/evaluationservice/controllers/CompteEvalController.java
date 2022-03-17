package com.byblos.evaluation.evaluationservice.controllers;


import com.byblos.evaluation.evaluationservice.dtos.CompteEvalDto;
import com.byblos.evaluation.evaluationservice.mappers.CompteEvalMapper;
import com.byblos.evaluation.evaluationservice.models.CompteEval;
import com.byblos.evaluation.evaluationservice.services.CompteEvall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compteEval")
public class CompteEvalController {
    @Autowired
    CompteEvall compteEvallService;
    @Autowired
    CompteEvalMapper compteEvalMapper;

    @GetMapping("/all")
    public ResponseEntity<List<CompteEvalDto>> findAll() {
        return ResponseEntity.ok(compteEvallService.findAll());
    }
    @DeleteMapping("/delete/{id}")
    public void deleteAccess(@PathVariable(name = "id") Long id)

    {
        compteEvallService.deleteCompteEval(id);

    }
    @GetMapping("/find/{id}")
    public ResponseEntity <CompteEvalDto> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(compteEvallService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<CompteEvalDto> create(@RequestBody CompteEvalDto compteEvalDto) {

        CompteEval compteEval=compteEvallService.save(compteEvalDto);

        compteEvalDto=compteEvalMapper.toCompteEvalDto(compteEval);

        return ResponseEntity.status(HttpStatus.CREATED).body(compteEvalDto);
}
    @PutMapping("update/{id}")
    public  ResponseEntity <CompteEvalDto> update(@RequestBody CompteEvalDto compteEvalDto, @PathVariable (name="id")Long id){
        return  ResponseEntity.ok(compteEvallService.updateCompteEval(compteEvalDto,id));
    }
@GetMapping("findCompte/{email}")
    public  ResponseEntity<CompteEvalDto> findCompteEval(@PathVariable(name = "email") String email){
        return ResponseEntity.ok(compteEvallService.findCompteEval(email));
}


}
