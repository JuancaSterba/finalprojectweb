package com.itec1.finalprojectweb.controller;

import com.itec1.finalprojectweb.dto.SellerDTO;
import com.itec1.finalprojectweb.exception.InvalidDataException;
import com.itec1.finalprojectweb.exception.NotFoundException;
import com.itec1.finalprojectweb.service.ISellerService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    private final ISellerService sellerService;

    public SellerController(ISellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping("/")
    public ResponseEntity<List<SellerDTO>> getAllSellers() {
        List<SellerDTO> sellers = sellerService.findAll();
        return ResponseEntity.ok(sellers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerDTO> getSellerById(@PathVariable Long id) {
        SellerDTO seller = sellerService.findOne(id);
        return ResponseEntity.ok(seller);
    }

    @PostMapping("/")
    public ResponseEntity<SellerDTO> createSeller(@RequestBody SellerDTO sellerDTO) {
        try {
            SellerDTO createdSeller = sellerService.save(sellerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSeller);
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SellerDTO> updateSeller(@PathVariable Long id, @RequestBody SellerDTO sellerDTO) {
        try {
            SellerDTO updatedSeller = sellerService.update(sellerDTO, id);
            return ResponseEntity.ok(updatedSeller);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
        try {
            sellerService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
