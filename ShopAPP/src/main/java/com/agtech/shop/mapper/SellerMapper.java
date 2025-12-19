package com.agtech.shop.mapper;

import com.agtech.shop.entity.Seller;
import com.agtech.shop.model.dto.SellerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SellerMapper {

    SellerMapper INSTANCE = Mappers.getMapper(SellerMapper.class);

    SellerDTO toDTO(Seller seller);

}
