package com.agtech.shop.mapper;

import com.agtech.shop.entity.User;
import com.agtech.shop.model.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "id" , target = "id")
    @Mapping(source = "firstName" , target = "firstName")
    @Mapping(source = "lastName" , target = "lastName")
    @Mapping(source = "email" , target = "email")
    UserDTO toDTO(User user);
}
