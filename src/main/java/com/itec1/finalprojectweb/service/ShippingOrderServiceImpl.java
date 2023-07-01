package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.dto.ShippingOrderDTO;
import com.itec1.finalprojectweb.entity.ShippingOrder;
import com.itec1.finalprojectweb.exception.NotFoundException;
import com.itec1.finalprojectweb.repository.IShippingOrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShippingOrderServiceImpl implements IShippingOrderService {
    private final IShippingOrderRepository shippingOrderRepository;
    private final ModelMapper mapper;

    public ShippingOrderServiceImpl(IShippingOrderRepository shippingOrderRepository, ModelMapper mapper) {
        this.shippingOrderRepository = shippingOrderRepository;
        this.mapper = mapper;
    }

    @Override
    public ShippingOrderDTO findOne(Long id) throws DataAccessException {
        ShippingOrder shippingOrder = shippingOrderRepository.findById(id).orElse(null);
        if (shippingOrder != null) {
            return mapper.map(shippingOrder, ShippingOrderDTO.class);
        }
        return null;
    }

    @Override
    public List<ShippingOrderDTO> findAll() throws DataAccessException {
        List<ShippingOrder> shippingOrders = shippingOrderRepository.findAll();
        List<ShippingOrderDTO> list = new ArrayList<>();
        for (ShippingOrder shippingOrder : shippingOrders) {
            ShippingOrderDTO map = mapper.map(shippingOrder, ShippingOrderDTO.class);
            list.add(map);
        }
        return list;
    }

    @Override
    public ShippingOrderDTO save(ShippingOrderDTO shippingOrderDTO) throws DataAccessException {
        ShippingOrder shippingOrder = mapper.map(shippingOrderDTO, ShippingOrder.class);
        shippingOrder = shippingOrderRepository.save(shippingOrder);
        return mapper.map(shippingOrder, ShippingOrderDTO.class);
    }

    @Override
    public boolean validateDTO(ShippingOrderDTO shippingOrderDTO) throws DataAccessException {
        return false;
    }

    @Override
    public void deleteById(Long id) throws DataAccessException {
        shippingOrderRepository.deleteById(id);
    }

    @Override
    public ShippingOrderDTO createShippingOrder(ShippingOrderDTO shippingOrderDTO) {
        // Mapear el DTO a la entidad ShippingOrder
        ShippingOrder shippingOrder = mapper.map(shippingOrderDTO, ShippingOrder.class);

        // Realizar cualquier lógica adicional antes de guardar el pedido de envío

        // Guardar el pedido de envío en la base de datos
        shippingOrder = shippingOrderRepository.save(shippingOrder);

        // Mapear la entidad guardada a un DTO y devolverlo
        return mapper.map(shippingOrder, ShippingOrderDTO.class);
    }

    @Override
    public ShippingOrderDTO getShippingOrderById(Long id) {
        Optional<ShippingOrder> shippingOrderOptional = shippingOrderRepository.findById(id);
        if (shippingOrderOptional.isPresent()) {
            ShippingOrder shippingOrder = shippingOrderOptional.get();
            return mapper.map(shippingOrder, ShippingOrderDTO.class);
        } else {
            throw new NotFoundException("Shipping order not found with ID: " + id);
        }
    }

    @Override
    public List<ShippingOrderDTO> getAllShippingOrders() {
        List<ShippingOrder> shippingOrders = shippingOrderRepository.findAll();
        List<ShippingOrderDTO> list = new ArrayList<>();
        for (ShippingOrder shippingOrder : shippingOrders) {
            ShippingOrderDTO map = mapper.map(shippingOrder, ShippingOrderDTO.class);
            list.add(map);
        }
        return list;
    }

    @Override
    public void deleteShippingOrder(Long id) {
        shippingOrderRepository.deleteById(id);
    }
}
