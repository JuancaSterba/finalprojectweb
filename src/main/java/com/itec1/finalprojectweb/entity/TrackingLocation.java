package com.itec1.finalprojectweb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TrackingLocation {

    @Id
    @ManyToOne
    @JoinColumn(name = "tracking_id", nullable = false)
    private Tracking tracking;

    @Id
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private Float latitude;

    @Column(nullable = false)
    private Float longitude;

    private boolean deleted = false;

}
