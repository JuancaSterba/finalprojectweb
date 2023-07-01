package com.itec1.finalprojectweb.dto;

import com.itec1.finalprojectweb.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippingOrderDTO {
    private Long customerId;
    private Long shippingNoteId;
    private Long originSectorId;
    private Long destinySectorId;
    private List<Long> orderLinesId;
    private Long trackingId;
    private String orderStatus;
    private String number;
    private LocalDate startDate;
    private LocalDate finishDate;
    private int currierRating;
}
