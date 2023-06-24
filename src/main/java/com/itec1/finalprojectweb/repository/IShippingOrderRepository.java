package com.itec1.finalprojectweb.repository;

import com.itec1.finalprojectweb.entity.ShippingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IShippingOrderRepository extends JpaRepository<ShippingOrder, Long> {
}
