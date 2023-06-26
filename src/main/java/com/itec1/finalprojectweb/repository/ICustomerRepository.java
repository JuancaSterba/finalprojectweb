package com.itec1.finalprojectweb.repository;

import com.itec1.finalprojectweb.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByCuit(String cuit);
    Customer findByEmail(String email);
}
