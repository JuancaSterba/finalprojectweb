package com.itec1.finalprojectweb.controller;

import com.itec1.finalprojectweb.dto.LocationDTO;
import com.itec1.finalprojectweb.dto.TrackingDTO;
import com.itec1.finalprojectweb.entity.ShippingOrder;
import com.itec1.finalprojectweb.entity.Tracking;
import com.itec1.finalprojectweb.exception.InvalidDataException;
import com.itec1.finalprojectweb.repository.IShippingOrderRepository;
import com.itec1.finalprojectweb.service.ITrackingService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trackings")
public class TrackingController {

    private final ITrackingService trackingService;
    private final ModelMapper mapper;
    private final IShippingOrderRepository shippingOrderRepository;

    public TrackingController(ITrackingService trackingService, ModelMapper mapper, IShippingOrderRepository shippingOrderRepository) {
        this.trackingService = trackingService;
        this.mapper = mapper;
        this.shippingOrderRepository = shippingOrderRepository;
    }

    @PostMapping("/tracking")
    public ResponseEntity<TrackingDTO> createTracking(@RequestBody TrackingDTO trackingDTO) {
        try {
            // Crear un nuevo objeto Tracking a partir del DTO
            Tracking tracking = mapper.map(trackingDTO, Tracking.class);

            // Obtener la ShippingOrder correspondiente
            ShippingOrder shippingOrder = shippingOrderRepository.findById(trackingDTO.getShippingOrderId())
                    .orElseThrow(() -> new InvalidDataException("ShippingOrder not found"));

            // Asignar el objeto Tracking a la ShippingOrder
            shippingOrder.setTracking(tracking);

            // Guardar la ShippingOrder actualizada en la base de datos
            shippingOrderRepository.save(shippingOrder);

            // Convertir el objeto Tracking a TrackingDTO y devolverlo en la respuesta
            TrackingDTO createdTrackingDTO = mapper.map(tracking, TrackingDTO.class);
            return ResponseEntity.ok(createdTrackingDTO);
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{trackingId}/locations")
    public ResponseEntity<LocationDTO> saveLocationInTracking(@PathVariable Long trackingId, @RequestBody LocationDTO locationDTO) {
        try {
            // Guardar la ubicación en la lista de ubicaciones del Tracking utilizando el servicio
            LocationDTO savedLocationDTO = trackingService.saveLocationInTracking(trackingId, locationDTO);

            // Devolver el DTO de la ubicación guardada en la respuesta
            return ResponseEntity.ok(savedLocationDTO);
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
