package com.itec1.finalprojectweb.controller;

import com.itec1.finalprojectweb.dto.ShippingOrderDTO;
import com.itec1.finalprojectweb.dto.ShippingOrderStatusDTO;
import com.itec1.finalprojectweb.exception.InvalidDataException;
import com.itec1.finalprojectweb.service.IShippingOrderService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipping-orders")
public class ShippingOrderController {

    private final IShippingOrderService shippingOrderService;

    public ShippingOrderController(IShippingOrderService shippingOrderService) {
        this.shippingOrderService = shippingOrderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShippingOrderDTO> getShippingOrderById(@PathVariable Long id) {
        try {
            ShippingOrderDTO shippingOrderDTO = shippingOrderService.findOne(id);
            if (shippingOrderDTO != null) {
                return ResponseEntity.ok(shippingOrderDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ShippingOrderDTO>> getAllShippingOrders() {
        try {
            List<ShippingOrderDTO> shippingOrders = shippingOrderService.findAll();
            return ResponseEntity.ok(shippingOrders);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<ShippingOrderDTO> createShippingOrder(@RequestBody ShippingOrderDTO shippingOrderDTO) {
        try {
            ShippingOrderDTO createdShippingOrder = shippingOrderService.save(shippingOrderDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdShippingOrder);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (InvalidDataException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShippingOrder(@PathVariable Long id) {
        try {
            shippingOrderService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ShippingOrderDTO> updateShippingOrderStatus(@PathVariable Long id, @RequestBody ShippingOrderStatusDTO statusDTO) {
        try {
            // Actualizar el estado de la ShippingOrder utilizando el servicio
            ShippingOrderDTO updatedShippingOrderDTO = shippingOrderService.updateShippingOrderStatus(id, statusDTO);

            // Devolver el DTO actualizado en la respuesta
            return ResponseEntity.ok(updatedShippingOrderDTO);
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}