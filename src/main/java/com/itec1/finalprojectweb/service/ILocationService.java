package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.dto.LocationDTO;
import com.itec1.finalprojectweb.exception.InvalidDataException;

import java.util.Optional;

public interface ILocationService extends ICRUDService<LocationDTO, LocationDTO> {
    LocationDTO saveLocationInTracking(Long trackingId, LocationDTO locationDTO) throws InvalidDataException;
}
