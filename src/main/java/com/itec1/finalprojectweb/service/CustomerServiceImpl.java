package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.dto.CustomerDTO;
import com.itec1.finalprojectweb.dto.ShippingOrderDTO;
import com.itec1.finalprojectweb.entity.Customer;
import com.itec1.finalprojectweb.entity.ShippingOrder;
import com.itec1.finalprojectweb.exception.InvalidDataException;
import com.itec1.finalprojectweb.exception.NotFoundException;
import com.itec1.finalprojectweb.repository.ICustomerRepository;
import com.itec1.finalprojectweb.repository.IShippingOrderRepository;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<CustomerDTO> result = new ArrayList<>();
        for (Customer customer : customers) {
            result.add(mapper.map(customer, CustomerDTO.class));
        }
        return result;
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) throws DataAccessException, InvalidDataException {
        validateDTO(customerDTO);

        String cuit = customerDTO.getCuit();
        String email = customerDTO.getEmail();

        // Verificar si ya existe un cliente con el mismo CUIT
        Customer existingCustomerByCuit = customerRepository.findByCuit(cuit);
        if (existingCustomerByCuit != null) {
            throw new DuplicateKeyException("Customer with CUIT already exists: " + cuit);
        }

        // Verificar si ya existe un cliente con el mismo correo electrónico
        Customer existingCustomerByEmail = customerRepository.findByEmail(email);
        if (existingCustomerByEmail != null) {
            throw new DuplicateKeyException("Customer with Email already exists: " + email);
        }

        // Verificar si ya existe un cliente con los mismos datos a guardar
        Customer existingCustomerByData = customerRepository.findByCuitAndDniAndEmail(cuit, customerDTO.getDni(), customerDTO.getEmail());
        if (existingCustomerByData != null) {
            throw new InvalidDataException("Ya existe un cliente con los mismos datos");
        }

        Customer customer = mapper.map(customerDTO, Customer.class);
        Customer savedCustomer = customerRepository.save(customer);
        return mapper.map(savedCustomer, CustomerDTO.class);
    }

    @Override
    public CustomerDTO update(CustomerDTO customerDTO, Long id) throws DataAccessException, InvalidDataException {
        if (!validateDTO(customerDTO)) {
            throw new InvalidDataException("Invalid customer data");
        }

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
    public boolean validateDTO(CustomerDTO customerDTO) throws InvalidDataException {
        if (customerDTO.getName() == null || customerDTO.getName().isEmpty()) {
            throw new InvalidDataException("Invalid customer name");
        }
        if (customerDTO.getCuit() == null || customerDTO.getCuit().isEmpty()) {
            throw new InvalidDataException("Invalid customer CUIT");
        }
        if (customerDTO.getAddress() == null || customerDTO.getAddress().isEmpty()) {
            throw new InvalidDataException("Invalid customer address");
        }
        if (customerDTO.getPhoneNumber() == null || customerDTO.getPhoneNumber().isEmpty()) {
            throw new InvalidDataException("Invalid customer phone number");
        }
        if (customerDTO.getEmail() == null || customerDTO.getEmail().isEmpty()) {
            throw new InvalidDataException("Invalid customer email");
        }

        return true;
    }

    @Override
    public void deleteById(Long id) {
        try{
            customerRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new DataAccessException("Error al borrar el cliente", e) {};
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
    public List<ShippingOrderDTO> getShippingOrdersByCustomerId(Long customerId) {
        List<ShippingOrder> shippingOrders = shippingOrderRepository.findByCustomerId(customerId);
        List<ShippingOrderDTO> list = new ArrayList<>();
        for (ShippingOrder shippingOrder : shippingOrders) {
            ShippingOrderDTO map = mapper.map(shippingOrder, ShippingOrderDTO.class);
            list.add(map);
        }
        return list;
    }
}
