package com.agtech.shop.model.formDto;

import lombok.Builder;

@Builder
public record UserFormDTO(
        String firstName,
        String lastName,
        String email
) {
}
