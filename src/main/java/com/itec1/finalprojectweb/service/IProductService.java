package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.dto.ProductDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface IProductService extends ICRUDService<ProductDTO, ProductDTO> {
    List<ProductDTO> findBySellerId(Long sellerId);
    List<ProductDTO> findByCategoryId(Long categoryId);
    List<ProductDTO> findBySellerIdAndCategoryId(Long sellerId, Long categoryId);

    ProductDTO updateById(Long id, ProductDTO productDTO) throws DataAccessException;

}
