package com.byblos.evaluation.evaluationservice.dtos;

import com.byblos.evaluation.evaluationservice.models.RoleName;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String email;
    private String password;
    private RoleName role;

}
