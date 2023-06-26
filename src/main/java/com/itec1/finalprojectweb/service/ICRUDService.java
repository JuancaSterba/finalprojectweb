package com.itec1.finalprojectweb.service;

import org.springframework.dao.DataAccessException;

import java.util.List;

public interface ICRUDService<REQUEST, RESPONSE> {
    RESPONSE findById(Long id) throws DataAccessException;
    List<RESPONSE> findAll() throws DataAccessException;
    RESPONSE save(REQUEST request)throws DataAccessException;
    boolean validateDTO(REQUEST request)throws DataAccessException;
    void deleteById(Long id) throws DataAccessException;
}
