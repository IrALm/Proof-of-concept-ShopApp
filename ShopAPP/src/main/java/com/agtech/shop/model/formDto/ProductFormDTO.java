package com.agtech.shop.model.formDto;

import lombok.Builder;

@Builder
public record ProductFormDTO(
        String name,
        Double price,
        String description
) {
}
