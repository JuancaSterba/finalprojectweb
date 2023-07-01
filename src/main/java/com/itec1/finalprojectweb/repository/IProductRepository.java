package com.itec1.finalprojectweb.repository;

import com.itec1.finalprojectweb.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySellerId(Long sellerId);
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findBySellerIdAndCategoryId(Long sellerId, Long categoryId);
}
