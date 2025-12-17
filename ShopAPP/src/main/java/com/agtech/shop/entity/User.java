package com.agtech.shop.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "utilisateurs")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false , unique = true)
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
