package com.agtech.shop.repository;

import com.agtech.shop.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    boolean existsByEmail(String email);
    Optional<Seller> findByEmail(String email);
}
