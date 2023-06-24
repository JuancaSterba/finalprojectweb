package com.itec1.finalprojectweb.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trackings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tracking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "shipping_order_id", nullable = false)
    private ShippingOrder shippingOrder;

    @Column(nullable = false)
    private float latitude;

    @Column(nullable = false)
    private float longitude;

}
