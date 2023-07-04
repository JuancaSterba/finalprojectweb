package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.dto.LocationDTO;
import com.itec1.finalprojectweb.dto.TrackingDTO;
import com.itec1.finalprojectweb.exception.InvalidDataException;
import org.springframework.dao.DataAccessException;

public interface ITrackingService extends ICRUDService<TrackingDTO, TrackingDTO> {
    LocationDTO saveLocationInTracking(Long trackingId, LocationDTO locationDTO) throws DataAccessException, InvalidDataException;
}
