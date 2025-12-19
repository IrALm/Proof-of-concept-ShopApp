package com.agtech.shop.model.formDto;

import lombok.Builder;

@Builder
public record SellerFormDTO(
        String fullName , String email
) {
}
