package com.itec1.finalprojectweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrierDTO {
    private String name;
    private String cuit;
    private String phoneNumber;
    private String email;
    private Long currierTypeId;
}