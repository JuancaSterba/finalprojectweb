package com.itec1.finalprojectweb.dto;

import lombok.Data;

@Data
public class WarehouseDTO {
    private Long id;
    private Long locationId;
    private Long sectorId;
    private String warehouseCode;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
}
