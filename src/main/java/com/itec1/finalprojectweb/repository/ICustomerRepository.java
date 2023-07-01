package com.itec1.finalprojectweb.repository;

import com.itec1.finalprojectweb.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByCuit(String cuit);
    Customer findByEmail(String email);
    Customer findByCuitAndDniAndEmail(String cuit, String dni, String email);
}
