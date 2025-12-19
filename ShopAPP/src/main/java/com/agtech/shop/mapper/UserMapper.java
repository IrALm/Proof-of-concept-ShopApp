package com.agtech.shop.mapper;

import com.agtech.shop.entity.User;
import com.agtech.shop.model.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring" , uses = OrderMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    UserDTO toDTO(User user);
}
