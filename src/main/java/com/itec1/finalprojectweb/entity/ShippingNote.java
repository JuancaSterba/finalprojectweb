package com.itec1.finalprojectweb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "shipping_notes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class ShippingNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String shippingCode;

    @Column(nullable = false)
    private LocalDate noteDate;

    @ManyToOne
    @JoinColumn(name = "currier_id", nullable = false)
    private Currier currier;

}
