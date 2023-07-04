package com.itec1.finalprojectweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackingDTO {
    private Long id;
    private Long shippingOrderId;
    private List<Long> locationIds;
}