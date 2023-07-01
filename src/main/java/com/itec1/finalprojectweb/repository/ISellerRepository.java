package com.itec1.finalprojectweb.repository;

import com.itec1.finalprojectweb.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISellerRepository extends JpaRepository<Seller, Long> {
    Seller findByCuit(String cuit);
    Seller findByEmail(String email);
    Seller findByNameAndAddress(String name, String address);
}
