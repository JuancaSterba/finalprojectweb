package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.dto.CustomerDTO;
import org.springframework.dao.DataAccessException;

public interface ICustomerService extends ICRUDService<CustomerDTO, CustomerDTO> {
    CustomerDTO findByCuit(String cuit) throws DataAccessException;

    CustomerDTO findByEmail(String email) throws DataAccessException;

    CustomerDTO updateById(Long id, CustomerDTO customerDTO) throws DataAccessException;

}
