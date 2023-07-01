package com.itec1.finalprojectweb.controller;

import com.itec1.finalprojectweb.dto.ProductCategoryDTO;
import com.itec1.finalprojectweb.exception.InvalidDataException;
import com.itec1.finalprojectweb.exception.NotFoundException;
import com.itec1.finalprojectweb.service.IProductCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-categories")
public class ProductCategoryController {
    private final IProductCategoryService productCategoryService;

    public ProductCategoryController(IProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryDTO> getProductCategoryById(@PathVariable Long id) {
        try {
            ProductCategoryDTO productCategoryDTO = productCategoryService.findOne(id);
            return ResponseEntity.ok(productCategoryDTO);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductCategoryDTO>> getAllProductCategories() {
        List<ProductCategoryDTO> productCategories = productCategoryService.findAll();
        return ResponseEntity.ok(productCategories);
    }

    @PostMapping("/")
    public ResponseEntity<ProductCategoryDTO> createProductCategory(@RequestBody ProductCategoryDTO productCategoryDTO) {
        try {
            ProductCategoryDTO savedProductCategoryDTO = productCategoryService.save(productCategoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProductCategoryDTO);
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCategoryDTO> updateProductCategory(@PathVariable Long id, @RequestBody ProductCategoryDTO productCategoryDTO) {
        try {
            ProductCategoryDTO updatedProductCategoryDTO = productCategoryService.update(productCategoryDTO, id);
            return ResponseEntity.ok(updatedProductCategoryDTO);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductCategory(@PathVariable Long id) {
        try {
            productCategoryService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}