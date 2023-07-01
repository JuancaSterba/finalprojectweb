package com.itec1.finalprojectweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippingNoteDTO {
    private String shippingCode;
    private LocalDate noteDate;
    private CurrierDTO currier;
}