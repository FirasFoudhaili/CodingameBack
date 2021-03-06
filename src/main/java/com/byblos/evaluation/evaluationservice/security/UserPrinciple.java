package com.byblos.evaluation.evaluationservice.security;

import com.byblos.evaluation.evaluationservice.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
@Data
@Service
public class UserPrinciple implements UserDetails {
    private static final long serialVersionUID = 1L;
 
    private Long id;
    private String email;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
     public UserPrinciple(Long id, String email, String password,
                         Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }
UserPrinciple(){
         
}
    public static UserPrinciple build(User user) {

        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority sp = new SimpleGrantedAuthority(user.getRole().name());
        authorities.add(sp);
        return new UserPrinciple(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }



    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }
}