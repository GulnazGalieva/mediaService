package com.example.mediaservice.mapper;

import com.example.mediaservice.dto.UserDto;
import com.example.mediaservice.model.Users;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UsersMapper extends GenericMapper<Users, UserDto>{
    protected UsersMapper(ModelMapper mapper) {
        super(mapper, Users.class, UserDto.class);
    }
}
