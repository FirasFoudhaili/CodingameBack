package com.byblos.evaluation.evaluationservice.mappers;

import com.byblos.evaluation.evaluationservice.dtos.UserDto;
import com.byblos.evaluation.evaluationservice.models.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    List<UserDto> toUserDtos(List<User> users);

    User toUser(UserDto userDto);
}
