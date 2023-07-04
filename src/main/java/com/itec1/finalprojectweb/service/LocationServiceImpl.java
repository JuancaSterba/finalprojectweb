package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.dto.LocationDTO;
import com.itec1.finalprojectweb.entity.Location;
import com.itec1.finalprojectweb.entity.Tracking;
import com.itec1.finalprojectweb.exception.InvalidDataException;
import com.itec1.finalprojectweb.repository.ILocationRepository;
import com.itec1.finalprojectweb.repository.ITrackingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

public class LocationServiceImpl implements ILocationService {

    private final ILocationRepository locationRepository;
    private final ITrackingRepository trackingRepository;
    private final ModelMapper mapper;

    public LocationServiceImpl(ILocationRepository locationRepository, ITrackingRepository trackingRepository, ModelMapper mapper) {
        this.locationRepository = locationRepository;
        this.trackingRepository = trackingRepository;
        this.mapper = mapper;
    }


    @Override
    public LocationDTO findOne(Long id) {
        return null;
    }

    @Override
    public List<LocationDTO> findAll() {
        return null;
    }

    @Override
    public LocationDTO save(LocationDTO locationDTO) throws DataAccessException, InvalidDataException {
        return null;
    }

    @Override
    public LocationDTO saveLocationInTracking(Long trackingId, LocationDTO locationDTO) throws InvalidDataException {
        // Obtener el objeto Tracking correspondiente
        Tracking tracking = trackingRepository.findById(trackingId)
                .orElseThrow(() -> new InvalidDataException("Tracking not found"));

        // Validar los datos de la ubicación
        validateDTO(locationDTO);

        // Convertir el DTO a una entidad de ubicación utilizando ModelMapper
        Location location = mapper.map(locationDTO, Location.class);

        // Asignar el objeto Tracking a la ubicación
        location.setTracking(tracking);

        // Agregar la ubicación a la lista de ubicaciones del Tracking
        tracking.getLocations().add(location);

        // Guardar la ubicación en la base de datos
        locationRepository.save(location);

        // Convertir la entidad guardada a un DTO utilizando ModelMapper y devolverlo
        return mapper.map(location, LocationDTO.class);
    }

    @Override
    public LocationDTO update(LocationDTO locationDTO, Long id) throws DataAccessException, InvalidDataException {
        return null;
    }

    @Override
    public boolean validateDTO(LocationDTO locationDTO) throws InvalidDataException {
        if (locationDTO.getDateTime() == null) {
            throw new InvalidDataException("Date and time is required");
        }
        if (locationDTO.getLatitude() == null || locationDTO.getLongitude() == null) {
            throw new InvalidDataException("Latitude and longitude are required");
        }
        return true;
    }

    @Override
    public void deleteById(Long id) throws DataAccessException {

    }

}
