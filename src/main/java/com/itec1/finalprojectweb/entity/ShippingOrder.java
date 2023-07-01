package com.itec1.finalprojectweb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "shipping_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ShippingOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "shipping_note_id", nullable = false)
    private ShippingNote shippingNote;

    @ManyToOne
    @JoinColumn(name = "sector_rigen_id", nullable = false)
    private Sector originSector;

    @ManyToOne
    @JoinColumn(name = "sector_destino_id", nullable = false)
    private Sector destinySector;

    @OneToMany(mappedBy = "shippingOrder", cascade = CascadeType.ALL)
    private List<OrderLine> orderLines;

    @OneToOne(mappedBy = "shippingOrder", cascade = CascadeType.ALL)
    private Tracking tracking;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    private String number;
    private LocalDate startDate;
    private LocalDate finishDate;
    private int currierRating;

}
