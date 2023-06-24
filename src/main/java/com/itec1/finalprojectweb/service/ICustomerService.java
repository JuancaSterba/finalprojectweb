package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.dto.CustomerDTO;

public interface ICustomerService extends ICRUDService<CustomerDTO, CustomerDTO> {
    CustomerDTO findByCuit(String cuit);

    CustomerDTO findByEmail(String email);
}
