package com.itec1.finalprojectweb.controller;

import com.itec1.finalprojectweb.dto.CustomerDTO;
import com.itec1.finalprojectweb.service.ICustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private ICustomerService customerService;

    @InjectMocks
    private CustomerController customerController;


    @Test
    @DisplayName("Should return bad request when the DTO is invalid")
    void createCustomerWhenDtoIsInvalidThenReturnBadRequest() {
        CustomerDTO invalidCustomerDTO = new CustomerDTO();
        when(customerService.validateDTO(invalidCustomerDTO)).thenReturn(false);

        ResponseEntity<CustomerDTO> response = customerController.createCustomer(invalidCustomerDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(customerService, times(1)).validateDTO(invalidCustomerDTO);
        verify(customerService, never()).save(invalidCustomerDTO);
    }

    @Test
    @DisplayName("Should return internal server error when data access exception occurs")
    void createCustomerWhenDataAccessExceptionOccursThenReturnInternalServerError() {
        CustomerDTO customerDTO = new CustomerDTO();
        when(customerService.validateDTO(customerDTO)).thenReturn(true);
        when(customerService.save(customerDTO)).thenThrow(new DataAccessException("...") {
        });

        ResponseEntity<CustomerDTO> response = customerController.createCustomer(customerDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        verify(customerService, times(1)).validateDTO(customerDTO);
        verify(customerService, times(1)).save(customerDTO);
    }

    @Test
    @DisplayName("Should create a customer when the DTO is valid")
    void createCustomerWhenDtoIsValid() {
        CustomerDTO customerDTO = new CustomerDTO(
                1L, "John Doe", "123456789", "1234567890", "123 Main St", "1234567890", "john.doe@example.com");
        when(customerService.validateDTO(customerDTO)).thenReturn(true);
        when(customerService.save(customerDTO)).thenReturn(customerDTO);

        ResponseEntity<CustomerDTO> response = customerController.createCustomer(customerDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(customerDTO, response.getBody());
        verify(customerService, times(1)).validateDTO(customerDTO);
        verify(customerService, times(1)).save(customerDTO);
    }

    @Test
    @DisplayName("Should return internal server error when a data access exception occurs")
    void getAllCustomersWhenDataAccessExceptionOccurs() {
        when(customerService.findAll()).thenThrow(new DataAccessException("...") {
        });

        ResponseEntity<List<CustomerDTO>> response = customerController.getAllCustomers();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());

        verify(customerService, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return an empty list when the database is empty")
    void getAllCustomersWhenDatabaseIsEmpty() {
        when(customerService.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<CustomerDTO>> response = customerController.getAllCustomers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
        verify(customerService, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return all customers when the database is not empty")
    void getAllCustomersWhenDatabaseIsNotEmpty() {
        List<CustomerDTO> customers = new ArrayList<>();
        customers.add(new CustomerDTO(1L, "John Doe", "123456789", "1234567890", "123 Main St", "1234567890", "john.doe@example.com"));
        customers.add(new CustomerDTO(2L, "Jane Smith", "987654321", "0987654321", "456 Elm St", "0987654321", "jane.smith@example.com"));

        when(customerService.findAll()).thenReturn(customers);

        ResponseEntity<List<CustomerDTO>> response = customerController.getAllCustomers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customers, response.getBody());
        verify(customerService, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return not found when the customer id does not exist")
    void getCustomerByIdWhenIdDoesNotExist() {
        Long customerId = 1L;
        when(customerService.findOne(customerId)).thenReturn(null);

        ResponseEntity<CustomerDTO> response = customerController.getCustomerById(customerId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(customerService, times(1)).findOne(customerId);
    }

    @Test
    @DisplayName("Should return internal server error when a data access exception occurs")
    void getCustomerByIdWhenDataAccessExceptionOccurs() {
        Long customerId = 1L;
        when(customerService.findOne(customerId)).thenThrow(new DataAccessException("...") {
        });

        ResponseEntity<CustomerDTO> response = customerController.getCustomerById(customerId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @DisplayName("Should return the customer when the customer id exists")
    void getCustomerByIdWhenIdExists() {
        Long customerId = 1L;
        CustomerDTO customerDTO = new CustomerDTO(
                customerId, "John Doe", "123456789", "987654321", "123 Main St", "555-1234", "john.doe@example.com");

        when(customerService.findOne(customerId)).thenReturn(customerDTO);

        ResponseEntity<CustomerDTO> response = customerController.getCustomerById(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerDTO, response.getBody());

        verify(customerService, times(1)).findOne(customerId);
    }
}