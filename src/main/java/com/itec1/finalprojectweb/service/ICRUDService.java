package com.itec1.finalprojectweb.service;

import org.springframework.dao.DataAccessException;

import java.util.List;

public interface ICRUDService<REQUEST, RESPONSE> {
    RESPONSE findOne(Long id);
    List<RESPONSE> findAll();
    RESPONSE save(REQUEST request) throws DataAccessException;
    boolean validateDTO(REQUEST request);
    void deleteById(Long id) throws DataAccessException;
}
