package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.dto.CustomerDTO;
import com.itec1.finalprojectweb.entity.Customer;
import com.itec1.finalprojectweb.exception.NotFoundException;
import com.itec1.finalprojectweb.repository.ICustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private ICustomerRepository customerRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;


    @Test
    @DisplayName("Should delete the customer by id successfully")
    void deleteCustomerByIdSuccessfully() {
        Long customerId = 1L;
        doNothing().when(customerRepository).deleteById(customerId);

        assertDoesNotThrow(() -> customerService.deleteById(customerId));

        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    @DisplayName("Should throw an exception when trying to delete a customer by non-existing id")
    void deleteCustomerByNonExistingIdThenThrowException() {
        Long nonExistingId = 100L;
        doThrow(NotFoundException.class)
                .when(customerRepository)
                .deleteById(nonExistingId);

        assertThrows(NotFoundException.class, () -> customerService.deleteById(nonExistingId));

        verify(customerRepository, times(1)).deleteById(nonExistingId);
    }

    @Test
    @DisplayName("Should validate the DTO when the CUIT and Email are not taken")
    void validateDTOWhenCuitAndEmailAreNotTaken() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCuit("123456789");
        customerDTO.setEmail("test@example.com");

        when(customerRepository.findByCuit(customerDTO.getCuit())).thenReturn(null);
        when(customerRepository.findByEmail(customerDTO.getEmail())).thenReturn(null);

        boolean result = customerService.validateDTO(customerDTO);

        assertTrue(result);
        verify(customerRepository, times(1)).findByCuit(customerDTO.getCuit());
        verify(customerRepository, times(1)).findByEmail(customerDTO.getEmail());
    }

    @Test
    @DisplayName("Should throw an exception when the Email is already taken")
    void validateDTOWhenEmailIsAlreadyTakenThenThrowException() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setEmail("test@example.com");

        Customer existingCustomer = new Customer();
        existingCustomer.setEmail("test@example.com");

        when(customerRepository.findByEmail(customerDTO.getEmail())).thenReturn(existingCustomer);

        // Act and Assert
        assertThrows(DuplicateKeyException.class, () -> customerService.validateDTO(customerDTO));

        // Verify
        verify(customerRepository, times(1)).findByEmail(customerDTO.getEmail());
    }

    @Test
    @DisplayName("Should throw an exception when the CUIT is already taken")
    void validateDTOWhenCuitIsAlreadyTakenThenThrowException() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCuit("1234567890");
        Customer existingCustomer = new Customer();
        existingCustomer.setCuit("1234567890");

        when(customerRepository.findByCuit(customerDTO.getCuit())).thenReturn(existingCustomer);

        // Act and Assert
        assertThrows(DuplicateKeyException.class, () -> customerService.validateDTO(customerDTO));

        // Verify
        verify(customerRepository, times(1)).findByCuit(customerDTO.getCuit());
    }

    @Test
    @DisplayName("Should throw a RuntimeException when there is a DataAccessException while saving the customer")
    void saveCustomerWhenDataAccessExceptionOccursThenThrowRuntimeException() {
        CustomerDTO customerDTO = new CustomerDTO(
                1L, "John Doe", "123456789", "1234567890", "123 Main St", "1234567890", "john.doe@example.com");

        Customer customer = new Customer(
                1L, "John Doe", "123456789", "1234567890", "123 Main St", "1234567890", "john.doe@example.com");

        when(modelMapper.map(customerDTO, Customer.class)).thenReturn(customer);
        when(customerRepository.save(customer)).thenThrow(new DataAccessException("...") {
        });

        assertThrows(RuntimeException.class, () -> customerService.save(customerDTO));

        verify(customerRepository, times(1)).save(customer);
        verify(modelMapper, times(1)).map(customerDTO, Customer.class);
    }

    @Test
    @DisplayName("Should save the customer when the customerDTO is valid")
    void saveCustomerWhenCustomerDTOIsValid() {
        CustomerDTO customerDTO = new CustomerDTO(
                1L, "John Doe", "123456789", "1234567890",
                "123 Main St", "123-456-7890", "john.doe@example.com");
        Customer customer = new Customer(
                1L, "John Doe", "123456789", "1234567890",
                "123 Main St", "123-456-7890", "john.doe@example.com");

        when(modelMapper.map(customerDTO, Customer.class)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(modelMapper.map(customer, CustomerDTO.class)).thenReturn(customerDTO);

        CustomerDTO savedCustomerDTO = customerService.save(customerDTO);

        assertNotNull(savedCustomerDTO);
        assertEquals(customerDTO.getId(), savedCustomerDTO.getId());
        assertEquals(customerDTO.getName(), savedCustomerDTO.getName());
        assertEquals(customerDTO.getCuit(), savedCustomerDTO.getCuit());
        assertEquals(customerDTO.getDni(), savedCustomerDTO.getDni());
        assertEquals(customerDTO.getAddress(), savedCustomerDTO.getAddress());
        assertEquals(customerDTO.getPhoneNumber(), savedCustomerDTO.getPhoneNumber());
        assertEquals(customerDTO.getEmail(), savedCustomerDTO.getEmail());

        verify(customerRepository, times(1)).save(customer);
        verify(modelMapper, times(1)).map(customerDTO, Customer.class);
        verify(modelMapper, times(1)).map(customer, CustomerDTO.class);
    }

    @Test
    @DisplayName("Should return an empty list when no customers exist in the repository")
    void findAllWhenNoCustomersExist() {
        when(customerRepository.findAll()).thenReturn(Collections.emptyList());

        List<CustomerDTO> result = customerService.findAll();

        assertTrue(result.isEmpty());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return all customers when customers exist in the repository")
    void findAllWhenCustomersExist() {
        List<Customer> customers = Collections.singletonList(
                new Customer(1L, "John Doe", "1234567890", "123456789", "123 Main St", "1234567890", "john.doe@example.com")
        );
        when(customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> expectedCustomerDTOs = Collections.singletonList(
                new CustomerDTO(1L, "John Doe", "1234567890", "123456789", "123 Main St", "1234567890", "john.doe@example.com")
        );
        when(modelMapper.map(customers.get(0), CustomerDTO.class)).thenReturn(expectedCustomerDTOs.get(0));

        List<CustomerDTO> actualCustomerDTOs = customerService.findAll();

        assertEquals(expectedCustomerDTOs.size(), actualCustomerDTOs.size());
        assertEquals(expectedCustomerDTOs.get(0), actualCustomerDTOs.get(0));

        verify(customerRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(customers.get(0), CustomerDTO.class);
    }

    @Test
    @DisplayName("Should return an empty list when the repository has no customers")
    void findAllWhenRepositoryHasNoCustomers() {
        when(customerRepository.findAll()).thenReturn(Collections.emptyList());

        List<CustomerDTO> result = customerService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return null when the customer id does not exist")
    void findOneWhenCustomerIdDoesNotExist() {
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        CustomerDTO result = customerService.findOne(customerId);

        assertNull(result);
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    @DisplayName("Should return the customer when the customer id exists")
    void findOneWhenCustomerIdExists() {
        Long customerId = 1L;
        Customer customer = new Customer(customerId, "John Doe", "123456789", "1234567890",
                "123 Main St", "1234567890", "john.doe@example.com");
        CustomerDTO expectedCustomerDTO = new CustomerDTO(customerId, "John Doe", "123456789", "1234567890",
                "123 Main St", "1234567890", "john.doe@example.com");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(modelMapper.map(customer, CustomerDTO.class)).thenReturn(expectedCustomerDTO);

        CustomerDTO result = customerService.findOne(customerId);

        assertEquals(expectedCustomerDTO, result);
        verify(customerRepository, times(1)).findById(customerId);
        verify(modelMapper, times(1)).map(customer, CustomerDTO.class);
    }
}