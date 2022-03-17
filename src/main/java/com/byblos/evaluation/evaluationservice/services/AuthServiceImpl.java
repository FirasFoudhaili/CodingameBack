package com.byblos.evaluation.evaluationservice.services;

import com.byblos.evaluation.evaluationservice.dtos.UserDto;
import com.byblos.evaluation.evaluationservice.mappers.UserMapper;
import com.byblos.evaluation.evaluationservice.models.User;
import com.byblos.evaluation.evaluationservice.repositories.UserRepo;
import com.byblos.evaluation.evaluationservice.security.JwtProvider;
import com.byblos.evaluation.evaluationservice.security.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserMapper userMapper;
    @Override
    // registration user
    public User registerUser(@Valid @RequestBody UserDto userr)  {
        // if email of user exist returns null 
          Boolean trouve=userRepo.existsByEmail(userr.getEmail()) ; 
        if(trouve.equals(true)) {
           return null;
        }
        
        // else create a new object user and save it 
        User user=new User();
        user.setEmail(userr.getEmail());
        user.setPassword(encoder.encode(userr.getPassword()));	
        user.setRole(userr.getRole());
        userRepo.save(user);
        return user;
    }

    @Override
    //authentication method
    public JwtResponse authenticateUser(@Valid @RequestBody UserDto user) {
//construction of a new object usernamePasswordAuthenticationToken where parameters are email and password of user 
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                );
        // the oject authentication as a result of method authenticate of class authenticationManager that takes 
        //usernamePasswordAuthenticationToken as parameter
        Authentication authentication = authenticationManager.authenticate(

                usernamePasswordAuthenticationToken
        );
        //set the authentication attribute of the context of the class SecurityclassHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //generating token 
        String jwt = jwtProvider.generateJwtToken(authentication);
        //get Principal that contains (username,password,authorities) of the object authentication
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //returns jwtResponse that contains  jwt ,username(email) and authorities
        return new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities());

    }

    @Override
    //update user password 
    public UserDto updateUser(UserDto user) {
        //find user by email 
        Optional<User> pr= userRepo.findByEmail(user.getEmail());
        //if user exist update password and return user else return null 
        if (pr.isPresent()) {
            User nu=pr.get();
            nu.setPassword(encoder.encode(user.getPassword()));
            User p=userRepo.save(nu);
        return userMapper.toUserDto(p);
        }
        return null;
    }
}