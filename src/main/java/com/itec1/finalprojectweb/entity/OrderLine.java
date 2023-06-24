package com.itec1.finalprojectweb.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_lines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "shipping_order_id", nullable = false)
    private ShippingOrder shippingOrder;

    @Column(nullable = false)
    private int quantity;

}
