package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.dto.CustomerDTO;
import com.itec1.finalprojectweb.dto.ShippingOrderDTO;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface ICustomerService extends ICRUDService<CustomerDTO, CustomerDTO> {
    CustomerDTO findByCuit(String cuit);

    CustomerDTO findByEmail(String email);

    CustomerDTO updateById(Long id, CustomerDTO customerDTO) throws DataAccessException;

    List<ShippingOrderDTO> getShippingOrdersByCustomerId(Long customerId);

}
