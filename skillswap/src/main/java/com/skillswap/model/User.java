package com.skillswap.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String email;
    private String password;
    private String role; // 'STUDENT' ou 'ADMIN'

    @Column(name = "is_active") // Force Java à chercher exactement ce nom-là en BDD
    private boolean isActive;
}