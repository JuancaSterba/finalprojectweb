package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.dto.CustomerDTO;
import com.itec1.finalprojectweb.dto.ShippingOrderDTO;
import com.itec1.finalprojectweb.exception.InvalidDataException;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface ICustomerService extends ICRUDService<CustomerDTO, CustomerDTO> {
    CustomerDTO findByCuit(String cuit);
    CustomerDTO findByEmail(String email);
    List<ShippingOrderDTO> getShippingOrdersByCustomerId(Long customerId);
}
