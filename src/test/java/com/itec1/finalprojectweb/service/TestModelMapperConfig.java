package com.itec1.finalprojectweb.service;

import com.itec1.finalprojectweb.dto.CustomerDTO;
import com.itec1.finalprojectweb.entity.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestModelMapperConfig {

    @Bean
    public ModelMapper mapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configurar el mapeo entre Customer y CustomerDTO si es necesario
        modelMapper.createTypeMap(Customer.class, CustomerDTO.class)
                .addMapping(Customer::getName, CustomerDTO::setName)
                .addMapping(Customer::getCuit, CustomerDTO::setCuit)
                .addMapping(Customer::getDni, CustomerDTO::setDni)
                .addMapping(Customer::getAddress, CustomerDTO::setAddress)
                .addMapping(Customer::getPhoneNumber, CustomerDTO::setPhoneNumber)
                .addMapping(Customer::getEmail, CustomerDTO::setEmail);

        // Configurar otros mapeos si es necesario

        return modelMapper;
    }
}
