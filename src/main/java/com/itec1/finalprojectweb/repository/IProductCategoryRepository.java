package com.itec1.finalprojectweb.repository;

import com.itec1.finalprojectweb.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    ProductCategory findByDescription(String description);
}