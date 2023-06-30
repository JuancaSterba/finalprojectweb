package com.itec1.finalprojectweb.repository;

import com.itec1.finalprojectweb.entity.ShippingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IShippingOrderRepository extends JpaRepository<ShippingOrder, Long> {
    List<ShippingOrder> findByCustomerId(Long customerId);

}
