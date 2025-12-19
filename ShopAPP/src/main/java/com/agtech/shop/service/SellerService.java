package com.agtech.shop.service;

import com.agtech.shop.entity.Seller;
import com.agtech.shop.model.formDto.SellerFormDTO;
import com.agtech.shop.repository.SellerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;

    @Transactional
    private void createSeller(SellerFormDTO sellerFormDTO){

        if( sellerRepository.existsByEmail(sellerFormDTO.email())){
            throw new IllegalArgumentException("Email déjà existant");
        }
        Seller seller = new Seller();
        seller.setFullName(sellerFormDTO.fullName());
        seller.setEmail(sellerFormDTO.email());
        seller.setProducts(new ArrayList<>());
        sellerRepository.save(seller);
    }
}
