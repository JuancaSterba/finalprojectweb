package com.itec1.finalprojectweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerDTO {
    private Long id;
    private String cuit;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
}