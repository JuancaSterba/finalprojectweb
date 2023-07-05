package com.itec1.finalprojectweb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trackings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "shipping_order_id", nullable = false)
    private ShippingOrder shippingOrder;

    @OneToMany(mappedBy = "tracking", cascade = CascadeType.ALL)
    private List<TrackingLocation> trackingLocations = new ArrayList<>();

    private boolean deleted = false;

}
