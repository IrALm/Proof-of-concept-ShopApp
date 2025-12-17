package com.agtech.shop.model.formDto;

public record ProductFormDTO(
        String name,
        Double price,
        String description,
        String imageUrl
) {
}
