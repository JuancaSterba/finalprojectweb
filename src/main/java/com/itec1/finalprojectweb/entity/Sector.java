package com.itec1.finalprojectweb.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sectors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sectorCode;
    private String description;

}
