package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.dto.CustomerDTO;
import com.itec1.finalprojectweb.dto.ShippingOrderDTO;
import com.itec1.finalprojectweb.entity.Customer;
import com.itec1.finalprojectweb.entity.ShippingOrder;
import com.itec1.finalprojectweb.exception.NotFoundException;
import com.itec1.finalprojectweb.repository.ICustomerRepository;
import com.itec1.finalprojectweb.repository.IShippingOrderRepository;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final ICustomerRepository customerRepository;
    private final IShippingOrderRepository shippingOrderRepository;
    private final ModelMapper mapper;

    public CustomerServiceImpl(ICustomerRepository customerRepository, IShippingOrderRepository shippingOrderRepository, ModelMapper mapper) {
        this.customerRepository = customerRepository;
        this.shippingOrderRepository = shippingOrderRepository;
        this.mapper = mapper;
    }

    @Override
    public CustomerDTO findOne(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            return mapper.map(customer, CustomerDTO.class);
        } else {
            return null;
        }
    }

    @Override
    public List<CustomerDTO> findAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customer -> mapper.map(customer, CustomerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        try {
            Customer customer = mapper.map(customerDTO, Customer.class);
            customer = customerRepository.save(customer);
            return mapper.map(customer, CustomerDTO.class);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al guardar el cliente", e);
        }
    }

    @Override
    public boolean validateDTO(CustomerDTO customerDTO) {
        if (customerRepository.findByCuit(customerDTO.getCuit()) != null) {
            throw new DuplicateKeyException("Customer with CUIT already exists: " + customerDTO.getCuit());
        }
        if (customerRepository.findByEmail(customerDTO.getEmail()) != null) {
            throw new DuplicateKeyException("Customer with Email already exists: " + customerDTO.getEmail());
        }
        return true;
    }

    @Override
    public void deleteById(Long id) {
        try{
            customerRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error al guardar el cliente", e);
        }
    }

    @Override
    public CustomerDTO findByCuit(String cuit) {
        Customer customer = customerRepository.findByCuit(cuit);
        if (customer != null) {
            return mapper.map(customer, CustomerDTO.class);
        }
        return null;
    }

    @Override
    public CustomerDTO findByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer != null) {
            return mapper.map(customer, CustomerDTO.class);
        }
        return null;
    }

    @Override
    public CustomerDTO updateById(Long id, CustomerDTO customerDTO) throws DataAccessException {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if (existingCustomer != null) {
            existingCustomer.setName(customerDTO.getName());
            existingCustomer.setCuit(customerDTO.getCuit());
            existingCustomer.setDni(customerDTO.getDni());
            existingCustomer.setAddress(customerDTO.getAddress());
            existingCustomer.setPhoneNumber(customerDTO.getPhoneNumber());
            existingCustomer.setEmail(customerDTO.getEmail());

            existingCustomer = customerRepository.save(existingCustomer);

            return mapper.map(existingCustomer, CustomerDTO.class);
        } else {
            throw new NotFoundException("Customer not found with ID: " + id);
        }
    }

    @Override
    public List<ShippingOrderDTO> getShippingOrdersByCustomerId(Long customerId) {
        List<ShippingOrder> shippingOrders = shippingOrderRepository.findByCustomerId(customerId);
        return shippingOrders.stream()
                .map(shippingOrder -> mapper.map(shippingOrder, ShippingOrderDTO.class))
                .collect(Collectors.toList());
    }
}
