package com.itec1.finalprojectweb.dto;

import com.itec1.finalprojectweb.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippingOrderStatusDTO {
    private String newStatus;
}
