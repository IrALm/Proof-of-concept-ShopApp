package com.agtech.shop.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "produits")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(length = 1000)
    private String description;

    private String imageUrl;
}
