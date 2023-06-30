package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.dto.ShippingOrderDTO;
import com.itec1.finalprojectweb.entity.ShippingOrder;
import com.itec1.finalprojectweb.repository.IShippingOrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return shippingOrders.stream()
                .map(shippingOrder -> mapper.map(shippingOrder, ShippingOrderDTO.class))
                .collect(Collectors.toList());
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
}
