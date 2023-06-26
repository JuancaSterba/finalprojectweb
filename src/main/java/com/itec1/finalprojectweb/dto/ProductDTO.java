package com.itec1.finalprojectweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String description;
    private float height;
    private float length;
    private float width;
    private float weight;
    private SellerDTO seller;
    private ProductCategoryDTO category;
}