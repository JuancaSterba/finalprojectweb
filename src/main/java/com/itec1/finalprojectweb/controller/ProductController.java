package com.itec1.finalprojectweb.controller;

import com.itec1.finalprojectweb.dto.ProductDTO;
import com.itec1.finalprojectweb.exception.InvalidDataException;
import com.itec1.finalprojectweb.exception.NotFoundException;
import com.itec1.finalprojectweb.service.IProductService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.findOne(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            ProductDTO createdProduct = productService.save(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        try {
            ProductDTO updatedProduct = productService.update(productDTO, id);
            return ResponseEntity.ok(updatedProduct);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().build();
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<ProductDTO>> getProductsBySellerId(@PathVariable Long sellerId) {
        List<ProductDTO> products = productService.findBySellerId(sellerId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategoryId(@PathVariable Long categoryId) {
        List<ProductDTO> products = productService.findByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/seller/{sellerId}/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsBySellerIdAndCategoryId(@PathVariable Long sellerId, @PathVariable Long categoryId) {
        List<ProductDTO> products = productService.findBySellerIdAndCategoryId(sellerId, categoryId);
        return ResponseEntity.ok(products);
    }
}
