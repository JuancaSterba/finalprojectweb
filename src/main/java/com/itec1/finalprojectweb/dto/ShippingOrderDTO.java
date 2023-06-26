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
    private Long id;
    private CustomerDTO customer;
    private ShippingNoteDTO shippingNote;
    private SectorDTO originSector;
    private SectorDTO destinySector;
    private List<OrderLineDTO> orderLines;
    private TrackingDTO tracking;
    private OrderStatus orderStatus;
    private String number;
    private LocalDate startDate;
    private LocalDate finishDate;
}
