package com.itec1.finalprojectweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "curriers")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Currier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String cuit;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "currier_type_id", nullable = false)
    private CurrierType currierType;

    private boolean deleted = false;

}
