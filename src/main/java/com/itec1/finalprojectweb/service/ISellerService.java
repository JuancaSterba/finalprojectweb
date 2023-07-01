package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.dto.SellerDTO;
import com.itec1.finalprojectweb.exception.InvalidDataException;
import org.springframework.dao.DataAccessException;

public interface ISellerService extends ICRUDService<SellerDTO, SellerDTO> {
    SellerDTO findByCuit(String cuit);
    SellerDTO updateById(SellerDTO sellerDTO, Long id) throws DataAccessException, InvalidDataException;
}
