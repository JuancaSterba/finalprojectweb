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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String cuit;
    private String phoneNumber;
    private String email;

    @ManyToOne
    @JoinColumn(name = "currier_type_id", nullable = false)
    private CurrierType currierType;

}
