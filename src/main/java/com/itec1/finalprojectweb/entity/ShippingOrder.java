package com.itec1.finalprojectweb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Column(nullable = false)
    private String shippingOrderCode;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "shippingOrder")
    private List<ShippingOrderDetail> shippingOrderDetails;

    @ManyToOne
    @JoinColumn(name = "origin_warehouse_id", nullable = false)
    private Warehouse originWarehouse;

    @ManyToOne
    @JoinColumn(name = "destination_warehouse_id", nullable = false)
    private Warehouse destinationWarehouse;

    @OneToOne(mappedBy = "tracking")
    private Tracking tracking;

    @Enumerated(EnumType.STRING)
    private ShippingOrderStatus shippingOrderStatus = ShippingOrderStatus.PENDING;

    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Temporal(TemporalType.TIME)
    private LocalDateTime startTime;

    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    @Temporal(TemporalType.TIME)
    private LocalDateTime endTime;

    @Min(0)
    @Max(9)
    private Integer currierRating;

    @ManyToOne
    @JoinColumn(name = "shipping_note_id")
    private ShippingOrderNote shippingOrderNote;

    private boolean deleted = false;

}
