package com.agtech.shop.entity;

import jakarta.persistence.*;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id" , nullable = false)
    private Order order;

    private String pdfUrl;
}
