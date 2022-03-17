package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.UserDto;
import com.byblos.evaluation.evaluationservice.models.User;
import com.byblos.evaluation.evaluationservice.security.JwtResponse;


public interface AuthService {
    User registerUser(UserDto userr);
    JwtResponse authenticateUser(UserDto user);
    UserDto updateUser(UserDto user);
}
