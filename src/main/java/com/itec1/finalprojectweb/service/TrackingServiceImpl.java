package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.dto.LocationDTO;
import com.itec1.finalprojectweb.dto.TrackingDTO;
import com.itec1.finalprojectweb.entity.TrackingLocation;
import com.itec1.finalprojectweb.entity.Tracking;
import com.itec1.finalprojectweb.exception.InvalidDataException;
import com.itec1.finalprojectweb.repository.ILocationRepository;
import com.itec1.finalprojectweb.repository.ITrackingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackingServiceImpl implements ITrackingService {

    private final ITrackingRepository trackingRepository;
    private final ILocationService locationService;
    private final ILocationRepository locationRepository;
    private final ModelMapper mapper;

    public TrackingServiceImpl(ITrackingRepository trackingRepository, ILocationService locationService, ILocationRepository locationRepository, ModelMapper mapper) {
        this.trackingRepository = trackingRepository;
        this.locationService = locationService;
        this.locationRepository = locationRepository;
        this.mapper = mapper;
    }


    @Override
    public TrackingDTO findOne(Long id) {
        return null;
    }

    @Override
    public List<TrackingDTO> findAll() {
        return null;
    }

    @Override
    public TrackingDTO save(TrackingDTO trackingDTO) throws DataAccessException, InvalidDataException {
        // Validar el objeto TrackingDTO
        if (!validateDTO(trackingDTO)) {
            throw new InvalidDataException("Invalid Tracking data");
        }

        // Convertir el objeto TrackingDTO a Tracking
        Tracking tracking = mapper.map(trackingDTO, Tracking.class);

        // Guardar el objeto Tracking en la base de datos
        Tracking savedTracking = trackingRepository.save(tracking);

        // Convertir el objeto Tracking guardado a TrackingDTO y devolverlo
        return mapper.map(savedTracking, TrackingDTO.class);
    }

    @Override
    public TrackingDTO update(TrackingDTO trackingDTO, Long id) throws DataAccessException, InvalidDataException {
        return null;
    }

    public boolean validateDTO(TrackingDTO trackingDTO) throws InvalidDataException {
        // Validar que el objeto TrackingDTO no sea nulo
        if (trackingDTO == null) {
            throw new InvalidDataException("TrackingDTO is null");
        }

        // Validar que el objeto TrackingDTO tenga los datos requeridos
        if (trackingDTO.getShippingOrderId() == null || trackingDTO.getLocationIds() == null || trackingDTO.getLocationIds().isEmpty()) {
            throw new InvalidDataException("Invalid TrackingDTO data");
        }

        return true;
    }


    @Override
    public void deleteById(Long id) throws DataAccessException {

    }

    @Override
    public LocationDTO saveLocationInTracking(Long trackingId, LocationDTO locationDTO) throws DataAccessException, InvalidDataException {
        // Obtener el objeto Tracking correspondiente
        Tracking tracking = trackingRepository.findById(trackingId)
                .orElseThrow(() -> new InvalidDataException("Tracking not found"));

        // Validar los datos de la ubicación
        locationService.validateDTO(locationDTO);

        // Convertir el DTO a una entidad de ubicación utilizando ModelMapper
        TrackingLocation trackingLocation = mapper.map(locationDTO, TrackingLocation.class);

        // Asignar el objeto Tracking a la ubicación
        trackingLocation.setTracking(tracking);

        // Agregar la ubicación a la lista de ubicaciones del Tracking
        tracking.getTrackingLocations().add(trackingLocation);

        // Guardar la ubicación en la base de datos
        locationRepository.save(trackingLocation);

        // Convertir la entidad guardada a un DTO utilizando ModelMapper y devolverlo
        return mapper.map(trackingLocation, LocationDTO.class);
    }
}
