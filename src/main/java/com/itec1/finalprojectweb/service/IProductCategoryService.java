package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.dto.ProductCategoryDTO;

public interface IProductCategoryService extends ICRUDService<ProductCategoryDTO, ProductCategoryDTO> {
    ProductCategoryDTO findByDescription(String description);
}