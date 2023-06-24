package com.itec1.finalprojectweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectorDTO {
    private Long id;
    private String sectorCode;
    private String description;
}