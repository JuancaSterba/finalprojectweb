package com.itec1.finalprojectweb.repository;

import com.itec1.finalprojectweb.entity.ShippingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IShippingOrderRepository extends JpaRepository<ShippingOrder, Long> {
    List<ShippingOrder> findByCustomerId(Long customerId);

}
