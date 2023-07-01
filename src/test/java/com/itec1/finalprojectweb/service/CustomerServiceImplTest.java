package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.dto.CustomerDTO;
import com.itec1.finalprojectweb.entity.Customer;
import com.itec1.finalprojectweb.repository.ICustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    @Mock
    private ICustomerRepository customerRepository;
    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private List<Customer> customers;

    @BeforeEach
    void setUp() {
        customers = new ArrayList<>();
        customers.add(new Customer(1L, "John Doe", "1234567890", "123456789", "123 Main St", "1234567890", "john.doe@example.com"));
        customers.add(new Customer(2L, "Jane Smith", "0987654321", "987654321", "456 Elm St", "9876543210", "jane.smith@example.com"));
    }


    @Test
    @DisplayName("Should return an empty list when the repository has no customers")
    void findAllWhenRepositoryHasNoCustomers() {
        when(customerRepository.findAll()).thenReturn(new ArrayList<>());

        List<CustomerDTO> result = customerService.findAll();

        assertNotNull(result);
        assertEquals(0, result.size());

        verify(customerRepository, times(1)).findAll();
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    @DisplayName("Should return all customers when the repository has customers")
    void findAllWhenRepositoryHasCustomers() {
        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> expectedCustomers = new ArrayList<>();
        for (Customer customer : customers) {
            expectedCustomers.add(new CustomerDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getCuit(),
                    customer.getDni(),
                    customer.getAddress(),
                    customer.getPhoneNumber(),
                    customer.getEmail()
            ));
        }

        when(mapper.map(any(Customer.class), eq(CustomerDTO.class))).thenAnswer(invocation -> {
            Customer customer = invocation.getArgument(0);
            return new CustomerDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getCuit(),
                    customer.getDni(),
                    customer.getAddress(),
                    customer.getPhoneNumber(),
                    customer.getEmail()
            );
        });

        List<CustomerDTO> result = customerService.findAll();

        assertNotNull(result);
        assertEquals(expectedCustomers.size(), result.size());
        for (int i = 0; i < expectedCustomers.size(); i++) {
            assertEquals(expectedCustomers.get(i), result.get(i));
        }

        verify(customerRepository, times(1)).findAll();
        verify(mapper, times(customers.size())).map(any(Customer.class), eq(CustomerDTO.class));
    }

    @Test
    @DisplayName("Should return null when the customer with the given id does not exist")
    void findOneWhenCustomerDoesNotExist() {
        Long id = 3L;
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        CustomerDTO result = customerService.findOne(id);

        assertNull(result);
        verify(customerRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should return the customer when the customer with the given id exists")
    void findOneWhenCustomerExists() {
        Long customerId = 1L;
        Customer customer = customers.get(0); // Obtener el primer cliente de la lista existente
        CustomerDTO expectedCustomerDTO = new CustomerDTO(1L, "John Doe", "1234567890", "123456789", "123 Main St", "1234567890", "john.doe@example.com");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(mapper.map(customer, CustomerDTO.class)).thenReturn(expectedCustomerDTO);

        CustomerDTO actualCustomerDTO = customerService.findOne(customerId);

        assertNotNull(actualCustomerDTO);
        assertEquals(expectedCustomerDTO, actualCustomerDTO);

        verify(customerRepository, times(1)).findById(customerId);
        verify(mapper, times(1)).map(customer, CustomerDTO.class);
    }
}