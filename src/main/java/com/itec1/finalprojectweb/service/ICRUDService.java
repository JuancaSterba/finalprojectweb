package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.exception.InvalidDataException;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface ICRUDService<REQUEST, RESPONSE> {
    RESPONSE findOne(Long id);
    List<RESPONSE> findAll();
    RESPONSE save(REQUEST request) throws DataAccessException, InvalidDataException;
    RESPONSE update(REQUEST request, Long id) throws DataAccessException, InvalidDataException;
    boolean validateDTO(REQUEST request) throws InvalidDataException;
    void deleteById(Long id) throws DataAccessException;
}
