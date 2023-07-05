package com.itec1.finalprojectweb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "shipping_notes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class ShippingOrderNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String shippingNoteCode;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate noteDate;

    @Column(nullable = false)
    @Temporal(TemporalType.TIME)
    private LocalDateTime noteTime;

    @ManyToOne
    @JoinColumn(name = "currier_id", nullable = false)
    private Currier currier;

    private boolean deleted = false;

}
