package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.dto.ShippingOrderDTO;
import com.itec1.finalprojectweb.dto.ShippingOrderStatusDTO;
import com.itec1.finalprojectweb.exception.InvalidDataException;

import java.util.List;

public interface IShippingOrderService extends ICRUDService<ShippingOrderDTO, ShippingOrderDTO> {
    ShippingOrderDTO createShippingOrder(ShippingOrderDTO shippingOrderDTO);
    ShippingOrderDTO getShippingOrderById(Long id);
    List<ShippingOrderDTO> getAllShippingOrders();
    void deleteShippingOrder(Long id);
    ShippingOrderDTO updateShippingOrderStatus(Long id, ShippingOrderStatusDTO statusDTO) throws InvalidDataException;
}
